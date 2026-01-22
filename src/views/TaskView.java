package views;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

// import de controlador y modelo
import controller.TaskController;
import model.Project;
import model.Task;

public class TaskView extends JFrame {

    // Repositorio en memoria
    private final TaskController manager = new TaskController();

    // Formato fecha (entrada)
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    // UI - formulario
    private JTextField txtNombre;
    private JTextField txtFecha;
    private JComboBox<String> cboPrioridad;
    private JComboBox<String> cboProyecto;

    private JTextField txtNuevoProyecto;

    // UI - filtros
    private JTextField txtBuscar;
    private JComboBox<String> cboEstado;
    private JComboBox<String> cboFiltroPrioridad;

    // UI - resumen
    private JLabel lblPendientes;
    private JLabel lblCompletadas;

    // Tabla
    private JTable table;
    private TaskTableModel tableModel;

    public TaskView() {
        setTitle("Gestión de Tareas (Swing - En memoria)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        sdf.setLenient(false);

        initData();
        initComponents();
        refreshAll();
    }

    private void initData() {
        // Proyectos base
        manager.crearProyecto("General");
        manager.crearProyecto("Trabajo");
        manager.crearProyecto("Estudio");
    }

    private void initComponents() {
        // Panel superior: formulario + proyectos
        JPanel panelTop = new JPanel(new BorderLayout());

        panelTop.add(buildFormPanel(), BorderLayout.WEST);
        panelTop.add(buildProjectPanel(), BorderLayout.CENTER);

        // Panel filtros
        JPanel panelFilters = buildFiltersPanel();

        // Tabla
        tableModel = new TaskTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(24);

        // Ajuste de la columna "Completada" (checkbox)
        table.getColumnModel().getColumn(5).setPreferredWidth(90);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Tareas"));

        // Panel resumen + acciones
        JPanel panelBottom = buildBottomPanel();

        setLayout(new BorderLayout());
        add(panelTop, BorderLayout.NORTH);
        add(panelFilters, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private JPanel buildFormPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Nueva tarea"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        txtNombre = new JTextField(18);
        txtFecha = new JTextField(10);
        cboPrioridad = new JComboBox<>(new String[]{"ALTA", "MEDIA", "BAJA"});
        cboProyecto = new JComboBox<>();

        JButton btnAgregar = new JButton("Agregar tarea");

        int row = 0;
        c.gridx = 0; c.gridy = row; p.add(new JLabel("Nombre:"), c);
        c.gridx = 1; c.gridy = row; p.add(txtNombre, c); row++;

        c.gridx = 0; c.gridy = row; p.add(new JLabel("Fecha límite (yyyy-MM-dd):"), c);
        c.gridx = 1; c.gridy = row; p.add(txtFecha, c); row++;

        c.gridx = 0; c.gridy = row; p.add(new JLabel("Prioridad:"), c);
        c.gridx = 1; c.gridy = row; p.add(cboPrioridad, c); row++;

        c.gridx = 0; c.gridy = row; p.add(new JLabel("Project:"), c);
        c.gridx = 1; c.gridy = row; p.add(cboProyecto, c); row++;

        c.gridx = 1; c.gridy = row; p.add(btnAgregar, c);

        btnAgregar.addActionListener(e -> onAgregarTask());

        return p;
    }

    private JPanel buildProjectPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Proyectos"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        txtNuevoProyecto = new JTextField(18);
        JButton btnCrearProyecto = new JButton("Crear proyecto");

        c.gridx = 0; c.gridy = 0; p.add(new JLabel("Nuevo proyecto:"), c);
        c.gridx = 1; c.gridy = 0; p.add(txtNuevoProyecto, c);
        c.gridx = 2; c.gridy = 0; p.add(btnCrearProyecto, c);

        btnCrearProyecto.addActionListener(e -> onCrearProyecto());

        return p;
    }

    private JPanel buildFiltersPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Buscar / Filtrar"));
        p.setPreferredSize(new Dimension(260, 200));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        txtBuscar = new JTextField(14);
        cboEstado = new JComboBox<>(new String[]{"TODAS", "PENDIENTES", "COMPLETADAS"});
        cboFiltroPrioridad = new JComboBox<>(new String[]{"TODAS", "ALTA", "MEDIA", "BAJA"});

        JButton btnAplicar = new JButton("Aplicar filtros");
        JButton btnLimpiar = new JButton("Limpiar filtros");

        int row = 0;
        c.gridx = 0; c.gridy = row; p.add(new JLabel("Buscar (nombre):"), c); row++;
        c.gridx = 0; c.gridy = row; p.add(txtBuscar, c); row++;

        c.gridx = 0; c.gridy = row; p.add(new JLabel("Estado:"), c); row++;
        c.gridx = 0; c.gridy = row; p.add(cboEstado, c); row++;

        c.gridx = 0; c.gridy = row; p.add(new JLabel("Prioridad:"), c); row++;
        c.gridx = 0; c.gridy = row; p.add(cboFiltroPrioridad, c); row++;

        c.gridx = 0; c.gridy = row; p.add(btnAplicar, c); row++;
        c.gridx = 0; c.gridy = row; p.add(btnLimpiar, c);

        btnAplicar.addActionListener(e -> refreshAll());
        btnLimpiar.addActionListener(e -> {
            txtBuscar.setText("");
            cboEstado.setSelectedIndex(0);
            cboFiltroPrioridad.setSelectedIndex(0);
            refreshAll();
        });

        return p;
    }

    private JPanel buildBottomPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel summary = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPendientes = new JLabel("Pendientes: 0");
        lblCompletadas = new JLabel("Completadas: 0");

        summary.add(lblPendientes);
        summary.add(new JLabel(" | "));
        summary.add(lblCompletadas);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEliminar = new JButton("Eliminar seleccionada");
        JButton btnRecargar = new JButton("Recargar");

        actions.add(btnEliminar);
        actions.add(btnRecargar);

        btnEliminar.addActionListener(e -> onEliminarSeleccionada());
        btnRecargar.addActionListener(e -> refreshAll());

        p.add(summary, BorderLayout.WEST);
        p.add(actions, BorderLayout.EAST);

        return p;
    }

    private void onCrearProyecto() {
        String nombre = norm(txtNuevoProyecto.getText());

        // Regla de negocio: campo vacío
        if (nombre.isEmpty()) {
            showError("El nombre del proyecto no puede estar vacío.");
            return;
        }

        // Evitar duplicados simples
        if (manager.buscarProyectoPorNombre(nombre) != null) {
            showError("Ya existe un proyecto con ese nombre.");
            return;
        }

        manager.crearProyecto(nombre);
        txtNuevoProyecto.setText("");
        refreshProjectsCombo();
        showInfo("Project creado.");
    }

    private void onAgregarTask() {
        String nombre = norm(txtNombre.getText());
        String fechaTxt = norm(txtFecha.getText());
        String prioridadTxt = (String) cboPrioridad.getSelectedItem();
        String proyecto = (String) cboProyecto.getSelectedItem();

        // Regla de negocio: campos vacíos
        if (nombre.isEmpty() || fechaTxt.isEmpty() || prioridadTxt == null || proyecto == null) {
            showError("Debe completar Nombre, Fecha límite, Prioridad y Project.");
            return;
        }

        Date fecha;
        try {
            fecha = sdf.parse(fechaTxt);
        } catch (ParseException ex) {
            showError("Fecha inválida. Use formato yyyy-MM-dd (ej: 2026-02-15).");
            return;
        }

        // Regla de negocio: fecha límite debe ser superior al día actual
        if (!esFechaSuperiorAHoy(fecha)) {
            showError("La fecha límite debe ser superior al día actual.");
            return;
        }

        int prioridad = prioridadTextoAInt(prioridadTxt);
        manager.crearTask(nombre, fecha, prioridad, proyecto);

        // limpiar
        txtNombre.setText("");
        txtFecha.setText("");
        cboPrioridad.setSelectedIndex(0);
        cboProyecto.setSelectedIndex(0);

        refreshAll();
        showInfo("Tarea creada.");
    }

    private void onEliminarSeleccionada() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Seleccione una tarea para eliminar.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar la tarea ID " + id + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = manager.eliminarTask(id);
        if (!ok) {
            showError("No se pudo eliminar (no encontrada).");
            return;
        }
        refreshAll();
        showInfo("Tarea eliminada.");
    }

    private void refreshAll() {
        refreshProjectsCombo();
        refreshTableWithFilters();
        refreshSummary();
    }

    private void refreshProjectsCombo() {
        // actualizar proyectos en combo
        String selected = (String) cboProyecto.getSelectedItem();

        cboProyecto.removeAllItems();
        for (Project p : manager.getProyectos()) {
            cboProyecto.addItem(p.getNombre());
        }

        // intentar mantener selección
        if (selected != null) {
            for (int i = 0; i < cboProyecto.getItemCount(); i++) {
                if (selected.equalsIgnoreCase(cboProyecto.getItemAt(i))) {
                    cboProyecto.setSelectedIndex(i);
                    break;
                }
            }
        }

        // si no hay selección, usar el primero
        if (cboProyecto.getItemCount() > 0 && cboProyecto.getSelectedIndex() < 0) {
            cboProyecto.setSelectedIndex(0);
        }
    }

    private void refreshTableWithFilters() {
        ArrayList<Task> base = manager.getTodasLasTareas();
        ArrayList<Task> filtered = new ArrayList<>();

        String buscar = norm(txtBuscar.getText()).toLowerCase();
        String estado = (String) cboEstado.getSelectedItem();
        String prio = (String) cboFiltroPrioridad.getSelectedItem();

        for (Task t : base) {
            boolean ok = true;

            // buscar por nombre
            if (!buscar.isEmpty()) {
                String n = t.getNombre() == null ? "" : t.getNombre().toLowerCase();
                if (!n.contains(buscar)) ok = false;
            }

            // filtro estado
            if (ok && estado != null) {
                if ("PENDIENTES".equals(estado) && t.isCompletada()) ok = false;
                if ("COMPLETADAS".equals(estado) && !t.isCompletada()) ok = false;
            }

            // filtro prioridad
            if (ok && prio != null && !"TODAS".equals(prio)) {
                int val = prioridadTextoAInt(prio);
                if (t.getPrioridad() != val) ok = false;
            }

            if (ok) filtered.add(t);
        }

        tableModel.setData(filtered);
    }

    private void refreshSummary() {
        int pendientes = 0;
        int completadas = 0;

        for (Task t : manager.getTodasLasTareas()) {
            if (t.isCompletada()) completadas++;
            else pendientes++;
        }

        lblPendientes.setText("Pendientes: " + pendientes);
        lblCompletadas.setText("Completadas: " + completadas);
    }

    // ---------------- Helpers / reglas ----------------

    private static String norm(String s) {
        return s == null ? "" : s.trim();
    }

    private boolean esFechaSuperiorAHoy(Date fecha) {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaIngresada = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return fechaIngresada.isAfter(hoy); // estrictamente superior
    }

    private int prioridadTextoAInt(String prioridadTxt) {
        if ("ALTA".equalsIgnoreCase(prioridadTxt)) return 1;
        if ("MEDIA".equalsIgnoreCase(prioridadTxt)) return 2;
        if ("BAJA".equalsIgnoreCase(prioridadTxt)) return 3;
        return 2;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- TableModel ----------------

    private class TaskTableModel extends AbstractTableModel {
        private final String[] cols = {"ID", "Nombre", "Fecha límite", "Prioridad", "Project", "Completada"};
        private ArrayList<Task> data = new ArrayList<>();

        public void setData(ArrayList<Task> newData) {
            this.data = newData == null ? new ArrayList<>() : newData;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return cols.length;
        }

        @Override
        public String getColumnName(int column) {
            return cols[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 5) return Boolean.class; // checkbox
            return Object.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            // Solo permitir editar "Completada"
            return columnIndex == 5;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Task t = data.get(rowIndex);
            switch (columnIndex) {
                case 0: return t.getIdTask();
                case 1: return t.getNombre();
                case 2: return sdf.format(t.getFechaLimite());
                case 3: return t.prioridadComoTexto();
                case 4: return t.getNombreProyecto();
                case 5: return t.isCompletada();
                default: return "";
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex != 5) return;

            Task t = data.get(rowIndex);
            boolean nueva = (aValue instanceof Boolean) ? (Boolean) aValue : false;

            // Actualizar en el manager por ID (para mantener consistencia global)
            manager.cambiarEstadoCompletada(t.getIdTask(), nueva);

            // refrescar todo: tabla (por filtros) + resumen
            refreshAll();
        }
    }
}

