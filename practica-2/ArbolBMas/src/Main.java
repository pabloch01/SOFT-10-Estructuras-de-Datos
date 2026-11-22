public class Main {

    public static void main(String[] args) {

        ArbolBMas arbol = new ArbolBMas(4);

        arbol.insertar(10, "Registro 10");
        arbol.insertar(20, "Registro 20");
        arbol.insertar(5, "Registro 5");
        arbol.insertar(6, "Registro 6");
        arbol.insertar(15, "Registro 15");
        arbol.insertar(30, "Registro 30");
        arbol.insertar(25, "Registro 25");
        arbol.insertar(40, "Registro 40");
        arbol.insertar(45, "Registro 45");
        arbol.insertar(50, "Registro 50");

        arbol.imprimirArbol();
    }
}
