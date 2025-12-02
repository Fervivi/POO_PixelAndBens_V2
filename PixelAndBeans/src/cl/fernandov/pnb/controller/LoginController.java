package cl.fernandov.pnb.controller;

import cl.fernandov.pnb.model.Usuario;
import cl.fernandov.pnb.service.UsuarioService; // Importa el servicio

/**
 * Controlador específico para el proceso de Login.
 */
public class LoginController {

    // El LoginController necesita el UsuarioService para la lógica de autenticación
    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Intenta autenticar. Si falla, lanza una RuntimeException.
     * @return Usuario autenticado.
     */
    public Usuario autenticar(String username, String password) {
        // La lógica de negocio está en el servicio. El controlador solo la invoca.
        // ESTA ES LA LÍNEA CRÍTICA QUE ESTÁ FALLANDO PORQUE EL SERVICIO NO LA TENÍA.
        return usuarioService.autenticar(username, password);
    }
}