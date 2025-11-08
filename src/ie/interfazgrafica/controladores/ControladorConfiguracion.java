package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.*;
import ie.interfazgrafica.modelo.*;
import javax.swing.*;

public class ControladorConfiguracion {

    private final VentanaConfiguracion vista;

    public ControladorConfiguracion(VentanaConfiguracion vista) {
        this.vista = vista;
        inicializarEventos();
    }

    private void inicializarEventos() {
        // Botones principales
        vista.getBtnIniciarBatalla().addActionListener(e -> iniciarBatalla());
        vista.getBtnCargarBatalla().addActionListener(e -> cargarBatalla());
        vista.getBtnSalir().addActionListener(e -> salir());

        // Hacer checks mutuamente excluyentes
        vista.getCkActivar1().addActionListener(e ->
                vista.getCkDesactivar().setSelected(!vista.getCkActivar1().isSelected()));
        vista.getCkDesactivar().addActionListener(e ->
                vista.getCkActivar1().setSelected(!vista.getCkDesactivar().isSelected()));

        // Estado inicial sugerido: Activar ON
        vista.getCkActivar1().setSelected(true);
        vista.getCkDesactivar().setSelected(false);

        // Eventos de registro de jugadores
        vista.getBtnAgregar().addActionListener(e -> agregarJugador());
        vista.getBtnEliminar().addActionListener(e -> eliminarJugador());
    }

    // ==========================================================
    // INICIAR BATALLA
    // ==========================================================
    private void iniciarBatalla() {
        try {
            // === Leer valores del HÉROE ===
            String nombreHeroe = (String) vista.getCbApodoHeroe().getSelectedItem();
            String sVidaHeroe = vista.getTxtVidaHeroe().getText().trim();
            String sFuerzaHeroe = vista.getTxtFuerzaHeroe().getText().trim();
            String sDefHeroe = vista.getTxtDefensaHeroe().getText().trim();
            String sBendHeroe = vista.getTxtBendicionHeroe().getText().trim();

            // === Leer valores del VILLANO ===
            String nombreVillano = (String) vista.getCbApodoVillano().getSelectedItem();
            String sVidaVillano = vista.getTxtVidaVillano().getText().trim();
            String sFuerzaVillano = vista.getTxtFuerzaVillano().getText().trim();
            String sDefVillano = vista.getTxtDefensaVillano().getText().trim();
            String sBendVillano = vista.getTxtBendicionVillano().getText().trim();

            // === Validar campos vacíos ===
            if (nombreHeroe == null || nombreVillano == null ||
                sVidaHeroe.isEmpty() || sFuerzaHeroe.isEmpty() || sDefHeroe.isEmpty() || sBendHeroe.isEmpty() ||
                sVidaVillano.isEmpty() || sFuerzaVillano.isEmpty() || sDefVillano.isEmpty() || sBendVillano.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Completá todos los campos y seleccioná ambos apodos.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // === Convertir a números ===
            int vidaHeroe = Integer.parseInt(sVidaHeroe);
            int fuerzaHeroe = Integer.parseInt(sFuerzaHeroe);
            int defensaHeroe = Integer.parseInt(sDefHeroe);
            int bendicionHeroe = Integer.parseInt(sBendHeroe);

            int vidaVillano = Integer.parseInt(sVidaVillano);
            int fuerzaVillano = Integer.parseInt(sFuerzaVillano);
            int defensaVillano = Integer.parseInt(sDefVillano);
            int bendicionVillano = Integer.parseInt(sBendVillano);

            // === Validaciones de rango ===
            if (vidaHeroe <= 0 || vidaVillano <= 0) {
                JOptionPane.showMessageDialog(vista, "La vida debe ser mayor a 0.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (fuerzaHeroe < 0 || defensaHeroe < 0 || fuerzaVillano < 0 || defensaVillano < 0) {
                JOptionPane.showMessageDialog(vista, "Fuerza y defensa no pueden ser negativas.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bendicionHeroe < 0 || bendicionHeroe > 100 || bendicionVillano < 0 || bendicionVillano > 100) {
                JOptionPane.showMessageDialog(vista, "La bendición debe estar entre 0 y 100.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // === Leer cantidad de batallas ===
            String sel = (String) vista.getCbCantidadBatallas().getSelectedItem();
            if (sel == null || sel.trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Elegí la cantidad de batallas (2, 3 o 5).", "Dato requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int cantidadBatallas = Integer.parseInt(sel.trim());

            // === Verificar ataques supremos ===
            boolean ataquesSupremos = vista.getCkActivar1().isSelected() && !vista.getCkDesactivar().isSelected();

            // === Crear personajes con valores separados ===
            Heroe heroe = new Heroe(nombreHeroe, vidaHeroe, fuerzaHeroe, defensaHeroe, bendicionHeroe);
            Villano villano = new Villano(nombreVillano, vidaVillano, fuerzaVillano, defensaVillano, bendicionVillano);

            // === Abrir ventana de batalla ===
            VentanaBatalla ventanaBatalla = new VentanaBatalla();
            new ControladorBatalla(ventanaBatalla, heroe, villano, cantidadBatallas, ataquesSupremos);
            ventanaBatalla.setLocationRelativeTo(null);
            ventanaBatalla.setTitle("Batalla (" + cantidadBatallas + " ronda/s)");
            ventanaBatalla.setVisible(true);
            vista.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingresá solo números enteros válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al iniciar la batalla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // ==========================================================
    // FUNCIONES DE BOTONES SECUNDARIOS
    // ==========================================================
    private void cargarBatalla() {
        JOptionPane.showMessageDialog(vista, "Funcionalidad de carga no implementada aún.");
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

        // Validar apodo con tu clase modelo
        if (!ValidacionApodos.esValido(apodo)) {
            JOptionPane.showMessageDialog(vista, "Apodo inválido. Debe tener entre 3 y 10 letras y solo espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar tipo
        if (!esHeroe && !esVillano) {
            JOptionPane.showMessageDialog(vista, "Seleccioná si es Héroe o Villano.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Agregar a la tabla
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) vista.getTablaPersonajes().getModel();
        modelo.addRow(new Object[]{nombre, apodo, esHeroe ? "Héroe" : "Villano"});

        // Agregar a los comboBox
        if (esHeroe) vista.getCbApodoHeroe().addItem(apodo);
        else vista.getCbApodoVillano().addItem(apodo);

        // Limpiar campos
        vista.getTxtNombre().setText("");
        vista.getTxtApodo().setText("");
        vista.getRbHeroe().setSelected(false);
        vista.getRbVillano().setSelected(false);
    }

    private void eliminarJugador() {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) vista.getTablaPersonajes().getModel();
        int filaSeleccionada = vista.getTablaPersonajes().getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccioná un jugador para eliminar.", "Ninguna fila seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String apodo = (String) modelo.getValueAt(filaSeleccionada, 1);
        String tipo = (String) modelo.getValueAt(filaSeleccionada, 2);

        // Eliminar de tabla
        modelo.removeRow(filaSeleccionada);

        // También quitar del combo correspondiente
        if ("Héroe".equals(tipo)) vista.getCbApodoHeroe().removeItem(apodo);
        else if ("Villano".equals(tipo)) vista.getCbApodoVillano().removeItem(apodo);

        JOptionPane.showMessageDialog(vista, "Jugador eliminado correctamente.");
    }
}
