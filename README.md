# java-poo-taskmanager
Aplicación construida en java ant con patron de arquitectura MVC e interfáz gráfica java swing

# RESUMEN (JAVA SWING)
1️⃣ JFrame
2️⃣ JPanel
3️⃣ JLabel + JTextField
4️⃣ JButton
5️⃣ JTable
6️⃣ JComboBox
7️⃣ JScrollPane
8️⃣ Eventos (ActionListener)
9️⃣ LayoutManagers
🔟 Modelos (TableModel)

🧩 COMPONENTES PRINCIPALES:

🪟 Contenedores (Containers)
Son los componentes que contienen otros componentes.

🔹 JFrame
Ventana principal de la aplicación.
Es el contenedor raíz de la interfaz.
Ejemplo de uso: ventana principal del sistema.

🔹 JDialog
Ventana secundaria (modal o no modal).
Usada para formularios, confirmaciones, mensajes.

🔹 JPanel
Contenedor genérico.
Permite organizar componentes dentro de una ventana.
Se usa con LayoutManagers.

```code
buildFormPanel()
buildProjectPanel()
buildFiltersPanel()
buildBottomPanel()

```

🔹 JScrollPane
Contenedor con barras de desplazamiento.
Usado para tablas, listas, paneles grandes.

```code
new JScrollPane(table)
new JScrollPane(list)

```

🧱 COMPONENETES DE ENTRADA DE DATOS:

🔹 JTextField
Campo de texto de una sola línea.
Para ingresar nombres, códigos, fechas, etc.

🔹 JPasswordField
Campo de texto oculto.
Para contraseñas.

🔹 JTextArea
Campo de texto multilínea.
Para descripciones o comentarios largos.

🔹 JComboBox
Lista desplegable.
Para seleccionar una opción.

```code
private JComboBox<String> cboPrioridad;
private JComboBox<String> cboProyecto;
private JComboBox<String> cboEstado;
private JComboBox<String> cboFiltroPrioridad;

```

🔹 JCheckBox
Casilla de selección (verdadero/falso).

🔹 JRadioButton
Botones de opción excluyentes (se usan con ButtonGroup).

🎛️ COMPONENTES DE INTERACCIÓN:

🔹 JButton
Botón de acción.
Ejecuta eventos (ActionListener).

```code
JButton btnAgregar
JButton btnCrearProyecto
JButton btnAplicar
JButton btnLimpiar
JButton btnEliminar
JButton btnRecargar

```

🔹 JToggleButton
Botón con estado activo/inactivo.

📋 COMPONENTES DE VISUALIZACIÓN DE DATOS:

🔹 JLabel
Texto estático.
Para títulos, etiquetas y mensajes.

```code
private JLabel lblPendientes;
private JLabel lblCompletadas;

```

🔹 JTable
Tabla de datos.
Usada para mostrar listas estructuradas.
Usa modelos (TableModel).

```code
private JTable table;

```
jtable con modelo:
```code
private class TaskTableModel extends AbstractTableModel

```


🔹 JList
Lista simple de elementos.
```code
new JList<>(listModel)

```

🔹 JTree
Estructura jerárquica (árbol).

📊 COMPONENTES INFORMATIVOS:

🔹 JProgressBar
Barra de progreso.

🔹 JSlider
Selector de valores por rango.

🧭 COMPONENTES DE MENÚ:

🔹 JMenuBar
Barra de menú superior.

🔹 JMenu
Menú desplegable.

🔹 JMenuItem
Opción dentro del menú.

📐 LAYOUT MANAGER (gestión de diseño)
Controlan cómo se distribuyen los componentes en pantalla:

```code
setLayout(new BorderLayout());
add(panelTop, BorderLayout.NORTH);
add(panelFilters, BorderLayout.WEST);
add(scroll, BorderLayout.CENTER);
add(panelBottom, BorderLayout.SOUTH);

```

🔹 BorderLayout
Norte, Sur, Este, Oeste, Centro

🔹 FlowLayout
En línea (horizontal)
```code
new FlowLayout(FlowLayout.LEFT)
new FlowLayout(FlowLayout.RIGHT)

```

🔹 GridLayout
Matriz de filas/columnas

🔹 GridBagLayout
Diseño avanzado y flexible
```code
new JPanel(new GridBagLayout())

```

🔹 BoxLayout
Vertical u horizontal

🔹 GroupLayout
Usado por NetBeans GUI Builder

🎯 EVENTOS (Event Handling)

🔹 ActionListener
Eventos de botones

🔹 MouseListener
Eventos del mouse

🔹 KeyListener
Eventos de teclado

🔹 ChangeListener
Cambios de estado


