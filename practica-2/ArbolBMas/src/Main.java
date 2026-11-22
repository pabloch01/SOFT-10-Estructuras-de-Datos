import java.util.Scanner;

public class Main {

    // Atributos
    private static Scanner sc = new Scanner(System.in);
    private static ArbolBMas arbol = new ArbolBMas(4);

    // Rutina principal
    public static void main(String[] args) {
        // Se precargan algunos registros para que el árbol no inicie vacío
        precargarDatos();

        // Se muestra el menú hasta que el usuario decida salir
        menu();
    }

    // Precarga de registros de prueba
    private static void precargarDatos() {
        int[] llaves = {10, 20, 5, 6, 15, 30, 25, 40, 45, 50};
        for(int llave : llaves) {
            arbol.insertar(llave, "Registro " + llave);
        }
    }

    // Menú de consola para interactuar con el árbol B+
    public static void menu() {
        int opcion;
        do {
            System.out.println("\n===== ÁRBOL B+ (orden 4) =====");
            System.out.println("1. Insertar un registro");
            System.out.println("2. Buscar una llave");
            System.out.println("3. Recorrer registros a partir de una llave");
            System.out.println("4. Mostrar el árbol");
            System.out.println("0. Salir");

            opcion = leerEntero("\nSeleccione una opción: ");

            switch(opcion) {
                case 1:
                    opcionInsertar();
                    break;
                case 2:
                    opcionBuscar();
                    break;
                case 3:
                    opcionRecorrerRango();
                    break;
                case 4:
                    System.out.println("\n--- ESTRUCTURA DEL ÁRBOL ---");
                    arbol.imprimirArbol();
                    break;
                case 0:
                    System.out.println("\nSaliendo del programa. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("\n[Error] Opción no válida. Elija un número entre 0 y 4.");
            }
        } while(opcion != 0);
    }

    // Opción para insertar una llave junto con su dato
    private static void opcionInsertar() {
        System.out.println("\n--- INSERTAR UN REGISTRO ---");
        int llave = leerEntero("Llave (número entero): ");

        // Se valida antes de pedir el dato, para no pedirlo si la llave ya existe
        if(arbol.buscar(llave)) {
            System.out.println("[Error] La llave " + llave + " ya existe en el árbol.");
            return;
        }

        String dato = leerTexto("Dato asociado a la llave: ");
        arbol.insertar(llave, dato);
        System.out.println("¡Registro insertado correctamente!");
    }

    // Opción para buscar una llave y mostrar su dato
    private static void opcionBuscar() {
        System.out.println("\n--- BUSCAR UNA LLAVE ---");
        int llave = leerEntero("Llave a buscar: ");

        if(arbol.buscar(llave)) {
            System.out.println("La llave se encuentra en el árbol:");
            // Un recorrido de un solo elemento muestra el dato de la llave encontrada
            arbol.recorrerRango(llave, 1);
        } else {
            System.out.println("[Error] La llave " + llave + " no se encuentra en el árbol.");
        }
    }

    // Opción para recorrer los siguientes n registros a partir de una llave
    private static void opcionRecorrerRango() {
        System.out.println("\n--- RECORRER REGISTROS ---");
        int llaveInicio = leerEntero("Llave de inicio: ");
        int cantidad = leerEnteroPositivo("Cantidad de registros a mostrar: ");
        arbol.recorrerRango(llaveInicio, cantidad);
    }

    // Lectura de un número entero, repitiendo la pregunta hasta recibir un valor válido
    private static int leerEntero(String mensaje) {
        while(true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch(NumberFormatException e) {
                System.out.println("[Error] Escriba un número entero.");
            }
        }
    }

    // Lectura de un número entero mayor que 0
    private static int leerEnteroPositivo(String mensaje) {
        while(true) {
            int numero = leerEntero(mensaje);
            if(numero > 0) {
                return numero;
            }
            System.out.println("[Error] El número debe ser mayor que 0.");
        }
    }

    // Lectura de un texto que no puede quedar vacío
    private static String leerTexto(String mensaje) {
        while(true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            if(!texto.isEmpty()) {
                return texto;
            }
            System.out.println("[Error] El dato no puede quedar vacío.");
        }
    }
}
