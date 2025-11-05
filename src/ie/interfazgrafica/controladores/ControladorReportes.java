package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.VentanaReportes;
import ie.interfazgrafica.modelo.Reportes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorReportes {

    private final VentanaReportes vista;

    public ControladorReportes(VentanaReportes vista) {
        this.vista = vista;
        inicializarVista();
    }

    // ==============================================================
    // CONFIGURACIÓN DE LA VENTANA DE REPORTES
    // ==============================================================
    private void inicializarVista() {
        vista.setTitle("Reportes de Batalla");
        vista.setLocationRelativeTo(null);

        // Cargar datos simulados (luego Bordiga los conectará a persistencia)
        cargarRanking();
        cargarEstadisticas();
        cargarHistorial();
    }

    // ==============================================================
    // DATOS DE PRUEBA (TEMPORAL)
    // ==============================================================
    private void cargarRanking() {
        JTable tabla = vista.getTablaRanking();
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0); // limpia

        // Simulación (después se reemplaza con datos reales desde Reportes o BD)
        model.addRow(new Object[]{"Arthas", "El Redentor", "Héroe", "120", "3", "12"});
        model.addRow(new Object[]{"Morgana", "La Oscura", "Villana", "0", "1", "8"});
        model.addRow(new Object[]{"Lucius", "El Bendito", "Héroe", "60", "2", "10"});
    }

    private void cargarEstadisticas() {
        vista.getLblMayorDaño().setText("Mayor daño en un solo ataque: 45");
        vista.getLblBatallaLarga().setText("Batalla más larga: 15 turnos");
        vista.getLblArmasInvocadas1().setText("Total de armas invocadas: 7");
        vista.getLblAtaquesEjecutados().setText("Ataques supremos ejecutados: 5");
    }

    private void cargarHistorial() {
        // Este método se conectará luego con la lista del historial
        // que persiste Bordiga desde archivo o base de datos.
        // Por ahora, solo muestra un mensaje informativo.
        JOptionPane.showMessageDialog(vista, "Historial de batallas cargado correctamente (modo demo).");
    }
}
