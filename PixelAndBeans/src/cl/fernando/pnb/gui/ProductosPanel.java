package cl.fernando.pnb.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosPanel extends JPanel {


    private final Color BACKGROUND_COLOR = new Color(51, 51, 51);
    private final Color TEXT_COLOR = Color.LIGHT_GRAY;
    private final Color ACCENT_COLOR = new Color(0, 191, 255); // Azul/Cian
    private final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 12);


    private JTextField searchField;
    private JComboBox<String> categoryFilterComboBox;
    private JButton newProductButton;


    private JTable productTable;
    private DefaultTableModel tableModel;

    private JTextField idField;
    private JTextField nameField;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> typeComboBox;
    private JTextField priceField;
    private JCheckBox activeCheckBox;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JButton changeStateButton;


    private Map<String, List<String>> productTypes = new HashMap<>();

    public ProductosPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));


        initializeProductTypes();


        add(createNorthPanel(), BorderLayout.NORTH);


        add(createCenterPanel(), BorderLayout.CENTER);


        initializeData();
        configurarListeners();
        clearFormFields();
    }


    private JPanel createNorthPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setOpaque(false);

        JLabel searchLabel = createStyledLabel("Buscar:");
        searchField = new JTextField(15);
        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(30, 25));

        JLabel filterLabel = createStyledLabel("Filtro Categoría:");
        categoryFilterComboBox = new JComboBox<>(new String[]{"Todas", "BEBIDA", "SNACK", "TIEMPO"});
        categoryFilterComboBox.setBackground(new Color(75, 75, 75));
        categoryFilterComboBox.setForeground(TEXT_COLOR);

        newProductButton = new JButton("Nuevo");

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(Box.createHorizontalStrut(20)); // Espacio
        panel.add(filterLabel);
        panel.add(categoryFilterComboBox);
        panel.add(Box.createHorizontalStrut(20)); // Espacio
        panel.add(newProductButton);

        return panel;
    }


    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;


        JPanel tableContainer = createTablePanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        centerPanel.add(tableContainer, gbc);


        JPanel formContainer = createFormPanel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        centerPanel.add(formContainer, gbc);

        return centerPanel;
    }


    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        String[] columnNames = {"ID", "Nombre", "Categoría", "Precio", "Tipo", "Activo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);


        productTable.setBackground(new Color(75, 75, 75));
        productTable.setForeground(TEXT_COLOR);
        productTable.getTableHeader().setBackground(new Color(60, 60, 60));
        productTable.getTableHeader().setForeground(TEXT_COLOR);
        productTable.setSelectionBackground(ACCENT_COLOR);
        productTable.setSelectionForeground(Color.WHITE);
        productTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.getViewport().setBackground(new Color(75, 75, 75));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }


    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        idField = new JTextField(5);
        idField.setEditable(false);
        idField.setVisible(false);


        JLabel nameLabel = createStyledLabel("Nombre:");
        nameField = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 0; panel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(nameField, gbc);


        JLabel categoryLabel = createStyledLabel("Categoría:");
        categoryComboBox = new JComboBox<>(new String[]{"", "BEBIDA", "SNACK", "TIEMPO"});
        categoryComboBox.setBackground(new Color(75, 75, 75));
        categoryComboBox.setForeground(TEXT_COLOR);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; panel.add(categoryLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(categoryComboBox, gbc);

        JLabel typeLabel = createStyledLabel("Tipo:");
        typeComboBox = new JComboBox<>();
        typeComboBox.setBackground(new Color(75, 75, 75));
        typeComboBox.setForeground(TEXT_COLOR);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; panel.add(typeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(typeComboBox, gbc);


        JLabel priceLabel = createStyledLabel("Precio:");
        priceField = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; panel.add(priceLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2; panel.add(priceField, gbc);


        activeCheckBox = new JCheckBox("Activo");
        activeCheckBox.setForeground(TEXT_COLOR);
        activeCheckBox.setOpaque(false);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3; panel.add(activeCheckBox, gbc);


        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        deleteButton = new JButton("Eliminar");
        changeStateButton = new JButton("Cambiar Estado");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(changeStateButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        return panel;
    }



    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(LABEL_FONT);
        return label;
    }


    private void initializeProductTypes() {
        productTypes.put("BEBIDA", List.of("Café", "Té", "Gaseosa", "Agua"));
        productTypes.put("SNACK", List.of("Galletas", "Pastel", "Fruta", "Sándwich"));
        productTypes.put("TIEMPO", List.of("15 min Arcade", "30 min Arcade", "1 hora Consola"));
    }


    private void initializeData() {

        tableModel.addRow(new Object[]{1, "Espresso", "BEBIDA", 2500, "Café", true});
        tableModel.addRow(new Object[]{2, "Cappuccino", "BEBIDA", 3000, "Café", true});
        tableModel.addRow(new Object[]{3, "Brownie", "SNACK", 2000, "Pastel", true});
        tableModel.addRow(new Object[]{4, "15 min Arcade", "TIEMPO", 1500, "15 min Arcade", true});
        tableModel.addRow(new Object[]{5, "Té Verde", "BEBIDA", 2200, "Té", false});
        tableModel.addRow(new Object[]{6, "Papas Fritas", "SNACK", 1200, "Snack", true});
    }


    private void clearFormFields() {
        idField.setText("");
        nameField.setText("");
        categoryComboBox.setSelectedIndex(0);
        typeComboBox.removeAllItems();
        priceField.setText("");
        activeCheckBox.setSelected(true);
        productTable.clearSelection();
    }


    private boolean isValidPrice(String price) {
        try {
            int p = Integer.parseInt(price);
            return p >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }




    private void configurarListeners() {

        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            typeComboBox.removeAllItems();

            if (selectedCategory != null && productTypes.containsKey(selectedCategory)) {
                for (String type : productTypes.get(selectedCategory)) {
                    typeComboBox.addItem(type);
                }
            }
        });


        newProductButton.addActionListener(e -> clearFormFields());


        saveButton.addActionListener(e -> {

            if (nameField.getText().isEmpty() || priceField.getText().isEmpty() || !isValidPrice(priceField.getText())) {
                JOptionPane.showMessageDialog(this, "Nombre y Precio (número válido) son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = idField.getText();
            String name = nameField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            String type = (String) typeComboBox.getSelectedItem();
            int price = Integer.parseInt(priceField.getText());
            boolean active = activeCheckBox.isSelected();

            if (id.isEmpty()) {
                int newId = tableModel.getRowCount() + 1;
                tableModel.addRow(new Object[]{newId, name, category, price, type, active});
            } else {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).toString().equals(id)) {
                        tableModel.setValueAt(name, i, 1);
                        tableModel.setValueAt(category, i, 2);
                        tableModel.setValueAt(price, i, 3);
                        tableModel.setValueAt(type, i, 4);
                        tableModel.setValueAt(active, i, 5);
                        break;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            clearFormFields();
        });


        cancelButton.addActionListener(e -> clearFormFields());


        deleteButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este producto?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Producto eliminado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    clearFormFields();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });


        changeStateButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                boolean currentState = (boolean) tableModel.getValueAt(selectedRow, 5);
                tableModel.setValueAt(!currentState, selectedRow, 5); // Alternar estado
                activeCheckBox.setSelected(!currentState); // Actualizar checkbox
                JOptionPane.showMessageDialog(this, "Estado del producto cambiado.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto para cambiar su estado.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });


        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && productTable.getSelectedRow() != -1) {
                int selectedRow = productTable.getSelectedRow();
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                categoryComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
                typeComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString()); // Asegurarse que la categoría cargue el tipo
                priceField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                activeCheckBox.setSelected((boolean) tableModel.getValueAt(selectedRow, 5));
            }
        });


        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterProducts(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterProducts(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterProducts(); }
        });


        categoryFilterComboBox.addActionListener(e -> filterProducts());
    }


    private void filterProducts() {
        String searchText = searchField.getText().toLowerCase();
        String selectedFilterCategory = (String) categoryFilterComboBox.getSelectedItem();


        tableModel.setRowCount(0);

        for (int i = 0; i < tableModel.getRowCount(); i++) {
        }


        List<Object[]> originalData = getOriginalProductData(); // Método para obtener datos originales
        for (Object[] row : originalData) {
            String name = ((String) row[1]).toLowerCase();
            String category = (String) row[2];

            boolean matchesSearch = searchText.isEmpty() || name.contains(searchText);
            boolean matchesCategory = selectedFilterCategory.equals("Todas") || category.equals(selectedFilterCategory);

            if (matchesSearch && matchesCategory) {
                tableModel.addRow(row);
            }
        }

        if (searchField.getText().isEmpty() && selectedFilterCategory.equals("Todas") && tableModel.getRowCount() == 0) {

            initializeData();
        }
    }

    // Método temporal para simular datos originales (en una aplicación real, sería una List<Producto>)
    private List<Object[]> getOriginalProductData() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{1, "Espresso", "BEBIDA", 2500, "Café", true});
        data.add(new Object[]{2, "Cappuccino", "BEBIDA", 3000, "Café", true});
        data.add(new Object[]{3, "Brownie", "SNACK", 2000, "Pastel", true});
        data.add(new Object[]{4, "15 min Arcade", "TIEMPO", 1500, "15 min Arcade", true});
        data.add(new Object[]{5, "Té Verde", "BEBIDA", 2200, "Té", false});
        data.add(new Object[]{6, "Papas Fritas", "SNACK", 1200, "Snack", true});
        return data;
    }


    // --- Método Principal de Prueba (Temporal) ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestión de Productos (Demo)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);

            frame.add(new ProductosPanel());

            frame.setVisible(true);
        });
    }
}