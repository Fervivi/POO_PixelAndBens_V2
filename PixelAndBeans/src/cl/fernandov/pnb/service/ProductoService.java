package cl.fernandov.pnb.service;

import cl.fernandov.pnb.model.Producto;
import cl.fernandov.pnb.repository.IProductoRepository;
import java.util.List;

/**
 * Servicio de lógica de negocio para Producto.
 * Contiene reglas de validación y flujos de actualización/creación.
 */
public class ProductoService {

    private final IProductoRepository repository;

    // Inyección de dependencia a través del constructor
    public ProductoService(IProductoRepository repository) {
        this.repository = repository;
    }

    // =========================================================
    // MÉTODOS PÚBLICOS DE NEGOCIO
    // =========================================================

    /**
     * Crea y guarda un nuevo producto después de validaciones.
     * @return El ID del producto creado.
     */
    public int crear(String nombre, String categoria, String tipo, double precio) {
        validarDatosProducto(nombre, categoria, tipo, precio);

        Producto producto = new Producto();
        producto.setNombre(nombre.trim());
        producto.setCategoria(categoria);
        producto.setTipo(tipo);
        producto.setPrecio(precio);
        producto.setActivo(true); // Nuevo producto siempre activo

        return repository.guardar(producto);
    }

    /**
     * Actualiza un producto existente.
     */
    public void actualizar(int id, String nombre, String categoria, String tipo, double precio, boolean activo) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de producto inválido para actualizar.");
        }
        validarDatosProducto(nombre, categoria, tipo, precio);

        Producto productoExistente = repository.buscarPorId(id);
        if (productoExistente == null) {
            throw new RuntimeException("Producto con ID " + id + " no encontrado.");
        }

        // Asignar nuevos valores
        productoExistente.setNombre(nombre.trim());
        productoExistente.setCategoria(categoria);
        productoExistente.setTipo(tipo);
        productoExistente.setPrecio(precio);
        productoExistente.setActivo(activo);

        repository.actualizar(productoExistente);
    }

    /**
     * Obtiene una lista de todos los productos (activos e inactivos).
     */
    public List<Producto> listarTodos() {
        return repository.listarTodos();
    }

    /**
     * Obtiene una lista de solo los productos activos.
     */
    public List<Producto> listarActivos() {
        return repository.listarActivos();
    }

    /**
     * Busca un producto por su ID.
     */
    public Producto buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de producto inválido.");
        }
        return repository.buscarPorId(id);
    }

    // =========================================================
    // VALIDACIONES INTERNAS
    // =========================================================

    /**
     * Contiene la lógica de validación para los datos de un producto.
     */
    private void validarDatosProducto(String nombre, String categoria, String tipo, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría es obligatoria.");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de producto es obligatorio.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser un valor positivo.");
        }
    }
}