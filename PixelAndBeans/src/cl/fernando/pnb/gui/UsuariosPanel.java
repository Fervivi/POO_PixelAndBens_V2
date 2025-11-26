package cl.fernando.pnb.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class UsuariosPanel extends JPanel {


    private JTextField searchField;
    private JButton newUserButton;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JComboBox<String> roleComboBox;
    private JCheckBox activeUserCheckBox;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;


    private final Color BACKGROUND_COLOR = new Color(51, 51, 51);
    private final Color TEXT_COLOR = Color.LIGHT_GRAY;
    private final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 12);

    public UsuariosPanel() {

        setBackground(BACKGROUND_COLOR);


        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(createSearchPanel(), gbc);


        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(createTablePanel(), gbc);


        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(createFormPanel(), gbc);


        configurarListeners();


        loadDummyData();
    }


    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setForeground(TEXT_COLOR);
        searchField = new JTextField(20);
        newUserButton = new JButton("Nuevo Usuario");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(20));
        searchPanel.add(newUserButton);

        return searchPanel;
    }


    private JScrollPane createTablePanel() {
        String[] columnNames = {"Usuario", "Nombre Completo", "Rol", "Activo"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);


        userTable.setBackground(new Color(75, 75, 75));
        userTable.setForeground(Color.WHITE);
        userTable.getTableHeader().setBackground(new Color(60, 60, 60));
        userTable.getTableHeader().setForeground(Color.WHITE);
        userTable.setSelectionBackground(new Color(0, 150, 200));
        userTable.setSelectionForeground(Color.WHITE);
        userTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.getViewport().setBackground(new Color(75, 75, 75));

        return scrollPane;
    }


    private JPanel createFormPanel() {
        JPanel formDetailsPanel = new JPanel(new GridBagLayout());
        formDetailsPanel.setOpaque(false);

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(8, 5, 8, 5);
        gbcForm.anchor = GridBagConstraints.WEST;


        JLabel usernameLabel = createLabel("Usuario:");
        usernameField = new JTextField(15);

        JLabel passwordLabel = createLabel("Contraseña:");
        passwordField = new JPasswordField(15);

        gbcForm.gridx = 0; gbcForm.gridy = 0; formDetailsPanel.add(usernameLabel, gbcForm);
        gbcForm.gridx = 1; formDetailsPanel.add(usernameField, gbcForm);
        gbcForm.gridx = 2; formDetailsPanel.add(passwordLabel, gbcForm);
        gbcForm.gridx = 3; formDetailsPanel.add(passwordField, gbcForm);


        JLabel fullNameLabel = createLabel("Nombre Completo:", new Color(255, 200, 0)); // Color anaranjado
        fullNameField = new JTextField(15);

        JLabel roleLabel = createLabel("Rol:");
        String[] roles = {"Admin", "Editor", "Viewer", "Invitado"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBackground(new Color(75, 75, 75));
        roleComboBox.setForeground(Color.WHITE);

        gbcForm.gridx = 0; gbcForm.gridy = 1; formDetailsPanel.add(fullNameLabel, gbcForm);
        gbcForm.gridx = 1; formDetailsPanel.add(fullNameField, gbcForm);
        gbcForm.gridx = 2; formDetailsPanel.add(roleLabel, gbcForm);
        gbcForm.gridx = 3; formDetailsPanel.add(roleComboBox, gbcForm);


        activeUserCheckBox = new JCheckBox("Usuario Activo");
        activeUserCheckBox.setForeground(TEXT_COLOR);
        activeUserCheckBox.setOpaque(false);

        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        deleteButton = new JButton("Eliminar");

        gbcForm.gridx = 0; gbcForm.gridy = 2;
        gbcForm.gridwidth = 2;
        formDetailsPanel.add(activeUserCheckBox, gbcForm);


        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionButtonsPanel.setOpaque(false);
        actionButtonsPanel.add(saveButton);
        actionButtonsPanel.add(cancelButton);
        actionButtonsPanel.add(deleteButton);

        gbcForm.gridx = 2; gbcForm.gridy = 2;
        gbcForm.gridwidth = 2;
        gbcForm.anchor = GridBagConstraints.EAST;
        formDetailsPanel.add(actionButtonsPanel, gbcForm);

        return formDetailsPanel;
    }


    private JLabel createLabel(String text) {
        return createLabel(text, TEXT_COLOR);
    }

    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(LABEL_FONT);
        return label;
    }


    private void loadDummyData() {
        tableModel.addRow(new Object[]{"f.perez", "Fernando Perez", "Admin", "Sí"});
        tableModel.addRow(new Object[]{"j.lopez", "Javier Lopez", "Editor", "Sí"});
        tableModel.addRow(new Object[]{"a.rojas", "Ana Rojas", "Viewer", "No"});
    }


    private void clearFormFields() {
        usernameField.setText("");
        passwordField.setText("");
        fullNameField.setText("");
        roleComboBox.setSelectedIndex(0);
        activeUserCheckBox.setSelected(true);
        userTable.clearSelection();
    }


    private void configurarListeners() {


        newUserButton.addActionListener(e -> clearFormFields());


        saveButton.addActionListener(e -> {

            if (usernameField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "Debe ingresar usuario y contraseña.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }


            JOptionPane.showMessageDialog(this,
                    "Usuario '" + usernameField.getText() + "' guardado/actualizado.",
                    "Guardar", JOptionPane.INFORMATION_MESSAGE);
            clearFormFields();
        });


        cancelButton.addActionListener(e -> clearFormFields());


        deleteButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow != -1) {
                String usernameToDelete = (String) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de eliminar al usuario '" + usernameToDelete + "'?",
                        "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedRow);
                    clearFormFields();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });


        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userTable.getSelectedRow() != -1) {
                int selectedRow = userTable.getSelectedRow();


                usernameField.setText((String) tableModel.getValueAt(selectedRow, 0));

                passwordField.setText("");
                fullNameField.setText((String) tableModel.getValueAt(selectedRow, 1));
                roleComboBox.setSelectedItem((String) tableModel.getValueAt(selectedRow, 2));
                activeUserCheckBox.setSelected(((String) tableModel.getValueAt(selectedRow, 3)).equals("Sí"));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestión de Usuarios (Demo)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(850, 700);
            frame.setLocationRelativeTo(null);


            frame.add(new UsuariosPanel());

            frame.setVisible(true);
        });
    }
}