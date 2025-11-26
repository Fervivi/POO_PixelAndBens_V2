package cl.fernando.pnb.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

public class VentasPanel extends JPanel {


    private final Color BACKGROUND_COLOR = new Color(51, 51, 51);
    private final Color TEXT_COLOR = Color.LIGHT_GRAY;
    private final Color ACCENT_COLOR = new Color(0, 191, 255); // Azul/Cian


    private JTextField searchField;
    private JList<String> productList;
    private DefaultListModel<String> productListModel;
    private JTextField quantityField;
    private JButton addButton;
    private JButton removeButton;

    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JTable dailySalesTable;
    private DefaultTableModel dailySalesTableModel;

    private JTextField totalField;
    private JTextField dailyTotalField;
    private JButton confirmButton;
    private JButton cancelButton;


    private final DecimalFormat currencyFormat = new DecimalFormat("#,##0");

    public VentasPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));


        JLabel titleLabel = new JLabel("Registro de Nueva Venta", SwingConstants.CENTER);
        titleLabel.setForeground(ACCENT_COLOR);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);


        add(createMainContentPanel(), BorderLayout.CENTER);


        add(createDailySalesPanel(), BorderLayout.SOUTH);

        initializeData();
        configurarListeners();
    }


    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;


        JPanel productPanel = createProductSearchPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.gridheight = 4;
        mainPanel.add(productPanel, gbc);


        JPanel cartPanel = createCartPanel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        gbc.gridheight = 4;
        mainPanel.add(cartPanel, gbc);


        JPanel controlPanel = createControlPanel();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa todo el ancho
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(controlPanel, gbc);

        return mainPanel;
    }


    private JPanel createProductSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel searchLabel = createStyledLabel("Buscar:");
        searchField = new JTextField(10);
        JButton searchButton = new JButton("Buscar");

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; panel.add(searchLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; panel.add(searchField, gbc);
        gbc.gridx = 2; gbc.weightx = 0; panel.add(searchButton, gbc);


        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);

        productList.setBackground(new Color(75, 75, 75));
        productList.setForeground(Color.WHITE);
        productList.setSelectionBackground(ACCENT_COLOR);
        productList.setSelectionForeground(Color.WHITE);

        JScrollPane listScrollPane = new JScrollPane(productList);
        listScrollPane.setPreferredSize(new Dimension(200, 150));

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3; gbc.weighty = 1.0;
        panel.add(listScrollPane, gbc);


        JLabel quantityLabel = createStyledLabel("Cantidad:");
        quantityField = new JTextField("1", 3);
        addButton = new JButton("Agregar");

        gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0; gbc.weighty = 0;
        panel.add(quantityLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; panel.add(quantityField, gbc);
        gbc.gridx = 2; gbc.weightx = 0; panel.add(addButton, gbc);

        return panel;
    }


    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);


        String[] cartColumnNames = {"Producto", "Cantidad", "Precio Unit", "Subtotal"};
        cartTableModel = new DefaultTableModel(cartColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);


        cartTable.setBackground(new Color(75, 75, 75));
        cartTable.setForeground(Color.WHITE);
        cartTable.getTableHeader().setBackground(new Color(60, 60, 60));
        cartTable.getTableHeader().setForeground(Color.WHITE);
        cartTable.setSelectionBackground(ACCENT_COLOR);

        JScrollPane cartScrollPane = new JScrollPane(cartTable);


        removeButton = new JButton("Quitar Seleccionado");
        JPanel removeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        removeButtonPanel.setOpaque(false);
        removeButtonPanel.add(removeButton);

        panel.add(new JLabel("Detalle de la Venta", SwingConstants.CENTER), BorderLayout.NORTH); // Título
        panel.add(cartScrollPane, BorderLayout.CENTER);
        panel.add(removeButtonPanel, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        panel.setOpaque(false);


        JLabel totalLabel = createStyledLabel("Total:");
        totalField = new JTextField("0", 8);
        totalField.setEditable(false);
        totalField.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(totalLabel);
        panel.add(totalField);


        confirmButton = new JButton("Confirmar Venta");
        cancelButton = new JButton("Cancelar");

        panel.add(Box.createHorizontalStrut(50)); // Espacio
        panel.add(confirmButton);
        panel.add(cancelButton);

        return panel;
    }


    private JPanel createDailySalesPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);


        JLabel titleLabel = new JLabel("Ventas del Día", SwingConstants.LEFT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] dailyColumnNames = {"#", "Fecha/Hora", "Usuario", "Total", "Estado"};
        dailySalesTableModel = new DefaultTableModel(dailyColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        dailySalesTable = new JTable(dailySalesTableModel);


        dailySalesTable.setBackground(new Color(75, 75, 75));
        dailySalesTable.setForeground(Color.WHITE);
        dailySalesTable.getTableHeader().setBackground(new Color(60, 60, 60));
        dailySalesTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(dailySalesTable);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        panel.add(scrollPane, BorderLayout.CENTER);


        JPanel totalDayPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalDayPanel.setOpaque(false);
        JLabel dailyTotalLabel = createStyledLabel("Total del día:");
        dailyTotalField = new JTextField("0", 10);
        dailyTotalField.setHorizontalAlignment(SwingConstants.RIGHT);
        dailyTotalField.setEditable(false);

        totalDayPanel.add(dailyTotalLabel);
        totalDayPanel.add(dailyTotalField);
        panel.add(totalDayPanel, BorderLayout.SOUTH);

        return panel;
    }



    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        return label;
    }


    private void initializeData() {

        productListModel.addElement("Espresso | 2500");
        productListModel.addElement("Cappuccino | 3000");
        productListModel.addElement("Brownie | 2000");
        productListModel.addElement("15 min Arcade | 1500");

        dailySalesTableModel.addRow(new Object[]{1, "17:00", "admin", currencyFormat.format(5500), "Pagada"});
        dailySalesTableModel.addRow(new Object[]{2, "17:30", "admin", currencyFormat.format(7000), "Pagada"});

        calculateDailyTotal();
    }



    private void configurarListeners() {

        addButton.addActionListener(this::handleAddItemToCart);

        removeButton.addActionListener(this::handleRemoveItemFromCart);

        cancelButton.addActionListener(e -> clearCart());

        confirmButton.addActionListener(this::handleConfirmSale);


        productList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    quantityField.setText("1");
                    handleAddItemToCart(null);
                }
            }
        });
    }

    private void handleAddItemToCart(ActionEvent e) {
        String selectedItem = productList.getSelectedValue();
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String name = selectedItem.substring(0, selectedItem.indexOf('|')).trim();
        int price;
        try {
            price = Integer.parseInt(selectedItem.substring(selectedItem.indexOf('|') + 1).trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de precio en el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int subtotal = price * quantity;

        cartTableModel.addRow(new Object[]{
                name,
                quantity,
                currencyFormat.format(price),
                currencyFormat.format(subtotal)
        });

        calculateCartTotal();
    }

    private void handleRemoveItemFromCart(ActionEvent e) {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow != -1) {
            cartTableModel.removeRow(selectedRow);
            calculateCartTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un ítem del carrito para quitar.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleConfirmSale(ActionEvent e) {
        if (cartTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío. No se puede confirmar la venta.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }


        String totalText = totalField.getText().replace(".", "").replace("$", "");
        int totalSale;
        try {
            totalSale = Integer.parseInt(totalText);
        } catch (NumberFormatException ex) {
            totalSale = 0;
        }

        if (totalSale > 0) {

            JOptionPane.showMessageDialog(this, "Venta de $" + currencyFormat.format(totalSale) + " confirmada.", "Venta Exitosa", JOptionPane.INFORMATION_MESSAGE);


            dailySalesTableModel.addRow(new Object[]{
                    dailySalesTableModel.getRowCount() + 1,
                    java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")),
                    "admin",
                    currencyFormat.format(totalSale),
                    "Pagada"
            });

            calculateDailyTotal();
            clearCart();
        }
    }


    private void calculateCartTotal() {
        int total = 0;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {

            String subtotalStr = (String) cartTableModel.getValueAt(i, 3);
            try {

                subtotalStr = subtotalStr.replace(".", "").replace("$", "");
                total += Integer.parseInt(subtotalStr);
            } catch (NumberFormatException e) {

            }
        }
        totalField.setText(currencyFormat.format(total));
    }


    private void calculateDailyTotal() {
        int dailyTotal = 0;
        for (int i = 0; i < dailySalesTableModel.getRowCount(); i++) {
            // El Total es la columna 3.
            String totalStr = (String) dailySalesTableModel.getValueAt(i, 3);
            try {
                totalStr = totalStr.replace(".", "").replace("$", "");
                dailyTotal += Integer.parseInt(totalStr);
            } catch (NumberFormatException e) {

            }
        }
        dailyTotalField.setText("$" + currencyFormat.format(dailyTotal));
    }


    private void clearCart() {
        cartTableModel.setRowCount(0);
        totalField.setText("0");
        quantityField.setText("1");
        productList.clearSelection();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Registro de Nueva Venta (Demo)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 800);
            frame.setLocationRelativeTo(null);

            frame.add(new VentasPanel());

            frame.setVisible(true);
        });
    }
}