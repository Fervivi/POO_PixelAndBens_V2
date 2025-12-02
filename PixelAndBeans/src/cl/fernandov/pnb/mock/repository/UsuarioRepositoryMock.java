package cl.fernandov.pnb.mock.repository;

import cl.fernandov.pnb.model.Usuario;
import cl.fernandov.pnb.repository.IUsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación Mock (en memoria) del repositorio de Usuario
 */
public class UsuarioRepositoryMock implements IUsuarioRepository {

    private final List<Usuario> usuarios;
    private int nextId;

    public UsuarioRepositoryMock() {
        usuarios = new ArrayList<>();
        nextId = 1;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        // Usuarios de ejemplo: ID 1 es ADMIN, ID 2, 3 son OPERADOR
        usuarios.add(new Usuario(nextId++, "admin", "admin123",  // ID 1
                "Administrador del Sistema", "ADMIN", true));
        usuarios.add(new Usuario(nextId++, "operador1", "op123",   // ID 2
                "Juan Pérez", "OPERADOR", true));
        usuarios.add(new Usuario(nextId++, "operador2", "op456",   // ID 3
                "María González", "OPERADOR", true));
        usuarios.add(new Usuario(nextId++, "cajero", "caj123",    // ID 4 (Inactivo)
                "Pedro Ramírez", "OPERADOR", false));
    }

    @Override
    public Usuario buscarPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarios.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> listarPorRol(String rol) {
        return usuarios.stream()
                .filter(u -> u.getRol().equalsIgnoreCase(rol))
                .collect(Collectors.toList());
    }

    @Override
    public int guardar(Usuario usuario) {
        usuario.setId(nextId++);
        usuarios.add(usuario);
        return usuario.getId();
    }

    @Override
    public void actualizar(Usuario usuario) {
        Usuario existente = buscarPorId(usuario.getId());
        if (existente != null) {
            int index = usuarios.indexOf(existente);
            usuarios.set(index, usuario); // Sobreescribir el objeto existente
        }
    }

    @Override
    public void eliminar(int id) {
        usuarios.removeIf(u -> u.getId() == id);
    }

    @Override
    public boolean existeUsername(String username) {
        return usuarios.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    @Override
    public int contarActivosPorRol(String rol) {
        return (int) usuarios.stream()
                .filter(u -> u.getRol().equalsIgnoreCase(rol))
                .filter(Usuario::isActivo)
                .count();
    }
}