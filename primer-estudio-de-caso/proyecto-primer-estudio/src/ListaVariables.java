public class ListaVariables {

    private NodoVariable primero;

    public ListaVariables(){
        primero = null;
    }

    public boolean estaVacia(){
        return primero == null;
    }

    private NodoVariable buscar(String nombre){
        NodoVariable temp = primero;
        while (temp != null){
            if (nombre.equals(temp.getNombre())) return temp;
            temp = temp.getSiguiente();
        }
        return null;
    }

    public String obtenerValor(String nombre){
        NodoVariable nodo = buscar(nombre);
        if (nodo == null) return null;
        return nodo.getValor();
    }

    public void definir(String nombre, String valor){
        NodoVariable existente = buscar(nombre);
        if (existente != null){
            existente.setValor(valor);
            return;
        }

        NodoVariable nodo = new NodoVariable(nombre, valor);
        if (estaVacia()){
            primero = nodo;
            return;
        }

        NodoVariable temp = primero;
        while (temp.getSiguiente() != null){
            temp = temp.getSiguiente();
        }
        temp.setSiguiente(nodo);
    }

    public void mostrarLista(){
        if (estaVacia()){
            System.out.println("No hay variables definidas.");
            return;
        }
        NodoVariable temp = primero;
        while (temp != null){
            System.out.println(temp);
            temp = temp.getSiguiente();
        }
    }


    private class NodoVariable{

        private String nombre;
        private String valor;
        private NodoVariable siguiente;

        public NodoVariable(String nombre, String valor){
            this.nombre = nombre;
            this.valor = valor;
            siguiente = null;
        }

        public String getNombre(){
            return nombre;
        }

        public String getValor(){
            return valor;
        }

        public NodoVariable getSiguiente(){
            return siguiente;
        }

        public void setNombre(String nombre){
            this.nombre = nombre;
        }

        public void setValor(String valor){
            this.valor = valor;
        }

        public void setSiguiente(NodoVariable siguiente){
            this.siguiente = siguiente;
        }

        public String toString(){
            return nombre + " = \"" + valor + "\"";
        }
    }
}
