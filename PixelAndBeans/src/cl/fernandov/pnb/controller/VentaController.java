package cl.fernandov.pnb.controller;

import cl.fernandov.pnb.model.Venta;
import cl.fernandov.pnb.model.Producto;
import cl.fernandov.pnb.service.VentaService;
import cl.fernandov.pnb.service.ProductoService;
import java.util.List;

/**
 * Controlador para el registro y consulta de ventas.
 */
public class VentaController {

    private final VentaService ventaService;
    private final ProductoService productoService;

    public VentaController(VentaService ventaService, ProductoService productoService) {
        this.ventaService = ventaService;
        this.productoService = productoService;
    }

    public List<Venta> listarVentasDelDia() {
        return ventaService.listarDelDia();
    }

    public double calcularTotalDelDia() {
        return ventaService.calcularTotalDelDia();
    }

    public List<Producto> listarProductosDisponibles() {
        return productoService.listarActivos();
    }

    public int registrarVenta(int usuarioId, String usuarioNombre, double total) {
        // En esta capa se coordina la lógica de negocio
        return ventaService.registrarVenta(usuarioId, usuarioNombre, total);
    }

    public void anularVenta(int id) {
        ventaService.anular(id);
    }
}