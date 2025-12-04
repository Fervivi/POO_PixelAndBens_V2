package cl.fernandov.pnb.gui;

// Imports de la Arquitectura
import cl.fernandov.pnb.app.ApplicationContext; // Para obtener el controlador
import cl.fernandov.pnb.controller.LoginController;
import cl.fernandov.pnb.model.Usuario;

// Imports de la GUI
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class LoginFrame extends JFrame {

    private final LoginController loginController;


    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;


    private final Color COLOR_BG_MAIN = new Color(20, 20, 30);
    private final Color COLOR_CARD = new Color(35, 35, 50);
    private final Color COLOR_TEXT_FIELD_BG = new Color(50, 50, 65);
    private final Color COLOR_BLUE_PRIMARY = new Color(59, 130, 246);
    private final Color COLOR_TEXT_LIGHT = new Color(200, 200, 200);

    public LoginFrame() {

        this.loginController = ApplicationContext.getInstance().getLoginController();

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

    private JPanel crearLoginCardPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_CARD);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));
        panel.setPreferredSize(new Dimension(360, 420));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;


        JLabel titleLabel = new JLabel("Pixel & Bean", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(COLOR_BLUE_PRIMARY);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(titleLabel, gbc);


        JLabel subtitleLabel = new JLabel("INICIAR SESIÓN", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(COLOR_TEXT_LIGHT);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0);
        panel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(8, 0, 8, 0);


        gbc.gridy = 2;
        panel.add(createInputPanel("USUARIO", true), gbc);


        gbc.gridy = 3;
        panel.add(createInputPanel("CONTRASEÑA", false), gbc);

        gbc.gridy = 4;
        panel.add(Box.createVerticalStrut(10), gbc);


        loginButton = new JButton("INICIAR SESIÓN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(COLOR_BLUE_PRIMARY);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new EmptyBorder(12, 0, 12, 0));

        gbc.gridy = 5;
        gbc.insets = new Insets(20, 0, 20, 0);
        panel.add(loginButton, gbc);


        JButton exitButton = new JButton("Salir de la Aplicación");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 12));
        exitButton.setForeground(COLOR_TEXT_LIGHT);
        exitButton.setBackground(COLOR_CARD);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(exitButton, gbc);

        exitButton.addActionListener(e -> System.exit(0));

        return panel;
    }

    private JPanel createInputPanel(String labelText, boolean isUsername) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 10));
        label.setForeground(COLOR_TEXT_LIGHT);
        panel.add(label, BorderLayout.NORTH);

        JComponent field;

        if (isUsername) {
            userField = new JTextField(20);
            field = userField;
        } else {
            passwordField = new JPasswordField(20);
            field = passwordField;
        }

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(COLOR_TEXT_LIGHT);
        field.setBackground(COLOR_TEXT_FIELD_BG);
        field.setBorder(new LineBorder(COLOR_TEXT_FIELD_BG, 10));

        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void configurarListeners() {
        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passwordField.getPassword());

            try {

                Usuario usuarioLogeado = loginController.autenticar(user, pass);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}