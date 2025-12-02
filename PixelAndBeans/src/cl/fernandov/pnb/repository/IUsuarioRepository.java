package cl.fernandov.pnb.repository;

import cl.fernandov.pnb.model.Usuario;
import java.util.List;

/**
 * Contrato de operaciones para acceso a datos de Usuario
 */
public interface IUsuarioRepository {

    Usuario buscarPorId(int id);
    Usuario buscarPorUsername(String username);
    List<Usuario> listarTodos();
    List<Usuario> listarPorRol(String rol);
    int guardar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(int id);
    boolean existeUsername(String username);
    int contarActivosPorRol(String rol);
}