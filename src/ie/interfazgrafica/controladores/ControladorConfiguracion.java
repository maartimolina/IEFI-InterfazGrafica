package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.*;
import ie.interfazgrafica.modelo.*;
import javax.swing.*;
import java.awt.event.*;

public class ControladorConfiguracion {

    private final VentanaConfiguracion vista;

    public ControladorConfiguracion(VentanaConfiguracion vista) {
        this.vista = vista;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnIniciarBatalla().addActionListener(e -> iniciarBatalla());
        vista.getBtnCargarBatalla().addActionListener(e -> cargarBatalla());
        vista.getBtnSalir().addActionListener(e -> salir());
    }

    private void iniciarBatalla() {
        try {
            // === Leer valores desde la vista ===
            int vida = Integer.parseInt(vista.getTxtVidaInicial().getText());
            int fuerza = Integer.parseInt(vista.getTxtFuerzaInicial().getText());
            int defensa = Integer.parseInt(vista.getTxtDefensaInicial().getText());
            int bendicion = Integer.parseInt(vista.getTxtBendicionInicial().getText());
            int cantidadBatallas = Integer.parseInt((String) vista.getCbCantidadBatallas().getSelectedItem());
            boolean ataquesSupremos = vista.getCkActivar1().isSelected();

            // === Crear personajes con los valores ===
            Heroe heroe = new Heroe("Heroe", vida, fuerza, defensa, bendicion);
            Villano villano = new Villano("Villano", vida, fuerza, defensa, bendicion);

            // === Abrir ventana de batalla ===
            VentanaBatalla ventanaBatalla = new VentanaBatalla();
            new ControladorBatalla(ventanaBatalla, heroe, villano, cantidadBatallas, ataquesSupremos);
            ventanaBatalla.setVisible(true);
            vista.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Por favor ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al iniciar la batalla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cargarBatalla() {
        JOptionPane.showMessageDialog(vista, "Funcionalidad de carga no implementada aún.");
    }

    private void salir() {
        vista.dispose();
    }
}