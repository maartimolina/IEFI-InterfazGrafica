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

        // Hacerlos mutuamente excluyentes
        vista.getCkActivar1().addActionListener(e ->
            vista.getCkDesactivar().setSelected(!vista.getCkActivar1().isSelected()));
        vista.getCkDesactivar().addActionListener(e ->
            vista.getCkActivar1().setSelected(!vista.getCkDesactivar().isSelected()));

        // Estado inicial sugerido: Activar ON
        vista.getCkActivar1().setSelected(true);
        vista.getCkDesactivar().setSelected(false);
    }

    private void iniciarBatalla() {
            try {
           // === Leer valores desde la vista ===
           String sVida   = vista.getTxtVidaInicial().getText().trim();
           String sFuerza = vista.getTxtFuerzaInicial().getText().trim();
           String sDef    = vista.getTxtDefensaInicial().getText().trim();
           String sBend   = vista.getTxtBendicionInicial().getText().trim();

           if (sVida.isEmpty() || sFuerza.isEmpty() || sDef.isEmpty() || sBend.isEmpty()) {
               JOptionPane.showMessageDialog(vista, "Completá todos los campos numéricos.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
               return;
           }

           int vida     = Integer.parseInt(sVida);
           int fuerza   = Integer.parseInt(sFuerza);
           int defensa  = Integer.parseInt(sDef);
           int bendicion= Integer.parseInt(sBend);

           // Combo de batallas: evitar el item " "
           String sel = (String) vista.getCbCantidadBatallas().getSelectedItem();
           if (sel == null || sel.trim().isEmpty()) {
               JOptionPane.showMessageDialog(vista, "Elegí la cantidad de batallas (2, 3 o 5).", "Dato requerido", JOptionPane.WARNING_MESSAGE);
               return;
           }
           int cantidadBatallas = Integer.parseInt(sel.trim());

           // === Validaciones de rango ===
           if (vida <= 0) {
               JOptionPane.showMessageDialog(vista, "La vida debe ser > 0.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
               return;
           }
           if (fuerza < 0 || defensa < 0) {
               JOptionPane.showMessageDialog(vista, "Fuerza y defensa no pueden ser negativas.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
               return;
           }
           if (bendicion < 0 || bendicion > 100) {
               JOptionPane.showMessageDialog(vista, "Bendición debe estar entre 0 y 100.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
               return;
           }

           // === Calcular toggle de supremos con ambos checks ===
           boolean ataquesSupremos = vista.getCkActivar1().isSelected() && !vista.getCkDesactivar().isSelected();

           // === Crear personajes con los valores ===
           Heroe heroe = new Heroe("Heroe", vida, fuerza, defensa, bendicion);
           Villano villano = new Villano("Villano", vida, fuerza, defensa, bendicion);

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

    private void cargarBatalla() {
        JOptionPane.showMessageDialog(vista, "Funcionalidad de carga no implementada aún.");
    }

    private void salir() {
        vista.dispose();
    }
}