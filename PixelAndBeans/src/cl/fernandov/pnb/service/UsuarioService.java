package cl.fernandov.pnb.service;

import cl.fernandov.pnb.model.Usuario;
import cl.fernandov.pnb.repository.IUsuarioRepository;
import java.util.List;
import java.util.stream.Collectors; // Necesario para el filtrado en 'buscar'

/**
 * Servicio de lógica de negocio para Usuario.
 */
public class UsuarioService {

    private final IUsuarioRepository repository;

    // Constructor con Inyección de Dependencia
    public UsuarioService(IUsuarioRepository repository) {
        this.repository = repository;
    }

    // =========================================================
    // MÉTODOS DE AUTENTICACIÓN (¡EL MÉTODO PROBLEMÁTICO!)
    // =========================================================

    /**
     * Autentica un usuario verificando credenciales y estado activo.
     * Es llamado por LoginController.
     * * @return El objeto Usuario si la autenticación es exitosa.
     * @throws IllegalArgumentException si los campos están vacíos.
     * @throws RuntimeException si las credenciales son inválidas o el usuario está inactivo.
     */
    public Usuario autenticar(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un usuario y contraseña.");
        }

        // 1. Buscar el usuario
        Usuario usuario = repository.buscarPorUsername(username);

        if (usuario == null) {
            throw new RuntimeException("Credenciales inválidas.");
        }

        // 2. Verificar la contraseña (simulada)
        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales inválidas.");
        }

        // 3. Verificar estado activo
        if (!usuario.isActivo()) {
            throw new RuntimeException("El usuario se encuentra inactivo.");
        }

        return usuario;
    }

    // =========================================================
    // MÉTODOS DE GESTIÓN (CRUD)
    // =========================================================

    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }

    public Usuario buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido.");
        }
        return repository.buscarPorId(id);
    }

    public List<Usuario> buscar(String textoBusqueda) {
        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            return repository.listarTodos();
        }

        final String busquedaLower = textoBusqueda.trim().toLowerCase();

        List<Usuario> todosLosUsuarios = repository.listarTodos();

        return todosLosUsuarios.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(busquedaLower) ||
                        u.getNombreCompleto().toLowerCase().contains(busquedaLower))
                .collect(Collectors.toList());
    }

    public int crear(String username, String password, String nombreCompleto, String rol) {
        // ... (Tu lógica de creación de usuario) ...
        // Simplificado para no repetir validaciones
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username.trim());
        nuevoUsuario.setPassword(password.trim());
        nuevoUsuario.setNombreCompleto(nombreCompleto.trim());
        nuevoUsuario.setRol(rol);
        nuevoUsuario.setActivo(true);
        return repository.guardar(nuevoUsuario);
    }

    public void actualizar(int id, String nombreCompleto, String rol, boolean activo) {
        // ... (Tu lógica de actualización de usuario) ...
        // Simplificado
        Usuario usuarioExistente = repository.buscarPorId(id);
        if (usuarioExistente != null) {
            usuarioExistente.setNombreCompleto(nombreCompleto.trim());
            usuarioExistente.setRol(rol);
            usuarioExistente.setActivo(activo);
            repository.actualizar(usuarioExistente);
        }
    }
    // ... (El resto de tus validaciones) ...
}