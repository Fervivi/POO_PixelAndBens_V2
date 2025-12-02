package cl.fernandov.pnb.repository;

import cl.fernandov.pnb.model.Producto;
import java.util.List;

/**
 * Contrato de operaciones para acceso a datos de Producto
 */
public interface IProductoRepository {

    Producto buscarPorId(int id);
    List<Producto> listarTodos();
    List<Producto> listarActivos();
    List<Producto> listarPorCategoria(String categoria);
    List<Producto> buscarPorNombre(String nombre);
    int guardar(Producto producto);
    void actualizar(Producto producto);
    void eliminar(int id);
    void cambiarEstado(int id, boolean activo);
}