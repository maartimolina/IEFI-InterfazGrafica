package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.*;
import ie.interfazgrafica.modelo.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class ControladorBatalla {

    private final VentanaBatalla vista;
    private final Heroe heroe;
    private final Villano villano;
    private final int cantidadBatallas;
    private final boolean ataquesSupremos;
    private int turnoActual = 1;
    private int numeroBatalla = 1;
    private final Random random = new Random();

    public ControladorBatalla(VentanaBatalla vista, Heroe heroe, Villano villano, int cantidadBatallas, boolean ataquesSupremos) {
        this.vista = vista;
        this.heroe = heroe;
        this.villano = villano;
        this.cantidadBatallas = cantidadBatallas;
        this.ataquesSupremos = ataquesSupremos;

        inicializarEventos();
        inicializarVista();
        iniciarTurno();
    }

    // ======================================================
    // EVENTOS DEL MENÚ
    // ======================================================
    private void inicializarEventos() {
        vista.getGuardarPartida().addActionListener(e -> guardarPartida());
        vista.getSalir().addActionListener(e -> salir());
        vista.getPausar().addActionListener(e -> pausar());
        vista.getHistorialPartidas().addActionListener(e -> abrirReportes());
        vista.getEstadisticas().addActionListener(e -> abrirReportes());
        vista.getRanking().addActionListener(e -> abrirReportes());
    }

    // ======================================================
    // INICIALIZACIÓN DE LA VISTA
    // ======================================================
    private void inicializarVista() {
        vista.getLblBatallaActual().setText("Batalla actual: " + numeroBatalla + "/" + cantidadBatallas);
        vista.getLblTurnoActual().setText("Turno actual: " + turnoActual);
        vista.getLblNombre1().setText("Nombre: " + heroe.getNombre());
        vista.getLblNombre2().setText("Nombre: " + villano.getNombre());

        actualizarBarras();
        agregarEvento("🔥 Comienza la batalla entre " + heroe.getNombre() + " y " + villano.getNombre());
    }

    // ======================================================
    // CONTROL DEL JUEGO
    // ======================================================
    private void iniciarTurno() {
        Timer timer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ejecutarTurno();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void ejecutarTurno() {
        // Terminar si alguien muere
        if (!heroe.estaVivo() || !villano.estaVivo()) {
            finalizarBatalla();
            return;
        }

        // Usamos atributos accesibles del modelo (sin getFuerza / setVida)
        if (turnoActual % 2 == 1) {
            // Turno del héroe
            int dano = calcularDanio(heroe, villano);
            villano.recibirDanio(dano);
            agregarEvento("💥 " + heroe.getNombre() + " ataca y causa " + dano + " de daño a " + villano.getNombre());
        } else {
            // Turno del villano
            int dano = calcularDanio(villano, heroe);
            heroe.recibirDanio(dano);
            agregarEvento("⚔️ " + villano.getNombre() + " contraataca y causa " + dano + " de daño a " + heroe.getNombre());
        }

        turnoActual++;
        actualizarBarras();
        vista.getLblTurnoActual().setText("Turno actual: " + turnoActual);
    }

    private int calcularDanio(Personaje atacante, Personaje defensor) {
        // Calcular daño en base a fuerza y defensa reales
        int base = getFuerzaReal(atacante) - defensor.getDefensaActual() / 2;
        return Math.max(base, 1);
    }

    private int getFuerzaReal(Personaje p) {
        // Deducción basada en atributos visibles en tu modelo
        // Como 'fuerza' es protected, Heroe/Villano lo exponen internamente
        try {
            java.lang.reflect.Field f = p.getClass().getSuperclass().getDeclaredField("fuerza");
            f.setAccessible(true);
            return (int) f.get(p);
        } catch (Exception e) {
            e.printStackTrace();
            return 10; // valor por defecto de seguridad
        }
    }

    private void finalizarBatalla() {
        String ganador = heroe.estaVivo() ? heroe.getNombre() : villano.getNombre();
        agregarEvento("🏆 ¡Ganador de la batalla: " + ganador + "!");
        JOptionPane.showMessageDialog(vista, "Ganador: " + ganador);

        VentanaReportes ventanaReportes = new VentanaReportes();
        new ControladorReportes(ventanaReportes);
        ventanaReportes.setVisible(true);
        vista.dispose();
    }

    private void actualizarBarras() {
        vista.getPbVida1().setValue(heroe.getVida());
        vista.getPbVida2().setValue(villano.getVida());
    }

    private void agregarEvento(String texto) {
        System.out.println(texto);
        vista.getLblLog().setText("<html>" + vista.getLblLog().getText() + "<br>" + texto + "</html>");
    }

    private void guardarPartida() {
        JOptionPane.showMessageDialog(vista, "Funcionalidad de guardado pendiente.");
    }

    private void salir() {
        vista.dispose();
    }

    private void pausar() {
        JOptionPane.showMessageDialog(vista, "Partida pausada (demo).");
    }

    private void abrirReportes() {
        VentanaReportes ventana = new VentanaReportes();
        new ControladorReportes(ventana);
        ventana.setVisible(true);
    }
}
