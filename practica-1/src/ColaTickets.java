import java.util.ArrayList;

public class ColaTickets {

    private ArrayList<Ticket> cola;

    public ColaTickets(){
        cola = new ArrayList<>();
    }

    //Operaciones
    private boolean estaVacia(){
        return cola.isEmpty();
    }

    public void insertar(Ticket ticket){
        int posicion = 0;
        while (posicion < cola.size() && cola.get(posicion).getPrioridad() <= ticket.getPrioridad()){
            posicion++;
        }

        cola.add(posicion, ticket);
    }

    public Ticket eliminar(){
        if (estaVacia()){
            System.out.println("La cola esta vacia.\n");
            return null;
        }
        return cola.removeFirst();
    }

    public Ticket verFrente(){
        if (estaVacia()){
            System.out.println("La cola esta vacia.\n");
            return null;
        }
        return cola.getFirst();
    }

    public void mostrarCola(){
        if (estaVacia()){
            System.out.println("\nLa cola esta vacia.\n");
            return;
        }

        for (int i = 0; i < cola.size(); i++){
            System.out.println("\nPosición " + (i + 1) + ":" + cola.get(i));
        }
    }
}