package cl.fernandov.pnb.gui;

import cl.fernandov.pnb.app.ApplicationContext;
import cl.fernandov.pnb.controller.UsuarioController;
import cl.fernandov.pnb.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class UsuariosPanel extends JPanel {


    private final UsuarioController usuarioController;


    private DefaultTableModel tableModel;
    private JTable usuarioTable;
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnActualizarEstado;


    private JTextField txtUsername;
    private JTextField txtNombreCompleto;
    private JComboBox<String> cmbRol;
    private JCheckBox chkActivo;


    private int selectedUserId = -1;


    private final String ROL_ADMIN = "ADMIN";
    private final String ROL_OPERADOR = "OPERADOR";


    public UsuariosPanel() {
        this.usuarioController = ApplicationContext.getInstance().getUsuarioController();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        configurarTabla();


        JPanel searchPanel = createSearchPanel();
        JPanel formPanel = createFormPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(usuarioTable), formPanel);
        splitPane.setResizeWeight(0.7);

        add(searchPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);


        configurarListeners();
        loadDataFromController();
        limpiarFormulario();
    }

    private void configurarTabla() {
        String[] columnNames = {"ID", "Username", "Nombre Completo", "Rol", "Activo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public int getUserIdAt(int row) {

                return (int) getValueAt(row, 0);
            }
        };
        usuarioTable = new JTable(tableModel);




        usuarioTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && usuarioTable.getSelectedRow() != -1) {
                cargarUsuarioSeleccionado();
            }
        });
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBusqueda = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnNuevo = new JButton("Nuevo");

        panel.add(new JLabel("Buscar Usuario:"));
        panel.add(txtBusqueda);
        panel.add(btnBuscar);
        panel.add(btnNuevo);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel titleLabel = new JLabel("Detalle del Usuario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);


        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        form.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtUsername = new JTextField(15);
        form.add(txtUsername, gbc);


        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        form.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNombreCompleto = new JTextField(15);
        form.add(txtNombreCompleto, gbc);


        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        form.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        cmbRol = new JComboBox<>(new String[]{ROL_ADMIN, ROL_OPERADOR});
        form.add(cmbRol, gbc);


        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        form.add(new JLabel("Activo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        chkActivo = new JCheckBox();
        form.add(chkActivo, gbc);


        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weighty = 1.0;
        form.add(new JPanel(), gbc);

        panel.add(form, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGuardar = new JButton("Guardar/Actualizar");
        btnCancelar = new JButton("Cancelar");
        btnActualizarEstado = new JButton("Cambiar Estado (Act/Inact)");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnActualizarEstado);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }


    private void loadDataFromController() {
        tableModel.setRowCount(0);

        try {
            List<Usuario> usuarios = usuarioController.listarTodos();

            for (Usuario u : usuarios) {
                tableModel.addRow(new Object[]{
                        u.getId(),
                        u.getUsername(),
                        u.getNombreCompleto(),
                        u.getRol(),
                        u.isActivo()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar usuarios: " + e.getMessage(),
                    "Error de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void cargarUsuarioSeleccionado() {
        int row = usuarioTable.getSelectedRow();
        if (row != -1) {
            try {

                selectedUserId = (int) tableModel.getValueAt(row, 0);
                Usuario usuario = usuarioController.buscarPorId(selectedUserId);

                if (usuario != null) {
                    txtUsername.setText(usuario.getUsername());
                    txtNombreCompleto.setText(usuario.getNombreCompleto());
                    cmbRol.setSelectedItem(usuario.getRol());
                    chkActivo.setSelected(usuario.isActivo());


                    txtUsername.setEditable(false);
                    btnActualizarEstado.setText(usuario.isActivo() ? "Desactivar Usuario" : "Activar Usuario");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al buscar usuario: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void guardarUsuario() {
        String username = txtUsername.getText();
        String nombre = txtNombreCompleto.getText();
        String rol = (String) cmbRol.getSelectedItem();
        boolean activo = chkActivo.isSelected();

        try {

            if (username.isEmpty() || nombre.isEmpty()) {
                throw new IllegalArgumentException("El username y el nombre completo son obligatorios.");
            }

            if (selectedUserId == -1) {



                String password = JOptionPane.showInputDialog(this, "Ingrese la contraseña para el nuevo usuario:");
                if (password == null || password.isEmpty()) {
                    throw new IllegalArgumentException("La contraseña es obligatoria.");
                }

                usuarioController.crearUsuario(username, password, nombre, rol);
                JOptionPane.showMessageDialog(this, "Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {

                usuarioController.actualizarUsuario(selectedUserId, nombre, rol, activo);
                JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            limpiarFormulario();
            loadDataFromController();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de Validación", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de Negocio", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error Inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void cambiarEstadoUsuario() {
        if (selectedUserId != -1) {
            try {

                usuarioController.actualizarUsuario(selectedUserId,
                        txtNombreCompleto.getText(),
                        (String) cmbRol.getSelectedItem(),
                        !chkActivo.isSelected());

                JOptionPane.showMessageDialog(this, "Estado del usuario cambiado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                loadDataFromController();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al cambiar el estado: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para cambiar su estado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void buscarUsuarios() {
        String texto = txtBusqueda.getText();
        tableModel.setRowCount(0);

        try {
            List<Usuario> usuarios = usuarioController.buscar(texto);

            for (Usuario u : usuarios) {
                tableModel.addRow(new Object[]{
                        u.getId(),
                        u.getUsername(),
                        u.getNombreCompleto(),
                        u.getRol(),
                        u.isActivo()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error en la búsqueda: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void limpiarFormulario() {
        txtUsername.setText("");
        txtNombreCompleto.setText("");
        cmbRol.setSelectedIndex(0);
        chkActivo.setSelected(true);
        selectedUserId = -1;
        txtUsername.setEditable(true);
        usuarioTable.clearSelection();
        btnActualizarEstado.setText("Cambiar Estado (Act/Inact)");
    }

    private void configurarListeners() {
        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnCancelar.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardarUsuario());
        btnActualizarEstado.addActionListener(e -> cambiarEstadoUsuario());
        btnBuscar.addActionListener(e -> buscarUsuarios());
    }
}