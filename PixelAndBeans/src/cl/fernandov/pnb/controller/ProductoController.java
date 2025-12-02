package cl.fernandov.pnb.controller;

import cl.fernandov.pnb.model.Producto;
import cl.fernandov.pnb.service.ProductoService;
import java.util.List;
import java.util.stream.Collectors; // <-- ¡ESTE IMPORT ES CRÍTICO!

/**
 * Controlador para la gestión de productos.
 * Actúa como intermediario entre la GUI (ProductosPanel) y la lógica de negocio (ProductoService).
 */
public class ProductoController {

    private final ProductoService productoService;

    // Inyección de dependencia
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // =========================================================
    // MÉTODOS DE CONSULTA Y GESTIÓN
    // =========================================================

    /**
     * Obtiene todos los productos activos.
     */
    public List<Producto> listarActivos() {
        return productoService.listarActivos();
    }

    /**
     * Obtiene una lista de todos los productos (activos e inactivos).
     */
    public List<Producto> listarTodos() {
        return productoService.listarTodos();
    }

    /**
     * Crea un nuevo producto.
     * @return El ID del producto creado.
     */
    public int crearProducto(String nombre, String categoria, String tipo, double precio) {
        return productoService.crear(nombre, categoria, tipo, precio);
    }

    /**
     * Actualiza un producto existente.
     */
    public void actualizarProducto(int id, String nombre, String categoria, String tipo, double precio, boolean activo) {
        productoService.actualizar(id, nombre, categoria, tipo, precio, activo);
    }

    /**
     * Busca productos por nombre y/o categoría.
     * La lógica de filtrado se realiza aquí utilizando Streams de Java.
     */
    public List<Producto> buscar(String textoBusqueda, String categoria) {
        // 1. Obtener la lista base (productos activos)
        List<Producto> productosFiltrados = productoService.listarActivos();

        // 2. Filtrar por texto de búsqueda (Nombre)
        if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
            final String busquedaLower = textoBusqueda.trim().toLowerCase();
            productosFiltrados = productosFiltrados.stream()
                    // Mantiene productos cuyo nombre contiene el texto de búsqueda
                    .filter(p -> p.getNombre().toLowerCase().contains(busquedaLower))
                    .collect(Collectors.toList());
        }

        // 3. Filtrar por categoría
        if (categoria != null && !categoria.equalsIgnoreCase("TODOS")) {
            final String categoriaLower = categoria.trim().toLowerCase();
            productosFiltrados = productosFiltrados.stream()
                    // Mantiene productos cuya categoría coincide
                    .filter(p -> p.getCategoria().toLowerCase().contains(categoriaLower))
                    .collect(Collectors.toList());
        }

        // Devolver la lista final filtrada
        return productosFiltrados;
    }
}