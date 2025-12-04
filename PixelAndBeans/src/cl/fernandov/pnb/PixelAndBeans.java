package cl.fernandov.pnb;

import cl.fernandov.pnb.gui.LoginFrame;
import cl.fernandov.pnb.repository.IUsuarioRepository;
import cl.fernandov.pnb.repository.UsuarioRepositoryImpl; // Implementación JDBC REAL
import cl.fernandov.pnb.service.UsuarioService;
import cl.fernandov.pnb.controller.LoginController; // Clase para la lógica de login

import javax.swing.SwingUtilities;


public class PixelAndBeans {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // =========================================================
            // CONSTRUCCIÓN DE LA CADENA DE DEPENDENCIA (JDBC)
            // =========================================================

            // 1. REPOSITORIO (Capa de datos: Usa JDBC para hablar con MySQL)
            IUsuarioRepository usuarioRepo = new UsuarioRepositoryImpl();

            // 2. SERVICIO (Capa de lógica de negocio: Le inyectamos el Repositorio)
            UsuarioService usuarioService = new UsuarioService(usuarioRepo);

            // 3. CONTROLADOR (Capa de interacción: Le inyectamos el Servicio)
            LoginController loginController = new LoginController(usuarioService);

            // 4. GUI (Ventana: Le inyectamos el Controlador)
            // Esto utiliza el constructor modificado en LoginFrame.java
            new LoginFrame(loginController).setVisible(true);
        });
    }
}