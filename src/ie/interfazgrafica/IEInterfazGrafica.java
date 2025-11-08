package ie.interfazgrafica;

import ie.interfazgrafica.vistas.VentanaConfiguracion;
import ie.interfazgrafica.controladores.ControladorConfiguracion;
import javax.swing.SwingUtilities;

public class IEInterfazGrafica {

    public static void main(String[] args) {
        // Ejecutar la aplicación en modo gráfico
        SwingUtilities.invokeLater(() -> {
            VentanaConfiguracion ventana = new VentanaConfiguracion();
            new ControladorConfiguracion(ventana);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}
