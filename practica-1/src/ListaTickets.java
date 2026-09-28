public class ListaTickets {

    private Ticket primero;

    public ListaTickets(){
        primero = null;
    }

    private Ticket getPrimero(){
        return primero;
    }

    private void setPrimero(Ticket primero){
        this.primero = primero;
    }

    private boolean estaVacia(){
        return primero == null;
    }

    public void insertarInicio(Ticket ticket){
        ticket.setSiguiente(primero);
        setPrimero(ticket);
    }

    public Ticket buscar(int id){
        if (estaVacia()){
            System.out.println("\nEl ticket aún se encuentra pendiente. Por favor vuelva a consultar más tarde.");
            return null;
        }

        Ticket temp = primero;
        while (temp!= null){
            if (id == temp.getId()) return temp;
            temp = temp.getSiguiente();
        }

        System.out.println("\nEl ticket aún se encuentra pendiente. Por favor vuelva a consultar más tarde.");
        return null;
    }

    public void mostrarLista(){
        if (estaVacia()){
            System.out.println("\nLa lista esta vacia.");
            return;
        }
        Ticket temp = primero;
        System.out.println("\nLista de tickets resueltos:");
        while (temp != null){
            System.out.println(temp);
            temp = temp.getSiguiente();
        }
    }
}
