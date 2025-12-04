package cl.fernandov.pnb.model;

public class Producto {

    private int id;
    private String nombre;
    private String categoria;
    private String tipo;
    private double precio;
    private boolean activo;


    public Producto() {}


    public Producto(int id, String nombre, String categoria, String tipo, double precio, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tipo = tipo;
        this.precio = precio;
        this.activo = activo;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }


    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }


    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }


    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }


    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}