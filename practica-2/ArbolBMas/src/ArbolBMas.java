import java.util.ArrayList;

public class ArbolBMas {

    // Atributos
    private final int orden;
    private NodoArbolBMas raiz;

    // Métodos
    // Constructor
    public ArbolBMas(int orden) {
        this.orden = orden;
        raiz = new NodoArbolBMas(true);
    }

    // Inserción de una llave y su dato asociado en el árbol B+
    public void insertar(int clave, String dato) {
        // Si la llave ya existe en el árbol, no se inserta de nuevo
        if(buscar(clave)) {
            System.out.println("La llave " + clave + " ya existe en el árbol.");
            return;
        }

        // Se inserta primero de forma recursiva
        insertarRecursivo(this.raiz, clave, dato);

        // Se verifica si la raíz se desbordó
        if(this.raiz.getClaves().size() == orden) {
            NodoArbolBMas nuevaRaiz = new NodoArbolBMas(false);
            nuevaRaiz.getHijos().add(this.raiz);
            dividirHijo(nuevaRaiz, 0);
            this.raiz = nuevaRaiz;
        }
    }

    // Inserción recursiva de una llave y su dato asociado
    // Permite que los nodos se desborden temporalmente y los divide al regresar de la recursión
    private void insertarRecursivo(NodoArbolBMas nodo, int llave, String dato) {
        int i = nodo.getClaves().size() - 1;

        if(nodo.esHoja()) {
            while(i >= 0 && llave < nodo.getClaves().get(i)) {
                i--;
            }
            nodo.getClaves().add(i + 1, llave);
            // El dato se guarda en la misma posición que su llave
            nodo.getDatos().add(i + 1, dato);
        } else {
            while(i >= 0 && llave < nodo.getClaves().get(i)) {
                i--;
            }
            i++;
            NodoArbolBMas hijo = nodo.getHijos().get(i);

            // Llamada recursiva para insertar la llave primero en el hijo correspondiente
            insertarRecursivo(hijo, llave, dato);

            // Evaluación de desbordamiento al retornar de la recursividad
            if(hijo.getClaves().size() == orden) {
                dividirHijo(nodo, i);
            }
        }
    }

    // Dividir un nodo que está lleno
    private void dividirHijo(NodoArbolBMas padre, int indice) {
        NodoArbolBMas nodoLleno = padre.getHijos().get(indice);
        NodoArbolBMas nuevoNodo = new NodoArbolBMas(nodoLleno.esHoja());
        int mitad = orden / 2;

        if (nodoLleno.esHoja()) {
            // En nodos hoja, se mantiene la llave media en ambos lados
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            // Los datos se reparten igual que las llaves, para que cada dato siga junto a su llave
            nuevoNodo.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(mitad, nodoLleno.getDatos().size())));
            nodoLleno.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(0, mitad)));

            // La nueva hoja queda a la derecha de la hoja dividida, por lo que se enlaza entre ella y su antigua siguiente
            nuevoNodo.setSiguiente(nodoLleno.getSiguiente());
            nodoLleno.setSiguiente(nuevoNodo);

            // El padre recibe como separador la primera llave del nuevo nodo
            int llaveMedia = nuevoNodo.getClaves().getFirst();
            padre.getClaves().add(indice, llaveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
        else {
            // En nodos internos, la llave media sube y se elimina del hijo
            int claveMedia = nodoLleno.getClaves().get(mitad);
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad + 1, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            // Mover los hijos del nodo dividido
            nuevoNodo.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(mitad + 1, nodoLleno.getHijos().size())));
            nodoLleno.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(0, mitad + 1)));

            padre.getClaves().add(indice, claveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
    }

    // Impresión del árbol como parte de la interfaz pública del árbol
    public void imprimirArbol() {
        imprimirNodo(raiz, "", true);
    }

    // Impresión recursiva privada de los nodos a partir de uno inicial
    private void imprimirNodo(NodoArbolBMas nodo, String indentacion, boolean esUltimo) {
        System.out.println(indentacion + "+- " + (nodo.esHoja() ? "Hoja > " : "Interno > ") + nodo.getClaves());
        indentacion += esUltimo ? "   " : "|  ";
        for(int i = 0; i < nodo.getHijos().size(); i++) {
            imprimirNodo(nodo.getHijos().get(i), indentacion, i == (nodo.getHijos().size() - 1));
        }
    }

    // Búsqueda de un nodo como parte de la interfaz pública del árbol
    public boolean buscar(int llave) {
        return buscarNodo(raiz, llave);
    }

    // Búsqueda recursiva privada de un nodo a partir de uno inicial
    private boolean buscarNodo(NodoArbolBMas nodo, int llave) {
        int i = 0;
        while(i < nodo.getClaves().size() && llave > nodo.getClaves().get(i)) {
            i++;
        }
        if(i < nodo.getClaves().size() && llave == nodo.getClaves().get(i)) {
            return true;
        }
        if(nodo.esHoja()) {
            return false;
        } else {
            return buscarNodo(nodo.getHijos().get(i), llave);
        }
    }

    // Eliminación
    // Eliminación de una llave y su dato asociado como parte de la interfaz pública del árbol
    public void eliminar(int clave) {
        // Si la llave no existe en el árbol, no hay nada que eliminar
        if(!buscar(clave)) {
            System.out.println("La llave " + clave + " no existe en el árbol.");
            return;
        }

        // Se elimina primero de forma recursiva
        eliminarRecursivo(this.raiz, clave);

        // Si la raíz es un nodo interno y se quedó sin llaves, su único hijo pasa a ser la nueva raíz
        if(!this.raiz.esHoja() && this.raiz.getClaves().isEmpty()) {
            this.raiz = this.raiz.getHijos().getFirst();
        }
    }

    // Eliminación recursiva de una llave
    // Permite que los nodos queden por debajo del mínimo temporalmente y los rebalancea al regresar de la recursión
    private void eliminarRecursivo(NodoArbolBMas nodo, int llave) {
        if(nodo.esHoja()) {
            // La llave y su dato se eliminan de la misma posición
            int posicion = nodo.getClaves().indexOf(llave);
            nodo.getClaves().remove(posicion);
            nodo.getDatos().remove(posicion);
        } else {
            // Se busca el hijo correspondiente con el mismo criterio de la inserción
            int i = nodo.getClaves().size() - 1;
            while(i >= 0 && llave < nodo.getClaves().get(i)) {
                i--;
            }
            i++;
            NodoArbolBMas hijo = nodo.getHijos().get(i);

            // Llamada recursiva para eliminar la llave primero en el hijo correspondiente
            eliminarRecursivo(hijo, llave);

            // La llave eliminada pudo haber sido un separador, por lo que se actualizan antes de rebalancear
            actualizarSeparadores(nodo);

            // Evaluación de subdesbordamiento al retornar de la recursividad
            if(hijo.getClaves().size() < minimoClaves(hijo)) {
                rebalancearHijo(nodo, i);
                actualizarSeparadores(nodo);
            }
        }
    }

    // Cantidad mínima de llaves de un nodo que no es la raíz
    // Una hoja debe conservar al menos m/2 llaves y un nodo interno al menos m/2 hijos (redondeado hacia arriba)
    private int minimoClaves(NodoArbolBMas nodo) {
        if(nodo.esHoja()) {
            return orden / 2;
        }
        return (orden + 1) / 2 - 1;
    }

    // Rebalanceo de un hijo que quedó por debajo del mínimo de llaves
    // Primero intenta pedir prestada una llave a un hermano y, si ninguno puede prestar, lo fusiona con uno de ellos
    private void rebalancearHijo(NodoArbolBMas padre, int indice) {
        NodoArbolBMas hijo = padre.getHijos().get(indice);
        NodoArbolBMas hermanoIzq = (indice > 0) ? padre.getHijos().get(indice - 1) : null;
        NodoArbolBMas hermanoDer = (indice < padre.getHijos().size() - 1) ? padre.getHijos().get(indice + 1) : null;

        if(hermanoIzq != null && hermanoIzq.getClaves().size() > minimoClaves(hermanoIzq)) {
            prestarDeIzquierda(padre, indice, hijo, hermanoIzq);
        } else if(hermanoDer != null && hermanoDer.getClaves().size() > minimoClaves(hermanoDer)) {
            prestarDeDerecha(padre, indice, hijo, hermanoDer);
        } else if(hermanoIzq != null) {
            fusionar(padre, indice - 1);
        } else {
            fusionar(padre, indice);
        }
    }

    // Préstamo de una llave del hermano izquierdo
    private void prestarDeIzquierda(NodoArbolBMas padre, int indice, NodoArbolBMas hijo, NodoArbolBMas hermanoIzq) {
        int ultima = hermanoIzq.getClaves().size() - 1;

        if(hijo.esHoja()) {
            // En hojas, la última llave del hermano y su dato pasan al inicio del hijo
            hijo.getClaves().add(0, hermanoIzq.getClaves().remove(ultima));
            hijo.getDatos().add(0, hermanoIzq.getDatos().remove(ultima));

            // El separador pasa a ser la nueva primera llave del hijo
            padre.getClaves().set(indice - 1, hijo.getClaves().getFirst());
        } else {
            // En nodos internos, el separador baja al hijo y la última llave del hermano sube al padre
            hijo.getClaves().add(0, padre.getClaves().get(indice - 1));
            padre.getClaves().set(indice - 1, hermanoIzq.getClaves().remove(ultima));

            // El último hijo del hermano pasa a ser el primer hijo del nodo
            hijo.getHijos().add(0, hermanoIzq.getHijos().remove(hermanoIzq.getHijos().size() - 1));
        }
    }

    // Préstamo de una llave del hermano derecho
    private void prestarDeDerecha(NodoArbolBMas padre, int indice, NodoArbolBMas hijo, NodoArbolBMas hermanoDer) {
        if(hijo.esHoja()) {
            // En hojas, la primera llave del hermano y su dato pasan al final del hijo
            hijo.getClaves().add(hermanoDer.getClaves().remove(0));
            hijo.getDatos().add(hermanoDer.getDatos().remove(0));

            // El separador pasa a ser la nueva primera llave del hermano
            padre.getClaves().set(indice, hermanoDer.getClaves().getFirst());
        } else {
            // En nodos internos, el separador baja al hijo y la primera llave del hermano sube al padre
            hijo.getClaves().add(padre.getClaves().get(indice));
            padre.getClaves().set(indice, hermanoDer.getClaves().remove(0));

            // El primer hijo del hermano pasa a ser el último hijo del nodo
            hijo.getHijos().add(hermanoDer.getHijos().remove(0));
        }
    }

    // Fusión de dos hijos contiguos: el hijo derecho se integra en el izquierdo y desaparece
    private void fusionar(NodoArbolBMas padre, int indiceIzq) {
        NodoArbolBMas izquierdo = padre.getHijos().get(indiceIzq);
        NodoArbolBMas derecho = padre.getHijos().get(indiceIzq + 1);

        // Se retira del padre el separador entre ambos hijos
        int separador = padre.getClaves().remove(indiceIzq);

        if(izquierdo.esHoja()) {
            // En hojas, el separador se descarta porque su llave ya está en la hoja derecha
            izquierdo.getClaves().addAll(derecho.getClaves());
            izquierdo.getDatos().addAll(derecho.getDatos());

            // La hoja fusionada se enlaza con la hoja que seguía a la derecha
            izquierdo.setSiguiente(derecho.getSiguiente());
        } else {
            // En nodos internos, el separador baja y queda entre las llaves de ambos hijos
            izquierdo.getClaves().add(separador);
            izquierdo.getClaves().addAll(derecho.getClaves());
            izquierdo.getHijos().addAll(derecho.getHijos());
        }

        padre.getHijos().remove(indiceIzq + 1);
    }

    // Actualización de los separadores de un nodo interno
    // Cada separador debe ser la llave más pequeña del subárbol que tiene a su derecha
    private void actualizarSeparadores(NodoArbolBMas nodo) {
        for(int i = 1; i < nodo.getHijos().size(); i++) {
            Integer minimo = obtenerMinimo(nodo.getHijos().get(i));
            if(minimo != null) {
                nodo.getClaves().set(i - 1, minimo);
            }
        }
    }

    // Obtención de la llave más pequeña de un subárbol, descendiendo siempre por el primer hijo
    // Retorna null si la hoja alcanzada está vacía
    private Integer obtenerMinimo(NodoArbolBMas nodo) {
        while(!nodo.esHoja()) {
            nodo = nodo.getHijos().getFirst();
        }
        if(nodo.getClaves().isEmpty()) {
            return null;
        }
        return nodo.getClaves().getFirst();
    }


    // Recorrido por rango (Debe mostrar los datos correspondientes al rango recuperado.)
    // Busca la llave de inicio y, a partir de ella, imprime los siguientes n elementos recorriendo las hojas enlazadas
    public void recorrerRango(int llaveInicio, int n) {
        if(n <= 0) {
            System.out.println("La cantidad de elementos a recorrer debe ser mayor que 0.");
            return;
        }
        if(this.raiz.getClaves().isEmpty()) {
            System.out.println("El árbol está vacío.");
            return;
        }

        // Se busca la hoja en la que se encuentra (o debería encontrarse) la llave de inicio
        NodoArbolBMas hoja = buscarHoja(llaveInicio);

        // Dentro de esa hoja, se ubica la primera llave mayor o igual que la llave de inicio
        int i = 0;
        while(i < hoja.getClaves().size() && hoja.getClaves().get(i) < llaveInicio) {
            i++;
        }

        if(!hoja.getClaves().contains(llaveInicio)) {
            System.out.println("La llave " + llaveInicio + " no existe; el recorrido comienza en la siguiente llave mayor.");
        }

        // Se imprimen los n elementos y, al terminar una hoja, se salta a la siguiente mediante el enlace
        int impresos = 0;
        while(hoja != null && impresos < n) {
            if(i < hoja.getClaves().size()) {
                System.out.println("Llave: " + hoja.getClaves().get(i) + " | Dato: " + hoja.getDatos().get(i));
                impresos++;
                i++;
            } else {
                hoja = hoja.getSiguiente();
                i = 0;
            }
        }

        if(impresos == 0) {
            System.out.println("No hay elementos a partir de la llave " + llaveInicio + ".");
        } else if(impresos < n) {
            System.out.println("Se llegó al final del árbol: solo había " + impresos + " elemento(s) para recorrer.");
        }
    }

    // Búsqueda privada de la hoja en la que se encuentra (o debería encontrarse) una llave
    // Las llaves iguales a un separador se encuentran en el subárbol derecho de este
    private NodoArbolBMas buscarHoja(int llave) {
        NodoArbolBMas nodo = this.raiz;
        while(!nodo.esHoja()) {
            int i = 0;
            while(i < nodo.getClaves().size() && llave >= nodo.getClaves().get(i)) {
                i++;
            }
            nodo = nodo.getHijos().get(i);
        }
        return nodo;
    }
}
