import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {

        ColaTickets cola = new ColaTickets();           
        ListaTickets resueltos = new ListaTickets();

        System.out.println("\t\n---MENU DE GESTION DE TICKETS---\n");

        byte usuario;

        while (true){
            try{
                System.out.print("\nIngrese su tipo de usuario:\n \n1. Usuario \n2. Administrador \n0. Salir \n\n: ");
                usuario = Byte.parseByte(entrada.readLine());

                if (usuario == 0){
                    System.out.println("\nSaliendo del sistema.");
                    break;
                }

                else if (usuario == 1) menuUsuario(cola, resueltos);

                else if (usuario == 2) menuAdministrador(cola, resueltos);

                else System.out.println("\nLa opción ingresada no está dentro de las opciones permitidas.");

            } catch(NumberFormatException e){
                System.out.println("\nEl dato ingresado es inválido.");
            }
        }
    }

    public static String obtenerFechaActual(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static void resolverTicket(ColaTickets cola, ListaTickets resueltos){
        Ticket ticket = cola.eliminar();
        if (ticket == null) return;

        ticket.setFechaResolucion(obtenerFechaActual());
        resueltos.insertarInicio(ticket);
        System.out.println("\nEl ticket con ID " + ticket.getId() + " fue resuelto el " + ticket.getFechaResolucion() + ".");
    }

    public static void crearTicket(ColaTickets cola) throws IOException {

        String nombreCompleto;
        while (true){
            System.out.print("\nDigite su nombre completo: ");
            nombreCompleto = entrada.readLine().trim();
            if (!nombreCompleto.isEmpty()) break;
            System.out.println("\nEl nombre no puede estar vacío.");
        }

        String descripcion;
        while (true){
            System.out.print("\nDescriba el problema o solicitud: ");
            descripcion = entrada.readLine().trim();
            if (!descripcion.isEmpty()) break;
            System.out.println("\nLa descripción no puede estar vacía.");
        }

        byte prioridad;
        while (true){
            try{
                System.out.print("\nSeleccione la prioridad del ticket: \n1. Alta \n2. Media \n3. Baja \n: ");
                prioridad = Byte.parseByte(entrada.readLine());
                if (prioridad >= 1 && prioridad <= 3) break;
                System.out.println("\nLa opción ingresada no está dentro de las opciones permitidas.");
            } catch(NumberFormatException e){
                System.out.println("\nEl dato ingresado no es válido.");
            }
        }

        Ticket ticket = new Ticket(nombreCompleto, descripcion, prioridad, obtenerFechaActual());
        cola.insertar(ticket);
        System.out.println("\nTicket creado con éxito. Guarde su ID para el seguimiento: " + ticket.getId());
    }

    public static void seguirTicket(ListaTickets resueltos) throws IOException {

        int id;
        while (true){
            try{
                System.out.print("\nDigite el ID del ticket que desea consultar: ");
                id = Integer.parseInt(entrada.readLine());
                break;
            } catch(NumberFormatException e){
                System.out.println("\nEl dato ingresado no es válido.");
            }
        }

        Ticket resuelto = resueltos.buscar(id);
        if (resuelto != null){
            System.out.println("\nEl ticket ya fue resuelto:" + resuelto);
        }
    }

    public static void verYResolverTicket(ColaTickets cola, ListaTickets resueltos) throws IOException {

        Ticket frente = cola.verFrente();
        if (frente == null) return;

        System.out.println("\nTicket al frente de la cola:" + frente);

        byte confirmacion;
        while (true){
            try{
                System.out.print("¿Desea resolver este ticket? \n1. Sí \n2. No \n: ");
                confirmacion = Byte.parseByte(entrada.readLine());
                if (confirmacion == 1 || confirmacion == 2) break;
                System.out.println("\nLa opción ingresada no está dentro de las opciones permitidas.");
            } catch(NumberFormatException e){
                System.out.println("\nEl dato ingresado no es válido.");
            }
        }

        if (confirmacion == 1) resolverTicket(cola, resueltos);
    }

    public static void menuUsuario(ColaTickets cola, ListaTickets resueltos) throws IOException {

        byte opcion;

        while (true){
            try{
                System.out.print("\nSeleccione la acción que desea realizar: \n1. Crear un ticket \n2. Seguimiento de ticket \n0. Volver \n: ");
                opcion = Byte.parseByte(entrada.readLine());

                if (opcion == 0) break;

                else if (opcion == 1) crearTicket(cola);

                else if (opcion == 2) seguirTicket(resueltos);

                else System.out.println("\nLa opción ingresada no está dentro de las opciones permitidas.");

            } catch(NumberFormatException e){
                System.out.println("\nEl dato ingresado no es válido.");
            }
        }
    }

    public static void menuAdministrador(ColaTickets cola, ListaTickets resueltos) throws IOException {

        byte opcion;

        while (true){
            try{
                System.out.print("\nSelecciona la acción que desea realizar: \n1. Ver cola de prioridad \n2. Ver y resolver un ticket \n3. Ver lista de tickets resueltos \n0. Volver \n: ");
                opcion = Byte.parseByte(entrada.readLine());

                if (opcion == 0) break;

                else if (opcion == 1) cola.mostrarCola();

                else if (opcion == 2) verYResolverTicket(cola, resueltos);

                else if (opcion == 3) resueltos.mostrarLista();

                else System.out.println("\nLa opción ingresada no está dentro de las opciones permitidas.");

            } catch(NumberFormatException e){
                System.out.println("\nEl dato ingresado no es válido.");
            }
        }
    }
}