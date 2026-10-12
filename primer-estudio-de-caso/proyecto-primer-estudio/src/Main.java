import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
    private static ListaVariables variables = new ListaVariables();
    private static PilaTokens pila = new PilaTokens();

        public static void main(String[] args){
        menu();
    }

    private static String leerLinea(){
        try {
            String linea = entrada.readLine();
            if (linea == null){
                System.out.println("\nFin de la entrada. Saliendo del sistema.");
                System.exit(0);
            }
            return linea;
        } catch (IOException e){
            System.out.println("Error al leer la entrada: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    private static int leerEntero(String msg){
        while (true){
            try {
                System.out.print(msg);
                return Integer.parseInt(leerLinea().trim());
            } catch (NumberFormatException e){
                System.out.println("Error: escriba un numero entero.");
            }
        }
    }

    private static void opcionDefinirVariable(){
        System.out.println("\n--- DEFINIR VARIABLE DE CADENA ---");
        System.out.print("Nombre de la variable: ");
        String nombre = leerLinea().trim();

        if (!esIdentificadorValido(nombre)){
            System.out.println("Nombre no valido. Debe iniciar con una letra o _ y solo contener letras, numeros o _.");
            return;
        }

        System.out.print("Valor de la variable (sin comillas): ");
        String valor = leerLinea();

        variables.definir(nombre, valor);
        System.out.println("Variable definida: " + nombre + " = \"" + valor + "\"");
    }

    private static void opcionVerVariables(){
        System.out.println("\n--- VARIABLES DEFINIDAS ---");
        variables.mostrarLista();
    }

    private static void opcionCargarCadena(){
        System.out.println("\n--- CARGAR CADENA EN LA PILA ---");
        cargarCadena();
    }

    private static void opcionVerPila(){
        System.out.println("\n--- CONTENIDO DE LA PILA ---");
        pila.mostrarPila();
    }

    private static void opcionConsultarTope(){
        System.out.println("\n--- CONSULTAR TOPE (PEEP) ---");
        Token tope = pila.peep();
        if (tope != null){
            System.out.println("Tope de la pila: " + tope + " (" + tope.getDescripcionTipo() + "). La pila no cambia.");
        }
    }

    private static void opcionSacarTope(){
        System.out.println("\n--- SACAR TOPE (POP) ---");
        Token tope = pila.pop();
        if (tope != null){
            System.out.println("Se saco el tope: " + tope + " (" + tope.getDescripcionTipo() + ")");
        }
    }

    private static void opcionEvaluarPila(){
        System.out.println("\n--- EVALUAR LA PILA ---");
        evaluarPila();
    }

    private static void opcionAnalizarCadenaCompleta(){
        System.out.println("\n--- ANALIZAR CADENA COMPLETA ---");
        if (cargarCadena()){
            evaluarPila();
        }
    }

    private static void menu(){
        int opcion;
        do {
            System.out.println("\n--- MENU DEL ANALIZADOR DE CADENAS DE IMPRESION ---");
            System.out.println("1. Definir variable de cadena");
            System.out.println("2. Ver variables definidas");
            System.out.println("3. Cargar cadena en la pila");
            System.out.println("4. Ver contenido de la pila");
            System.out.println("5. Consultar tope (peep)");
            System.out.println("6. Sacar tope (pop)");
            System.out.println("7. Evaluar la pila");
            System.out.println("8. Analizar cadena completa");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");

            if (opcion == 1){
                opcionDefinirVariable();
            } else if (opcion == 2){
                opcionVerVariables();
            } else if (opcion == 3){
                opcionCargarCadena();
            } else if (opcion == 4){
                opcionVerPila();
            } else if (opcion == 5){
                opcionConsultarTope();
            } else if (opcion == 6){
                opcionSacarTope();
            } else if (opcion == 7){
                opcionEvaluarPila();
            } else if (opcion == 8){
                opcionAnalizarCadenaCompleta();
            } else if (opcion != 0){
                System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
        System.out.println("Saliendo del sistema.");
    }

    private static boolean esIdentificadorValido(String nombre){
        if (nombre.isEmpty()) return false;
        if (!esInicioIdentificador(nombre.charAt(0))) return false;

        for (int i = 1; i < nombre.length(); i++){
            if (!esParteIdentificador(nombre.charAt(i))) return false;
        }
        return true;
    }

    private static boolean esInicioIdentificador(char c){
        return Character.isLetter(c) || c == '_';
    }

    private static boolean esParteIdentificador(char c){
        return Character.isLetterOrDigit(c) || c == '_';
    }

    private static boolean cargarCadena(){
        System.out.println("Escriba la cadena de impresion. Ejemplo: \"Hola \" + nombre + \"!\"");
        System.out.print("> ");
        String cadena = leerLinea();

        ArrayList<Token> tokens = particionar(cadena);
        if (tokens == null) return false;

        pila = new PilaTokens();
        for (Token token : tokens){
            pila.push(token);
        }

        System.out.println("Se apilaron " + tokens.size() + " tokens.");
        pila.mostrarPila();
        return true;
    }

    private static ArrayList<Token> particionar(String cadena){
        ArrayList<Token> tokens = new ArrayList<>();
        StringBuilder acumulado = new StringBuilder();
        boolean dentroComillas = false;
        int i = 0;

        while (i < cadena.length()){
            char c = cadena.charAt(i);

            if (dentroComillas){
                if (c == '"'){
                    tokens.add(new Token(acumulado.toString(), Token.Tipo.CADENA_LITERAL));
                    acumulado.setLength(0);
                    dentroComillas = false;
                } else {
                    acumulado.append(c);
                }
                i++;
            }
            else if (c == '"'){
                dentroComillas = true;
                i++;
            }
            else if (c == '+'){
                tokens.add(new Token("+", Token.Tipo.OPERADOR_CONCATENACION));
                i++;
            }
            else if (Character.isWhitespace(c)){
                i++;
            }
            else if (esInicioIdentificador(c)){
                int inicio = i;
                while (i < cadena.length() && esParteIdentificador(cadena.charAt(i))){
                    i++;
                }
                tokens.add(new Token(cadena.substring(inicio, i), Token.Tipo.VARIABLE_CADENA));
            }
            else {
                mostrarError("Caracter no reconocido: '" + c + "'. Solo se permiten cadenas entre comillas, variables y el operador +.");
                return null;
            }
        }

        if (dentroComillas){
            mostrarError("Comillas sin cerrar.");
            return null;
        }
        if (tokens.isEmpty()){
            mostrarError("La cadena esta vacia.");
            return null;
        }
        return tokens;
    }

    private static String evaluarPila(){
        if (pila.estaVacia()){
            System.out.println("No hay tokens en la pila. Cargue primero una cadena (opcion 3).");
            return null;
        }

        String resultado = "";
        boolean esperaOperando = true;
        boolean primerToken = true;
        int paso = 0;

        System.out.println("\nAnalisis desde el tope:");
        while (!pila.estaVacia()){
            paso++;
            Token tope = pila.peep();

            if (esperaOperando){
                if (tope.esOperador()){
                    if (primerToken) mostrarError("La expresion termina en un operador: falta el operando de la derecha.");
                    else mostrarError("Dos operadores seguidos: falta un operando entre ellos.");
                    mostrarPilaRestante();
                    return null;
                }

                String valor = obtenerValor(tope);
                if (valor == null){
                    mostrarError("La variable '" + tope.getTexto() + "' no tiene un valor definido.");
                    mostrarPilaRestante();
                    return null;
                }

                pila.pop();
                resultado = valor + resultado;
                System.out.println("Paso " + paso + ": peep() -> " + tope + " es operando (" + tope.getDescripcionTipo() + "); pop()");
                System.out.println("         Acumulado: \"" + resultado + "\"");
                esperaOperando = false;
            }
            else {
                if (tope.esOperando()){
                    mostrarError("Dos operandos seguidos: falta un operador entre ellos (se encontro " + tope + ").");
                    mostrarPilaRestante();
                    return null;
                }

                pila.pop();
                System.out.println("Paso " + paso + ": peep() -> " + tope + " es operador; pop()");
                esperaOperando = true;
            }
            primerToken = false;
        }

        if (esperaOperando){
            mostrarError("Operador sin operando a su izquierda.");
            return null;
        }

        System.out.println("Fin: la pila quedo vacia. Expresion valida.");
        System.out.println("Resultado impreso: " + resultado);
        return resultado;
    }

    private static String obtenerValor(Token operando){
        if (operando.getTipo() == Token.Tipo.CADENA_LITERAL) return operando.getTexto();
        return variables.obtenerValor(operando.getTexto());
    }

    private static void mostrarError(String mensaje){
        System.out.println("ERROR: " + mensaje);
    }

    private static void mostrarPilaRestante(){
        System.out.println("El analisis se detuvo en este punto. Pila restante:");
        pila.mostrarPila();
    }
}
