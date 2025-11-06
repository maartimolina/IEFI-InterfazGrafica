package ie.interfazgrafica;

import ie.interfazgrafica.modelo.Villano;
import ie.interfazgrafica.modelo.Reportes;
import ie.interfazgrafica.modelo.Personaje;
import ie.interfazgrafica.modelo.ValidacionApodos;
import ie.interfazgrafica.modelo.Heroe;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class IEInterfazGrafica {

   
     public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ie.interfazgrafica.vistas.VentanaConfiguracion v =
                new ie.interfazgrafica.vistas.VentanaConfiguracion();
            new ie.interfazgrafica.controladores.ControladorConfiguracion(v);
            v.setLocationRelativeTo(null);
            v.setVisible(true);
        });
    }
}
