package cl.fernandov.pnb.controller;

import cl.fernandov.pnb.model.Usuario;
import cl.fernandov.pnb.service.UsuarioService; // Importa el servicio


public class LoginController {


    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    public Usuario autenticar(String username, String password) {

        return usuarioService.autenticar(username, password);
    }
}