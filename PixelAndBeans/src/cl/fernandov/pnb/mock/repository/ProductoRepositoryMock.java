package cl.fernandov.pnb.mock.repository;

import cl.fernandov.pnb.model.Producto;
import cl.fernandov.pnb.repository.IProductoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ProductoRepositoryMock implements IProductoRepository {

    private final List<Producto> productos;
    private int nextId;

    public ProductoRepositoryMock() {
        productos = new ArrayList<>();
        nextId = 1;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        // Bebidas
        productos.add(new Producto(nextId++, "Espresso", "BEBIDA", "CAFE", 2500, true));
        productos.add(new Producto(nextId++, "Cappuccino", "BEBIDA", "CAFE", 3000, true));
        productos.add(new Producto(nextId++, "Coca-Cola", "BEBIDA", "GASEOSA", 1500, true));

        // Snacks
        productos.add(new Producto(nextId++, "Brownie", "SNACK", "POSTRE", 2000, true));
        productos.add(new Producto(nextId++, "Cheesecake", "SNACK", "POSTRE", 2500, true));

        // Tiempo de Arcade
        productos.add(new Producto(nextId++, "30 minutos", "TIEMPO", "ARCADE", 2500, true));
        productos.add(new Producto(nextId++, "1 hora", "TIEMPO", "ARCADE", 4000, true));
    }

    @Override
    public Producto buscarPorId(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }

    @Override
    public List<Producto> listarActivos() {
        return productos.stream()
                .filter(Producto::isActivo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> listarPorCategoria(String categoria) {
        return productos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        String nombreLower = nombre.toLowerCase();
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());
    }

    @Override
    public int guardar(Producto producto) {
        producto.setId(nextId++);
        productos.add(producto);
        return producto.getId();
    }

    @Override
    public void actualizar(Producto producto) {
        Producto existente = buscarPorId(producto.getId());
        if (existente != null) {
            int index = productos.indexOf(existente);
            productos.set(index, producto);
        }
    }

    @Override
    public void eliminar(int id) {
        productos.removeIf(p -> p.getId() == id);
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            producto.setActivo(activo);
        }
    }
}