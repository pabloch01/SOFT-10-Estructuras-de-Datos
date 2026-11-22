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
