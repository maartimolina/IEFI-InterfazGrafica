package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.*;
import ie.interfazgrafica.modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class ControladorConfiguracion {

    private final VentanaConfiguracion vista;

    public ControladorConfiguracion(VentanaConfiguracion vista) {
        this.vista = vista;
        inicializarEventos();

        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaPersonajes().getModel();
        modelo.setRowCount(0);
        cargarJugadoresPersistidos();

        // =============================
        // Valores iniciales por defecto
        // =============================
        Random rnd = new Random();
        vista.getTxtVidaHeroe().setText(String.valueOf(100 + rnd.nextInt(61))); 
        vista.getTxtFuerzaHeroe().setText(String.valueOf(15 + rnd.nextInt(11)));
        vista.getTxtDefensaHeroe().setText(String.valueOf(8 + rnd.nextInt(6)));
        vista.getTxtBendicionHeroe().setText(String.valueOf(30 + rnd.nextInt(71))); 

        vista.getTxtVidaVillano().setText(String.valueOf(100 + rnd.nextInt(61)));
        vista.getTxtFuerzaVillano().setText(String.valueOf(15 + rnd.nextInt(11)));
        vista.getTxtDefensaVillano().setText(String.valueOf(8 + rnd.nextInt(6)));
        vista.getTxtBendicionVillano().setText(String.valueOf(30 + rnd.nextInt(71)));
    }

    private void inicializarEventos() {
        // Botones principales
        vista.getBtnIniciarBatalla().addActionListener(e -> iniciarBatalla());
        vista.getBtnCargarBatalla().addActionListener(e -> cargarBatalla());
        vista.getBtnSalir().addActionListener(e -> salir());

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
            //Leer selección de apodos 
            String apodoHeroe = (String) vista.getCbApodoHeroe().getSelectedItem();
            String apodoVillano = (String) vista.getCbApodoVillano().getSelectedItem();

            if (apodoHeroe == null || apodoVillano == null) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar ambos jugadores antes de continuar.",
                        "Faltan datos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Leer estadísticas del héroe
            int vidaHeroe = Integer.parseInt(vista.getTxtVidaHeroe().getText());
            int fuerzaHeroe = Integer.parseInt(vista.getTxtFuerzaHeroe().getText());
            int defensaHeroe = Integer.parseInt(vista.getTxtDefensaHeroe().getText());
            int bendicionHeroe = Integer.parseInt(vista.getTxtBendicionHeroe().getText());

            //Leer estadísticas del villano
            int vidaVillano = Integer.parseInt(vista.getTxtVidaVillano().getText());
            int fuerzaVillano = Integer.parseInt(vista.getTxtFuerzaVillano().getText());
            int defensaVillano = Integer.parseInt(vista.getTxtDefensaVillano().getText());
            int bendicionVillano = Integer.parseInt(vista.getTxtBendicionVillano().getText());


            Heroe heroe = new Heroe(apodoHeroe, vidaHeroe, fuerzaHeroe, defensaHeroe, bendicionHeroe);
            Villano villano = new Villano(apodoVillano, vidaVillano, fuerzaVillano, defensaVillano, bendicionVillano);

            int cantidadBatallas = Integer.parseInt((String) vista.getCbCantidadBatallas().getSelectedItem());
            boolean ataquesSupremos = vista.getCkActivar1().isSelected();

            JOptionPane.showMessageDialog(vista,
                    "Batalla lista:\nHéroe: " + heroe.getNombre() + "\nVillano: " + villano.getNombre());

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
    private void cargarJugadoresPersistidos() {
        javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel) vista.getTablaPersonajes().getModel();

        for (String[] j : GestorArchivos.leerJugadores()) {
            if (j.length < 3) continue;
            String nombre = j[0], apodo = j[1], tipo = j[2];

            boolean dup = false;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                Object cell = modelo.getValueAt(i, 1);
                if (cell != null && cell.toString().equalsIgnoreCase(apodo)) { dup = true; break; }
            }
            if (!dup) modelo.addRow(new Object[]{nombre, apodo, tipo});

            // combos según tipo
            if ("Héroe".equalsIgnoreCase(tipo)) {
                if (((javax.swing.DefaultComboBoxModel<String>) vista.getCbApodoHeroe().getModel()).getIndexOf(apodo) == -1) {
                    vista.getCbApodoHeroe().addItem(apodo);
                }
            } else if ("Villano".equalsIgnoreCase(tipo)) {
                if (((javax.swing.DefaultComboBoxModel<String>) vista.getCbApodoVillano().getModel()).getIndexOf(apodo) == -1) {
                    vista.getCbApodoVillano().addItem(apodo);
                }
            }
        }
    }


    // ==========================================================
    // BOTONES SECUNDARIOS
    // ==========================================================
    private void cargarBatalla() {
        java.util.List<java.io.File> snaps = GestorArchivos.listarPartidasGuardadas();
        if (snaps.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No hay partidas guardadas aún.");
            return;
        }

        // simple selector por nombre de archivo
        String[] opciones = snaps.stream().map(java.io.File::getName).toArray(String[]::new);
        String elegido = (String) JOptionPane.showInputDialog(
                vista,
                "Selecciona un Batalla:",
                "Cargar batalla",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[opciones.length - 1]
        );
        if (elegido == null) return;

        java.io.File sel = snaps.stream().filter(f -> f.getName().equals(elegido)).findFirst().orElse(null);
        if (sel == null) return;

        GestorArchivos.PartidaGuardada s = GestorArchivos.leerPartidaGuardada(sel);

        // Cargar apodos en combos
        if (((javax.swing.DefaultComboBoxModel<String>) vista.getCbApodoHeroe().getModel())
                .getIndexOf(s.apodoHeroe) == -1) {
            vista.getCbApodoHeroe().addItem(s.apodoHeroe);
        }
        if (((javax.swing.DefaultComboBoxModel<String>) vista.getCbApodoVillano().getModel())
                .getIndexOf(s.apodoVillano) == -1) {
            vista.getCbApodoVillano().addItem(s.apodoVillano);
        }

        vista.getCbApodoHeroe().setSelectedItem(s.apodoHeroe);
        vista.getCbApodoVillano().setSelectedItem(s.apodoVillano);


        vista.getTxtVidaHeroe().setText(String.valueOf(s.vidaH));
        vista.getTxtFuerzaHeroe().setText(String.valueOf(s.fuerzaH));
        vista.getTxtDefensaHeroe().setText(String.valueOf(s.defensaH));
        vista.getTxtBendicionHeroe().setText(String.valueOf(s.bendH));

        vista.getTxtVidaVillano().setText(String.valueOf(s.vidaV));
        vista.getTxtFuerzaVillano().setText(String.valueOf(s.fuerzaV));
        vista.getTxtDefensaVillano().setText(String.valueOf(s.defensaV));
        vista.getTxtBendicionVillano().setText(String.valueOf(s.bendV));

        vista.getCbCantidadBatallas().setSelectedItem(String.valueOf(s.cantidadBatallas));
        vista.getCkActivar1().setSelected(s.supremos);
        vista.getCkDesactivar().setSelected(!s.supremos);

        JOptionPane.showMessageDialog(vista, "Partida cargada. Podes iniciar la batalla.");
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
            JOptionPane.showMessageDialog(vista, "Completa nombre y apodo.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar tipo
        if (!esHeroe && !esVillano) {
            JOptionPane.showMessageDialog(vista, "Selecciona si es Heroe o Villano.",
                    "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar apodo con tu clase modelo
        if (!ValidacionApodos.esValido(apodo)) {
            JOptionPane.showMessageDialog(vista,
                    "Apodo invalido. Debe tener entre 3 y 10 letras y solo espacios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaPersonajes().getModel();

        // Verificar duplicados
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object cell = modelo.getValueAt(i, 1);
            if (cell != null && cell.toString().equalsIgnoreCase(apodo)) {
                JOptionPane.showMessageDialog(vista, "Ese apodo ya esta registrado.",
                        "Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String tipo = esHeroe ? "Heroe" : "Villano";
        modelo.addRow(new Object[]{nombre, apodo, tipo});

        if (esHeroe) vista.getCbApodoHeroe().addItem(apodo);
        else vista.getCbApodoVillano().addItem(apodo);

        vista.getTxtNombre().setText("");
        vista.getTxtApodo().setText("");
        vista.getRbHeroe().setSelected(false);
        vista.getRbVillano().setSelected(false);
        GestorArchivos.guardarJugador(nombre, apodo, tipo);

        JOptionPane.showMessageDialog(vista, "Jugador agregado correctamente.");
    }

    private void eliminarJugador() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaPersonajes().getModel();
        int fila = vista.getTablaPersonajes().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Selecciona una fila para eliminar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object apodoObj = modelo.getValueAt(fila, 1);
        Object tipoObj = modelo.getValueAt(fila, 2);

        if (apodoObj == null || tipoObj == null) {
            JOptionPane.showMessageDialog(vista,
                    "Esa fila contiene datos vacios o invalidos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String apodo = apodoObj.toString();
        String tipo = tipoObj.toString();

        modelo.removeRow(fila);

        if ("Heroe".equalsIgnoreCase(tipo)) {
            vista.getCbApodoHeroe().removeItem(apodo);
        } else if ("Villano".equalsIgnoreCase(tipo)) {
            vista.getCbApodoVillano().removeItem(apodo);
        }
        GestorArchivos.borrarJugadorPorApodo(apodo);

        JOptionPane.showMessageDialog(vista, "Jugador eliminado correctamente.");
    }
}
