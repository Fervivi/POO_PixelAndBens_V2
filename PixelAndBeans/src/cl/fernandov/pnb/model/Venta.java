package cl.fernandov.pnb.model;

import java.time.LocalDateTime;

public class Venta {

    private int id;
    private LocalDateTime fechaHora;
    private int usuarioId;
    private String usuarioNombre;
    private double total;
    private String estado;

    public Venta() {
        this.fechaHora = LocalDateTime.now();
        this.estado = "ACTIVA";
    }

    public Venta(int id, LocalDateTime fechaHora, int usuarioId, String usuarioNombre, double total, String estado) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.total = total;
        this.estado = estado;
    }


    public int getId() { return id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public int getUsuarioId() { return usuarioId; }
    public String getUsuarioNombre() { return usuarioNombre; }
    public double getTotal() { return total; }
    public String getEstado() { return estado; }


    public void setId(int id) { this.id = id; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }
    public void setTotal(double total) { this.total = total; }
    public void setEstado(String estado) { this.estado = estado; }
}