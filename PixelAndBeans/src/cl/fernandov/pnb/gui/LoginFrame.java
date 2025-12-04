package cl.fernandov.pnb.gui;

// Eliminamos el import: import cl.fernandov.pnb.app.ApplicationContext;
import cl.fernandov.pnb.controller.LoginController;
import cl.fernandov.pnb.model.Usuario;

// Imports de la GUI
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class LoginFrame extends JFrame {

    // Ahora es inicializado por el constructor (Inyección de Dependencia)
    private final LoginController loginController;


    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;


    private final Color COLOR_BG_MAIN = new Color(20, 20, 30);
    private final Color COLOR_CARD = new Color(35, 35, 50);
    private final Color COLOR_TEXT_FIELD_BG = new Color(50, 50, 65);
    private final Color COLOR_BLUE_PRIMARY = new Color(59, 130, 246);
    private final Color COLOR_TEXT_LIGHT = new Color(200, 200, 200);

    // =================================================================
    // EL CONSTRUCTOR CORREGIDO PARA RECIBIR EL CONTROLADOR
    // =================================================================
    public LoginFrame(LoginController loginController) { // << CAMBIO CLAVE AQUÍ
        this.loginController = loginController; // Asigna el controlador

        // --- El resto del constructor (GUI) permanece IGUAL ---
        setTitle("Pixel & Bean - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(COLOR_BG_MAIN);
        setLayout(new GridBagLayout());

        JPanel loginCardPanel = crearLoginCardPanel();
        add(loginCardPanel);

        pack();
        setLocationRelativeTo(null);


        configurarListeners();
    }
    // ... resto de métodos de la GUI (crearLoginCardPanel, createInputPanel) ...
    // Estos no cambian.

    private void configurarListeners() {
        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passwordField.getPassword());

            try {
                // El loginController ahora usa JDBC
                Usuario usuarioLogeado = loginController.autenticar(user, pass);

                // NOTA: Si MainFrame no recibe argumentos, te dará un error después de este.
                new MainFrame(usuarioLogeado).setVisible(true);
                dispose();

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Datos Incompletos",
                        JOptionPane.WARNING_MESSAGE);

            } catch (RuntimeException ex) {

                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error de Autenticación",
                        JOptionPane.ERROR_MESSAGE);
            }
            passwordField.setText("");
        });
    }

    // ELIMINAR el método main de esta clase si existe, ya que está en PixelAndBeans.java
}