import java.util.ArrayList;
import java.util.List;

public class NodoArbolBMas {

    // Atributos
    private boolean esHoja;
    private List<Integer> claves;
    private List<NodoArbolBMas> hijos; // Vacía si es una hoja
    // datos (Puede ser simplemente un ArrayList de Strings. Debe ser null si es un nodo interno.)
    private List<String> datos;
    // siguiente (Es una instancia de la misma clase. Debe ser null si es un nodo interno.)
    private NodoArbolBMas siguiente;

    // Métodos
    // Constructor (Debe modificarse para que el nodo sea capaz de almacenar los datos.)
    public NodoArbolBMas(boolean esHoja) {
        this.esHoja = esHoja;
        this.claves = new ArrayList<>();    
        this.hijos = new ArrayList<>();
        if (!esHoja) this.datos = null;
        else this.datos = new ArrayList<>();
        siguiente = null;
    }

    // Getters
    public boolean esHoja() {
        return esHoja;
    }

    public List<Integer> getClaves() {
        return claves;
    }

    public List<NodoArbolBMas> getHijos() {
        return hijos;
    }

    public List<String> getDatos() {
        return datos;
    }

    public NodoArbolBMas getSiguiente() {
        return siguiente;
    }

    // Setters
    public void setClaves(List<Integer> claves) {
        this.claves = claves;
    }

    public void setHijos(List<NodoArbolBMas> hijos) {
        this.hijos = hijos;
    }

    public void setDatos(List<String> datos) {
        this.datos = datos;
    }

    public void setSiguiente(NodoArbolBMas siguiente) {
        this.siguiente = siguiente;
    }
}
