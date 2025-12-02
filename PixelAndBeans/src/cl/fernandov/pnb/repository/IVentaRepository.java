package cl.fernandov.pnb.repository;

import cl.fernandov.pnb.model.Venta;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Contrato de operaciones para acceso a datos de Venta
 */
public interface IVentaRepository {

    Venta buscarPorId(int id);
    List<Venta> listarTodas();
    List<Venta> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta);
    List<Venta> listarDelDia();
    List<Venta> listarPorUsuario(int usuarioId);
    int guardar(Venta venta);
    void anular(int id);
    double calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta);
    double calcularTotalDelDia();
}