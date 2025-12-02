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

/**
 * Panel para la gestión de usuarios (CRUD).
 * Implementa la Inyección de Dependencia para obtener el controlador.
 */
public class UsuariosPanel extends JPanel {

    // --- Inyección de Dependencia ---
    private final UsuarioController usuarioController;

    // --- Componentes de la GUI ---
    private DefaultTableModel tableModel;
    private JTable usuarioTable;
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnActualizarEstado;

    // Campos del formulario
    private JTextField txtUsername;
    private JTextField txtNombreCompleto;
    private JComboBox<String> cmbRol;
    private JCheckBox chkActivo;

    // Campo oculto para el ID del usuario seleccionado
    private int selectedUserId = -1;

    // Constantes de Rol
    private final String ROL_ADMIN = "ADMIN";
    private final String ROL_OPERADOR = "OPERADOR";


    public UsuariosPanel() {
        // 1. OBTENER EL CONTROLADOR
        this.usuarioController = ApplicationContext.getInstance().getUsuarioController();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 2. CONFIGURAR COMPONENTES
        configurarTabla();

        // 3. CONSTRUIR PANELES
        JPanel searchPanel = createSearchPanel();
        JPanel formPanel = createFormPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(usuarioTable), formPanel);
        splitPane.setResizeWeight(0.7);

        add(searchPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // 4. LISTENERS Y CARGA INICIAL
        configurarListeners();
        loadDataFromController(); // Carga real de datos
        limpiarFormulario(); // Inicialmente el formulario está limpio
    }

    private void configurarTabla() {
        String[] columnNames = {"ID", "Username", "Nombre Completo", "Rol", "Activo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas de la tabla no son editables
            }
            // Método para obtener el ID real (la tabla no mostrará el ID, pero lo usa internamente)
            public int getUserIdAt(int row) {
                // Asume que la columna 0 es el ID
                return (int) getValueAt(row, 0);
            }
        };
        usuarioTable = new JTable(tableModel);

        // Configuración para ocultar la columna ID si es necesario
        // usuarioTable.removeColumn(usuarioTable.getColumnModel().getColumn(0));

        // Listener para seleccionar una fila y cargar el formulario
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

        // Título del formulario
        JLabel titleLabel = new JLabel("Detalle del Usuario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Campos: Username
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        form.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtUsername = new JTextField(15);
        form.add(txtUsername, gbc);

        // Campos: Nombre Completo
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        form.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNombreCompleto = new JTextField(15);
        form.add(txtNombreCompleto, gbc);

        // Campos: Rol
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        form.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        cmbRol = new JComboBox<>(new String[]{ROL_ADMIN, ROL_OPERADOR});
        form.add(cmbRol, gbc);

        // Campos: Activo
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        form.add(new JLabel("Activo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        chkActivo = new JCheckBox();
        form.add(chkActivo, gbc);

        // Separador para empujar los botones hacia abajo
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weighty = 1.0;
        form.add(new JPanel(), gbc);

        panel.add(form, BorderLayout.CENTER);

        // Panel de botones (Sur)
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

    // =========================================================
    // LÓGICA DE NEGOCIO (Conexión al Controller)
    // =========================================================

    /**
     * Carga todos los usuarios activos en la JTable.
     */
    private void loadDataFromController() {
        tableModel.setRowCount(0); // Limpiar la tabla

        try {
            List<Usuario> usuarios = usuarioController.listarTodos();

            for (Usuario u : usuarios) {
                tableModel.addRow(new Object[]{
                        u.getId(), // El ID se usa internamente, no se muestra necesariamente
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

    /**
     * Carga el usuario seleccionado en el formulario.
     */
    private void cargarUsuarioSeleccionado() {
        int row = usuarioTable.getSelectedRow();
        if (row != -1) {
            try {
                // Obtener el ID de la columna 0 (que asumimos es el ID)
                selectedUserId = (int) tableModel.getValueAt(row, 0);
                Usuario usuario = usuarioController.buscarPorId(selectedUserId);

                if (usuario != null) {
                    txtUsername.setText(usuario.getUsername());
                    txtNombreCompleto.setText(usuario.getNombreCompleto());
                    cmbRol.setSelectedItem(usuario.getRol());
                    chkActivo.setSelected(usuario.isActivo());

                    // Solo se permite editar el username al crear, no al actualizar
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

    /**
     * Lógica para crear o actualizar un usuario.
     */
    private void guardarUsuario() {
        String username = txtUsername.getText();
        String nombre = txtNombreCompleto.getText();
        String rol = (String) cmbRol.getSelectedItem();
        boolean activo = chkActivo.isSelected();

        try {
            // Validación de campos básica
            if (username.isEmpty() || nombre.isEmpty()) {
                throw new IllegalArgumentException("El username y el nombre completo son obligatorios.");
            }

            if (selectedUserId == -1) {
                // CREAR NUEVO USUARIO

                // Nota: La contraseña debe ser manejada, aquí se usa una simulación
                String password = JOptionPane.showInputDialog(this, "Ingrese la contraseña para el nuevo usuario:");
                if (password == null || password.isEmpty()) {
                    throw new IllegalArgumentException("La contraseña es obligatoria.");
                }

                usuarioController.crearUsuario(username, password, nombre, rol);
                JOptionPane.showMessageDialog(this, "Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // ACTUALIZAR USUARIO EXISTENTE
                usuarioController.actualizarUsuario(selectedUserId, nombre, rol, activo);
                JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            limpiarFormulario();
            loadDataFromController(); // Refrescar la tabla
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de Validación", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de Negocio", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error Inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza el estado activo/inactivo del usuario.
     */
    private void cambiarEstadoUsuario() {
        if (selectedUserId != -1) {
            try {
                // El servicio maneja la lógica de si es activo o inactivo
                usuarioController.actualizarUsuario(selectedUserId,
                        txtNombreCompleto.getText(),
                        (String) cmbRol.getSelectedItem(),
                        !chkActivo.isSelected()); // Cambia el estado actual

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

    /**
     * Busca usuarios según el texto ingresado.
     */
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


    // =========================================================
    // UTILIDADES DE LA GUI
    // =========================================================

    private void limpiarFormulario() {
        txtUsername.setText("");
        txtNombreCompleto.setText("");
        cmbRol.setSelectedIndex(0);
        chkActivo.setSelected(true);
        selectedUserId = -1;
        txtUsername.setEditable(true); // Permite editar al crear
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