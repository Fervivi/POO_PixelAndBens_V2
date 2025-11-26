package cl.fernando.pnb.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private JLabel statusLabel;

    // --- Estilos Comunes ---
    private final Color BACKGROUND_COLOR = new Color(51, 51, 51);
    private final Color TEXT_COLOR = Color.LIGHT_GRAY;

    public MainFrame() {

        setTitle("Pixel & Bean – Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 700));

        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);


        JMenuBar menuBar = crearMenuBar();
        setJMenuBar(menuBar);


        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        add(contentPanel, BorderLayout.CENTER);

        add(crearStatusBar(), BorderLayout.SOUTH);

        mostrarPanelInicial();


    }


    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(60, 60, 60));


        JMenu fileMenu = crearMenu("Archivo", menuBar);
        crearMenuItem("Cerrar sesión", fileMenu, this::handleLogout);
        crearMenuItem("Salir", fileMenu, this::handleExit);


        JMenu managementMenu = crearMenu("Gestión", menuBar);
        crearMenuItem("Usuarios*", managementMenu, (e) -> mostrarPanel(new UsuariosPanel(), "Gestión de Usuarios"));

        crearMenuItem("Productos", managementMenu, (e) -> mostrarPanel(new ProductosPanel(), "Gestión de Productos"));


        JMenu operationMenu = crearMenu("Operación", menuBar);
        crearMenuItem("Ventas", operationMenu, (e) -> mostrarPanel(new VentasPanel(), "Registro de Ventas"));


        JMenu reportsMenu = crearMenu("Reportes", menuBar);
        crearMenuItem("Ventas del día", reportsMenu, null);
        crearMenuItem("Top productos", reportsMenu, null);


        JMenu eventsMenu = crearMenu("Eventos", menuBar);
        crearMenuItem("Torneos", eventsMenu, null);


        JMenu helpMenu = crearMenu("Ayuda", menuBar);
        crearMenuItem("Acerca de...", helpMenu, this::handleAbout);

        return menuBar;
    }

    private JMenu crearMenu(String name, JMenuBar menuBar) {
        JMenu menu = new JMenu(name);
        menu.setForeground(TEXT_COLOR);
        menu.setFont(new Font("SansSerif", Font.BOLD, 14));
        menuBar.add(menu);
        return menu;
    }

    private JMenuItem crearMenuItem(String name, JMenu parentMenu, ActionListener action) {
        JMenuItem item = new JMenuItem(name);
        item.setBackground(new Color(75, 75, 75));
        item.setForeground(TEXT_COLOR);
        item.setFont(new Font("SansSerif", Font.PLAIN, 12));
        if (action != null) {
            item.addActionListener(action);
        } else {
            item.addActionListener(e -> JOptionPane.showMessageDialog(this,
                    "Funcionalidad de '" + name + "' no implementada.", "Información", JOptionPane.INFORMATION_MESSAGE));
        }
        parentMenu.add(item);
        return item;
    }

    private JPanel crearStatusBar() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        statusPanel.setBackground(new Color(60, 60, 60));
        statusPanel.setPreferredSize(new Dimension(getWidth(), 30));

        statusLabel = new JLabel("Usuario: admin (sesión activa)");
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        statusPanel.add(statusLabel);
        return statusPanel;
    }


    private void mostrarPanel(JPanel newPanel, String title) {
        contentPanel.removeAll();
        contentPanel.add(newPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        statusLabel.setText("Módulo activo: " + title);
    }

    private void mostrarPanelInicial() {
        JLabel welcomeLabel = new JLabel("<html><h1 style='color:#00BFFF;'>Bienvenido al Sistema de Gestión Pixel & Bean</h1><p style='color:lightgray;'>Seleccione una opción del menú superior.</p></html>", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);
    }



    private void handleLogout(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de cerrar la sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }

    private void handleExit(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void handleAbout(ActionEvent e) {
        JOptionPane.showMessageDialog(this,
                "Pixel & Bean - Sistema de Gestión v1.0\nDesarrollado por Fernando.",
                "Acerca de...", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}