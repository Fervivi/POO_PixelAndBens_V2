package cl.fernandov.pnb.service;

import cl.fernandov.pnb.model.Venta;
import cl.fernandov.pnb.repository.IVentaRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de lógica de negocio para Venta
 */
public class VentaService {

    private final IVentaRepository repository;

    public VentaService(IVentaRepository repository) {
        this.repository = repository;
    }

    /**
     * Registra una nueva venta
     */
    public int registrarVenta(int usuarioId, String usuarioNombre, double total) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido.");
        }
        if (total <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a 0.");
        }

        // NOTA: En un sistema real, aquí se procesarían los ítems del carrito.

        Venta venta = new Venta();
        venta.setUsuarioId(usuarioId);
        venta.setUsuarioNombre(usuarioNombre);
        venta.setTotal(total);

        return repository.guardar(venta);
    }

    /**
     * Anula una venta
     */
    public void anular(int id) {
        Venta venta = repository.buscarPorId(id);
        if (venta == null) {
            throw new RuntimeException("Venta no encontrada.");
        }

        if ("ANULADA".equals(venta.getEstado())) {
            throw new RuntimeException("La venta ya está anulada.");
        }

        repository.anular(id);
    }

    // Métodos de consulta y reporte

    public List<Venta> listarDelDia() {
        return repository.listarDelDia();
    }

    public double calcularTotalDelDia() {
        return repository.calcularTotalDelDia();
    }

    public List<Venta> listarPorRango(LocalDateTime desde, LocalDateTime hasta) {
        return repository.listarPorRangoFechas(desde, hasta);
    }
}