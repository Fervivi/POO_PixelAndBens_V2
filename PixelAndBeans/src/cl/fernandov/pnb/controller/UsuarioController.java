package cl.fernandov.pnb.controller;

import cl.fernandov.pnb.model.Usuario;
import cl.fernandov.pnb.service.UsuarioService;
import java.util.List;


public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }


    public List<Usuario> buscar(String textoBusqueda) {
        return usuarioService.buscar(textoBusqueda);
    }


    public Usuario buscarPorId(int id) {
        return usuarioService.buscarPorId(id);
    }


    public int crearUsuario(String username, String password, String nombreCompleto, String rol) {
        return usuarioService.crear(username, password, nombreCompleto, rol);
    }


    public void actualizarUsuario(int id, String nombreCompleto, String rol, boolean activo) {
        usuarioService.actualizar(id, nombreCompleto, rol, activo);
    }


}