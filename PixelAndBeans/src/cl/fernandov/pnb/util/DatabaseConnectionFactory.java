// Ubicación: cl.fernandov.pnb.util.DatabaseConnectionFactory
package cl.fernandov.pnb.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionFactory {

    private static final Properties PROPS = new Properties();
    private static final String PROPERTIES_FILE = "application.properties";

    static {
        // Bloque estático: Se ejecuta una sola vez cuando la clase es cargada por el ClassLoader
        try (InputStream input = DatabaseConnectionFactory.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {

            if (input == null) {
                // Esto ocurre si el archivo no está en la carpeta 'resources'
                System.err.println("Error: No se encontró el archivo de propiedades: " + PROPERTIES_FILE);
                throw new RuntimeException("Fallo al inicializar la BD: Archivo de configuración no encontrado.");
            }

            // 1. Cargar las propiedades (URL, username, password)
            PROPS.load(input);

            // 2. Cargar el driver JDBC
            // Aunque JDBC 4.0+ lo hace automáticamente, esta línea asegura la carga
            Class.forName(PROPS.getProperty("db.driver"));

        } catch (Exception e) {
            System.err.println("Error FATAL al cargar configuración de la BD.");
            e.printStackTrace();
            // Lanza un error fatal que detiene la aplicación si la conexión falla al inicio
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Retorna una nueva conexión a la base de datos MySQL.
     * @return Objeto Connection.
     * @throws SQLException si falla la conexión (ej. MySQL no está corriendo).
     */
    public static Connection getConnection() throws SQLException {
        String url = PROPS.getProperty("db.url");
        String user = PROPS.getProperty("db.username");
        String pass = PROPS.getProperty("db.password");

        // DriverManager usa la información cargada para establecer la conexión
        return DriverManager.getConnection(url, user, pass);
    }
}