package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.*;
import ie.interfazgrafica.modelo.*;
import ie.interfazgrafica.modelo.GestorArchivos;
import javax.swing.*;
import javax.swing.Timer;   
import java.util.Random;

public class ControladorBatalla {

    private final VentanaBatalla vista;
    private final Heroe heroe;
    private final Villano villano;
    private final int cantidadBatallas;
    private final boolean ataquesSupremos;

    private int turnoActual = 1;
    private final int numeroBatalla = 1;
    private final Random random = new Random();
    private Timer timer;

    public ControladorBatalla(VentanaBatalla vista, Heroe heroe, Villano villano,
                              int cantidadBatallas, boolean ataquesSupremos) {
        this.vista = vista;
        this.heroe = heroe;
        this.villano = villano;
        this.cantidadBatallas = cantidadBatallas;
        this.ataquesSupremos = ataquesSupremos;

        heroe.setSupremosHabilitados(ataquesSupremos);
        villano.setSupremosHabilitados(ataquesSupremos);

        inicializarEventos();
        inicializarVista();
        iniciarTurno();
    }

    // ============================= EVENTOS MENÚ =============================
    private void inicializarEventos() {
        vista.getGuardarPartida().addActionListener(e -> guardarPartida());
        vista.getSalir().addActionListener(e -> salir());
        vista.getPausar().addActionListener(e -> pausar());
        vista.getHistorialPartidas().addActionListener(e -> abrirReportes());
        vista.getEstadisticas().addActionListener(e -> abrirReportes());
        vista.getRanking().addActionListener(e -> abrirReportes());
    }
    
    // ============================ INICIALIZACIÓN ============================
    private void inicializarVista() {
        Reportes.limpiarEventos();

        vista.getLblBatallaActual().setText("Batalla actual: " + numeroBatalla + "/" + cantidadBatallas);
        vista.getLblTurnoActual().setText("Turno actual: " + turnoActual);
        vista.getLblNombre1().setText("Nombre: " + heroe.getNombre());
        vista.getLblNombre2().setText("Nombre: " + villano.getNombre());

        actualizarBarras();

        agregarEvento("🔥 Comienza la batalla entre " + heroe.getNombre() + " y " + villano.getNombre());
        if (!ataquesSupremos) agregarEvento("❌ Ataques supremos DESACTIVADOS para esta partida.");
        else                  agregarEvento("✅ Ataques supremos ACTIVADOS para esta partida.");

        vista.getPbVida1().setMaximum(heroe.getVida());
        vista.getPbVida2().setMaximum(villano.getVida());
        vista.getPbVida1().setStringPainted(true);
        vista.getPbVida2().setStringPainted(true);

        vista.getPbBendicion1().setMaximum(100);
        vista.getPbBendicion2().setMaximum(100);
        vista.getPbBendicion1().setStringPainted(true);
        vista.getPbBendicion2().setStringPainted(true);

        vista.getLblArma1().setText("Arma: " + heroe.getArmaNombre());
        vista.getLblArma2().setText("Arma: " + villano.getArmaNombre());
        vista.getLblEstado1().setText("Estado: " + heroe.getEstadoTexto());
        vista.getLblEstado2().setText("Estado: " + villano.getEstadoTexto());
    }

    private void actualizarUI() {
        actualizarBarras();
        vista.getLblTurnoActual().setText("Turno actual: " + turnoActual);

        vista.getPbBendicion1().setValue(heroe.getPorcentajeBendicion());
        vista.getPbBendicion1().setString(heroe.getPorcentajeBendicion() + "%");
        vista.getPbBendicion2().setValue(villano.getPorcentajeBendicion());
        vista.getPbBendicion2().setString(villano.getPorcentajeBendicion() + "%");

        vista.getLblArma1().setText("Arma: " + heroe.getArmaNombre());
        vista.getLblArma2().setText("Arma: " + villano.getArmaNombre());
        vista.getLblEstado1().setText("Estado: " + heroe.getEstadoTexto());
        vista.getLblEstado2().setText("Estado: " + villano.getEstadoTexto());
    }

    // ============================== BUCLE JUEGO =============================
    private void iniciarTurno() {
        timer = new Timer(1500, e -> ejecutarTurno());
        timer.setRepeats(true);
        timer.start();
    }

    private void ejecutarTurno() {
        if (!heroe.estaVivo() || !villano.estaVivo()) {
            finalizarBatalla();
            return;
        }

        Personaje atacante = (turnoActual % 2 == 1) ? heroe : villano;
        Personaje defensor  = (atacante == heroe) ? villano : heroe;

        atacante.aplicarEstadosAlInicioDelTurno();
        actualizarUI();

        if (!atacante.estaVivo()) {
            finalizarBatalla();
            return;
        }

        int vidaAntes = defensor.getVida();
        atacante.decidirAccion(defensor);
        int dano = Math.max(0, vidaAntes - defensor.getVida());

        if (dano > 0) {
            // registrar mayor daño histórico
            GestorArchivos.registrarDanio(dano);

            if (atacante == heroe) {
                agregarEvento("💥 " + heroe.getNombre() + " causa " + dano + " de daño a " + villano.getNombre());
            } else {
                agregarEvento("⚔️ " + villano.getNombre() + " causa " + dano + " de daño a " + heroe.getNombre());
            }
        }

        flushEventosEspeciales();
        turnoActual++;
        actualizarUI();

        if (!heroe.estaVivo() || !villano.estaVivo()) {
            finalizarBatalla();
        }
    }

    private void flushEventosEspeciales() {
        for (String ev : Reportes.getEventos()) {
            agregarEvento("✨ " + ev);
        }
        Reportes.limpiarEventos();
    }

    // ============================= FIN DE BATALLA ===========================
    private void finalizarBatalla() {
         if (timer != null && timer.isRunning()) timer.stop();

        String ganador = heroe.estaVivo() ? heroe.getNombre() : villano.getNombre();
        agregarEvento("🏆 ¡Ganador de la batalla: " + ganador + "!");

        // Historial “crudo”
        GestorArchivos.guardarBatalla(heroe.getNombre(), villano.getNombre(), ganador, turnoActual);

        // NUEVO: transcript con los eventos que se mostraron en pantalla
        GestorArchivos.guardarTranscriptBatalla(
                Reportes.getEventos(), heroe.getNombre(), villano.getNombre(), ganador, turnoActual
        );

        // Ranking
        GestorArchivos.guardarPersonaje(
            heroe.getNombre(), "Héroe", heroe.getVida(), heroe.estaVivo()?1:0,
            heroe.getSupremosUsados(), heroe.getArmasInvocadas().size()
        );
        GestorArchivos.guardarPersonaje(
            villano.getNombre(), "Villano", villano.getVida(), villano.estaVivo()?1:0,
            villano.getSupremosUsados(), villano.getArmasInvocadas().size()
        );

        JOptionPane.showMessageDialog(vista, "Ganador: " + ganador);

        VentanaReportes vr = new VentanaReportes();
        new ControladorReportes(vr);
        vr.setVisible(true);
        vista.dispose();
    }
    
    // ================================ UTIL ================================
    private void actualizarBarras() {
        vista.getPbVida1().setValue(heroe.getVida());
        vista.getPbVida1().setString(String.valueOf(heroe.getVida()));
        vista.getPbVida2().setValue(villano.getVida());
        vista.getPbVida2().setString(String.valueOf(villano.getVida()));
    }

    private void agregarEvento(String texto) {
        System.out.println(texto);
        vista.getLblLog().setText("<html>" + vista.getLblLog().getText() + "<br>" + texto + "</html>");
    }

    private void guardarPartida() {
        // toma lo que está en pantalla/estado y lo guarda como partidaGuardada
        GestorArchivos.PartidaGuardada s = new GestorArchivos.PartidaGuardada();
        s.apodoHeroe = heroe.getNombre();
        s.apodoVillano = villano.getNombre();

        // Stats actuales (podés elegir iniciales si preferís)
        s.vidaH = heroe.getVida();
        s.fuerzaH = heroe.getFuerzaActual();
        s.defensaH = heroe.getDefensaActual();
        s.bendH = heroe.getPorcentajeBendicion();

        s.vidaV = villano.getVida();
        s.fuerzaV = villano.getFuerzaActual();
        s.defensaV = villano.getDefensaActual();
        s.bendV = villano.getPorcentajeBendicion();

        s.cantidadBatallas = cantidadBatallas;
        s.supremos = ataquesSupremos;

        java.io.File f = GestorArchivos.guardarPartidaGuardada(s);
        JOptionPane.showMessageDialog(vista, "Partida guardada en:\n" + f.getAbsolutePath());
    }

    private void salir() { vista.dispose(); }

    private void pausar() {
        if (timer == null) return;
        if (timer.isRunning()) {
            timer.stop();
            JOptionPane.showMessageDialog(vista, "Partida pausada.");
        } else {
            timer.start();
            JOptionPane.showMessageDialog(vista, "Partida reanudada.");
        }
    }

    private void abrirReportes() {
        VentanaReportes ventana = new VentanaReportes();
        new ControladorReportes(ventana);
        ventana.setVisible(true);
    }
}
