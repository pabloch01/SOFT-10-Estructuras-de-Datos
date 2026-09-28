public class Ticket {
    
    private static int cantidad = 0;
    private int id;
    private byte prioridad;
    private String descripcion;
    private String nombreCompleto;
    private String fechaCreacion;
    private String fechaResolucion;
    private Ticket siguiente;

    public Ticket(String nombreCompleto, String descripcion, byte prioridad, String fechaCreacion){
        cantidad++;
        this.id = cantidad;
        this.prioridad = prioridad;
        this.descripcion = descripcion;
        this.nombreCompleto = nombreCompleto;
        this.fechaCreacion = fechaCreacion;
        fechaResolucion = null;
        siguiente = null;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public byte getPrioridad(){
        return prioridad;
    }

    public String getNombreCompleto(){
        return nombreCompleto;
    }

    public String getFechaCreacion(){
        return fechaCreacion;
    }

    public String getFechaResolucion(){
        return fechaResolucion;
    }

    public int getId(){
        return id;
    }

    public Ticket getSiguiente(){
        return siguiente;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setPrioridad(byte prioridad){
        this.prioridad = prioridad;
    }

    public void setNombreCompleto(String nombreCompleto){
        this.nombreCompleto = nombreCompleto;
    }

    public void setFechaCreacion(String fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaResolucion(String fechaResolucion){
        this.fechaResolucion = fechaResolucion;
    }

    public void setSiguiente(Ticket siguiente){
        this.siguiente = siguiente;
    }

    private String textoPrioridad(){
        switch (prioridad){
            case 1: return "Alta";
            case 2: return "Media";
            case 3: return "Baja";
            default: return "Desconocida";
        }
    }

    public String toString(){
        String resolucion = (fechaResolucion == null) ? "Pendiente" : fechaResolucion;
        return "\nID del Ticket: " + id + "\nNombre completo del usuario: " + nombreCompleto + "\nPrioridad: " + textoPrioridad() + "\nFecha de creación: " + fechaCreacion + "\nDescripción: " + descripcion + "\nFecha de resolución: " + resolucion + "\n";
    }
}
