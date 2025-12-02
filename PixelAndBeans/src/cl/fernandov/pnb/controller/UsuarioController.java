package cl.fernandov.pnb.controller;

import cl.fernandov.pnb.model.Usuario;
import cl.fernandov.pnb.service.UsuarioService;
import java.util.List;

/**
 * Controlador para la gestión de usuarios.
 * Intermedia entre la GUI (UsuariosPanel) y la lógica de negocio (UsuarioService).
 */
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Obtiene la lista completa de usuarios para la tabla de la GUI.
     */
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    /**
     * Busca usuarios usando el texto de búsqueda.
     * ESTE ES EL MÉTODO QUE FALTABA Y CAUSABA EL ERROR DE 'cannot find symbol'.
     */
    public List<Usuario> buscar(String textoBusqueda) {
        return usuarioService.buscar(textoBusqueda);
    }

    /**
     * Busca un usuario por su ID.
     */
    public Usuario buscarPorId(int id) {
        return usuarioService.buscarPorId(id);
    }

    /**
     * Llama al servicio para crear un nuevo usuario.
     */
    public int crearUsuario(String username, String password, String nombreCompleto, String rol) {
        return usuarioService.crear(username, password, nombreCompleto, rol);
    }

    /**
     * Llama al servicio para actualizar los datos de un usuario existente.
     */
    public void actualizarUsuario(int id, String nombreCompleto, String rol, boolean activo) {
        usuarioService.actualizar(id, nombreCompleto, rol, activo);
    }

    // Nota: La funcionalidad de 'eliminar' generalmente se maneja desactivando (inactivando) al usuario.
    // Si tu servicio tuviera un método 'eliminar', lo llamarías aquí.
}