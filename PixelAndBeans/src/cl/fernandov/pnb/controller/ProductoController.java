package cl.fernandov.pnb.controller;

import cl.fernandov.pnb.model.Producto;
import cl.fernandov.pnb.service.ProductoService;
import java.util.List;
import java.util.stream.Collectors; // <-- ¡ESTE IMPORT ES CRÍTICO!


public class ProductoController {

    private final ProductoService productoService;


    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    public List<Producto> listarActivos() {
        return productoService.listarActivos();
    }


    public List<Producto> listarTodos() {
        return productoService.listarTodos();
    }


    public int crearProducto(String nombre, String categoria, String tipo, double precio) {
        return productoService.crear(nombre, categoria, tipo, precio);
    }


    public void actualizarProducto(int id, String nombre, String categoria, String tipo, double precio, boolean activo) {
        productoService.actualizar(id, nombre, categoria, tipo, precio, activo);
    }


    public List<Producto> buscar(String textoBusqueda, String categoria) {

        List<Producto> productosFiltrados = productoService.listarActivos();


        if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
            final String busquedaLower = textoBusqueda.trim().toLowerCase();
            productosFiltrados = productosFiltrados.stream()

                    .filter(p -> p.getNombre().toLowerCase().contains(busquedaLower))
                    .collect(Collectors.toList());
        }


        if (categoria != null && !categoria.equalsIgnoreCase("TODOS")) {
            final String categoriaLower = categoria.trim().toLowerCase();
            productosFiltrados = productosFiltrados.stream()

                    .filter(p -> p.getCategoria().toLowerCase().contains(categoriaLower))
                    .collect(Collectors.toList());
        }


        return productosFiltrados;
    }
}