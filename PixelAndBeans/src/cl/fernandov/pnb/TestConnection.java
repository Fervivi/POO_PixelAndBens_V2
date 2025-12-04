package cl.fernandov.pnb; // Este debe ser tu paquete base

import java.sql.Connection;
import java.sql.SQLException;
import cl.fernandov.pnb.util.DatabaseConnectionFactory;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Intentando conectar a MySQL...");

        // Usamos try-with-resources para asegurar que la conexión se cierre automáticamente
        try (Connection conn = DatabaseConnectionFactory.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Conexión a base de datos exitosa.");
            } else {
                System.out.println("❌ Conexión fallida (Objeto Connection nulo).");
            }
        } catch (SQLException e) {
            // Este catch captura errores como "Access denied" o "Cannot connect"
            System.err.println("❌ Error de SQL. Revisa que MySQL (XAMPP) esté activo y las credenciales en application.properties.");
            e.printStackTrace();
        } catch (ExceptionInInitializerError e) {
            // Este catch captura errores si no se encuentra el archivo application.properties
            System.err.println("❌ Error de inicialización. Revisa que application.properties exista en 'resources'.");
            e.printStackTrace();
        }
    }
}