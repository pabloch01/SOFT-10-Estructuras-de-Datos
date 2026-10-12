import java.util.ArrayList;

public class PilaTokens {

    private ArrayList<Token> pila;

    public PilaTokens(){
        pila = new ArrayList<>();
    }

    public int getTamanho(){
        return pila.size();
    }

    public boolean estaVacia(){
        return pila.isEmpty();
    }

    public void push(Token token){
        pila.add(token);
    }

    public Token pop(){
        if (estaVacia()){
            System.out.println("La pila esta vacia\n");
            return null;
        }
        return pila.removeLast();
    }

    public Token peep(){
        if (estaVacia()){
            System.out.println("La pila esta vacia\n");
            return null;
        }
        return pila.getLast();
    }
    
    public void mostrarPila(){
        if (estaVacia()){
            System.out.println("La pila esta vacia\n");
            return;
        }

        System.out.println("Pila (" + pila.size() + (pila.size() == 1 ? " elemento):" : " elementos):"));
        for (int i = pila.size() - 1; i >= 0; i--){
            Token token = pila.get(i);
            String marca = "";
            if (i == pila.size() - 1) marca = "  <- tope";
            else if (i == 0) marca = "  <- base";

            System.out.println("  [" + (i + 1) + "] " + token + "  (" + token.getDescripcionTipo() + ")" + marca);
        }
    }
}
