package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.*;
import ie.interfazgrafica.modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorConfiguracion {

    private final VentanaConfiguracion vista;

    public ControladorConfiguracion(VentanaConfiguracion vista) {
        this.vista = vista;
        inicializarEventos();

        // 🔹 Limpia las filas vacías iniciales del modelo de tabla
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaPersonajes().getModel();
        modelo.setRowCount(0);
    }

    private void inicializarEventos() {
        // Botones principales
        vista.getBtnIniciarBatalla().addActionListener(e -> iniciarBatalla());
        vista.getBtnCargarBatalla().addActionListener(e -> cargarBatalla());
        vista.getBtnSalir().addActionListener(e -> salir());

        // Checkboxes mutuamente excluyentes
        vista.getCkActivar1().addActionListener(e ->
                vista.getCkDesactivar().setSelected(!vista.getCkActivar1().isSelected()));
        vista.getCkDesactivar().addActionListener(e ->
                vista.getCkActivar1().setSelected(!vista.getCkDesactivar().isSelected()));

        // Estado inicial sugerido
        vista.getCkActivar1().setSelected(true);
        vista.getCkDesactivar().setSelected(false);

        // Botones de jugadores
        vista.getBtnAgregar().addActionListener(e -> agregarJugador());
        vista.getBtnEliminar().addActionListener(e -> eliminarJugador());
    }

    // ==========================================================
    // INICIAR BATALLA
    // ==========================================================
    private void iniciarBatalla() {
        try {
            // === Leer selección de apodos ===
            String apodoHeroe = (String) vista.getCbApodoHeroe().getSelectedItem();
            String apodoVillano = (String) vista.getCbApodoVillano().getSelectedItem();

            if (apodoHeroe == null || apodoVillano == null) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar ambos jugadores antes de continuar.",
                        "Faltan datos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // === Leer estadísticas del héroe ===
            int vidaHeroe = Integer.parseInt(vista.getTxtVidaHeroe().getText());
            int fuerzaHeroe = Integer.parseInt(vista.getTxtFuerzaHeroe().getText());
            int defensaHeroe = Integer.parseInt(vista.getTxtDefensaHeroe().getText());
            int bendicionHeroe = Integer.parseInt(vista.getTxtBendicionHeroe().getText());

            // === Leer estadísticas del villano ===
            int vidaVillano = Integer.parseInt(vista.getTxtVidaVillano().getText());
            int fuerzaVillano = Integer.parseInt(vista.getTxtFuerzaVillano().getText());
            int defensaVillano = Integer.parseInt(vista.getTxtDefensaVillano().getText());
            int bendicionVillano = Integer.parseInt(vista.getTxtBendicionVillano().getText());

            // === Crear objetos del modelo usando los apodos seleccionados ===
            Heroe heroe = new Heroe(apodoHeroe, vidaHeroe, fuerzaHeroe, defensaHeroe, bendicionHeroe);
            Villano villano = new Villano(apodoVillano, vidaVillano, fuerzaVillano, defensaVillano, bendicionVillano);

            // === Configuración general ===
            int cantidadBatallas = Integer.parseInt((String) vista.getCbCantidadBatallas().getSelectedItem());
            boolean ataquesSupremos = vista.getCkActivar1().isSelected();

            // === Confirmación ===
            JOptionPane.showMessageDialog(vista,
                    "Batalla lista:\nHéroe: " + heroe.getNombre() + "\nVillano: " + villano.getNombre());

            // === Abrir ventana de batalla ===
            VentanaBatalla ventanaBatalla = new VentanaBatalla();
            new ControladorBatalla(ventanaBatalla, heroe, villano, cantidadBatallas, ataquesSupremos);
            ventanaBatalla.setLocationRelativeTo(null);
            ventanaBatalla.setVisible(true);
            vista.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Verificá que todos los valores sean numéricos válidos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al iniciar la batalla: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // ==========================================================
    // BOTONES SECUNDARIOS
    // ==========================================================
    private void cargarBatalla() {
        JOptionPane.showMessageDialog(vista, "Funcionalidad de carga aún no implementada.");
    }

    private void salir() {
        vista.dispose();
    }

    // ==========================================================
    // GESTIÓN DE JUGADORES
    // ==========================================================
    private void agregarJugador() {
        String nombre = vista.getTxtNombre().getText().trim();
        String apodo = vista.getTxtApodo().getText().trim();
        boolean esHeroe = vista.getRbHeroe().isSelected();
        boolean esVillano = vista.getRbVillano().isSelected();

        // Validar campos vacíos
        if (nombre.isEmpty() || apodo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Completá nombre y apodo.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar tipo
        if (!esHeroe && !esVillano) {
            JOptionPane.showMessageDialog(vista, "Seleccioná si es Héroe o Villano.",
                    "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar apodo con tu clase modelo
        if (!ValidacionApodos.esValido(apodo)) {
            JOptionPane.showMessageDialog(vista,
                    "Apodo inválido. Debe tener entre 3 y 10 letras y solo espacios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaPersonajes().getModel();

        // Verificar duplicados
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object cell = modelo.getValueAt(i, 1);
            if (cell != null && cell.toString().equalsIgnoreCase(apodo)) {
                JOptionPane.showMessageDialog(vista, "Ese apodo ya está registrado.",
                        "Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String tipo = esHeroe ? "Héroe" : "Villano";
        modelo.addRow(new Object[]{nombre, apodo, tipo});

        // Actualizar combos
        if (esHeroe) vista.getCbApodoHeroe().addItem(apodo);
        else vista.getCbApodoVillano().addItem(apodo);

        // Limpiar campos
        vista.getTxtNombre().setText("");
        vista.getTxtApodo().setText("");
        vista.getRbHeroe().setSelected(false);
        vista.getRbVillano().setSelected(false);

        JOptionPane.showMessageDialog(vista, "Jugador agregado correctamente.");
    }

    private void eliminarJugador() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaPersonajes().getModel();
        int fila = vista.getTablaPersonajes().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Seleccioná una fila para eliminar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object apodoObj = modelo.getValueAt(fila, 1);
        Object tipoObj = modelo.getValueAt(fila, 2);

        if (apodoObj == null || tipoObj == null) {
            JOptionPane.showMessageDialog(vista,
                    "Esa fila contiene datos vacíos o inválidos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String apodo = apodoObj.toString();
        String tipo = tipoObj.toString();

        modelo.removeRow(fila);

        if ("Héroe".equalsIgnoreCase(tipo)) {
            vista.getCbApodoHeroe().removeItem(apodo);
        } else if ("Villano".equalsIgnoreCase(tipo)) {
            vista.getCbApodoVillano().removeItem(apodo);
        }

        JOptionPane.showMessageDialog(vista, "Jugador eliminado correctamente.");
    }
}
