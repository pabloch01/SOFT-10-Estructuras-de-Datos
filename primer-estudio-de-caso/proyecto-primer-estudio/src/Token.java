public class Token {

    public enum Tipo {
        CADENA_LITERAL,
        VARIABLE_CADENA,
        OPERADOR_CONCATENACION
    }

    private String texto;
    private Tipo tipo;

    public Token(String texto, Tipo tipo){
        this.texto = texto;
        this.tipo = tipo;
    }

    public String getTexto(){
        return texto;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }

    public void setTipo(Tipo tipo){
        this.tipo = tipo;
    }

    public boolean esOperando(){
        return tipo == Tipo.CADENA_LITERAL || tipo == Tipo.VARIABLE_CADENA;
    }

    public boolean esOperador(){
        return tipo == Tipo.OPERADOR_CONCATENACION;
    }

    public String getDescripcionTipo(){
        return switch (tipo){
            case CADENA_LITERAL -> "cadena literal";
            case VARIABLE_CADENA -> "variable de cadena";
            case OPERADOR_CONCATENACION -> "operador de concatenacion";
        };
    }

    public String toString(){
        if (tipo == Tipo.CADENA_LITERAL) return "\"" + texto + "\"";
        return texto;
    }
}
