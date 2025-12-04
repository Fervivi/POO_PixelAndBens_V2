package cl.fernandov.pnb.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import cl.fernandov.pnb.model.Usuario;
import cl.fernandov.pnb.util.DatabaseConnectionFactory;

/**
 * Implementación del repositorio de Usuario usando JDBC (MySQL Connector/J).
 * Nota: Los métodos no enfocados en la autenticación devuelven valores de
 * PLACEHOLDER para permitir que el proyecto compile.
 */
public class UsuarioRepositoryImpl implements IUsuarioRepository {

    // --- Consultas SQL ---
    private static final String SQL_AUTENTICAR =
            "SELECT id, username, password, rol, activo, nombre_completo, email " +
                    "FROM usuario WHERE username = ? AND password = ? AND activo = TRUE";

    private static final String SQL_CONTAR_ACTIVOS_POR_ROL =
            "SELECT COUNT(*) FROM usuario WHERE rol = ? AND activo = TRUE";

    private static final String SQL_LISTAR_POR_ROL =
            "SELECT id, username, password, rol, activo, nombre_completo, email " +
                    "FROM usuario WHERE rol = ?";

    // ===============================================
    // 1. MÉTODO AUTENTICAR (CORREGIDO)
    // ===============================================

    @Override
    // ¡CORRECCIÓN AQUÍ! Cambiar 'String String' por 'String password'
    public Optional<Usuario> autenticar(String username, String password) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_AUTENTICAR)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = mapearUsuario(rs);
                    return Optional.of(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario en la BD: " + e.getMessage());
            throw new RuntimeException("Error en la capa de persistencia.", e);
        }
        return Optional.empty();
    }

    // ===============================================
    // 2. MÉTODOS REQUERIDOS (Contar y Listar)
    // ===============================================

    @Override
    public int contarActivosPorRol(String rol) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR_ACTIVOS_POR_ROL)) {

            ps.setString(1, rol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al contar activos por rol en la BD: " + e.getMessage());
            throw new RuntimeException("Error en la capa de persistencia (Contar).", e);
        }
        return 0;
    }

    @Override
    public List<Usuario> listarPorRol(String rol) {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_POR_ROL)) {

            ps.setString(1, rol);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar por rol en la BD: " + e.getMessage());
            throw new RuntimeException("Error en la capa de persistencia (Listar por Rol).", e);
        }
        return usuarios;
    }

    // ===============================================
    // 3. MÉTODOS PENDIENTES (Placeholders para Compilar)
    // ===============================================

    @Override
    public List<Usuario> listarTodos() {
        return Collections.emptyList(); // JDBC pendiente
    }

    @Override
    public Usuario buscarPorId(int id) {
        return null; // JDBC pendiente
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return null; // JDBC pendiente
    }

    @Override
    public int guardar(Usuario usuario) {
        return -1; // JDBC pendiente
    }

    @Override
    public void actualizar(Usuario usuario) {
        // JDBC pendiente
    }

    @Override
    public void eliminar(int id) {
        // JDBC pendiente
    }

    @Override
    public boolean existeUsername(String username) {
        return false; // JDBC pendiente
    }

    // ===============================================
    // 4. Mapeador auxiliar
    // ===============================================

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setRol(Usuario.Rol.valueOf(rs.getString("rol").toUpperCase()));
        u.setActivo(rs.getBoolean("activo"));
        u.setNombreCompleto(rs.getString("nombre_completo"));
        u.setEmail(rs.getString("email"));
        return u;
    }
}