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
    private int numeroBatalla = 1;
    private final Random random = new Random();
    private Timer timer;

    private final int vidaInitH, fuerzaInitH, defensaInitH, bendInitH;
    private final int vidaInitV, fuerzaInitV, defensaInitV, bendInitV;

    private final StringBuilder logBuffer = new StringBuilder();

    public ControladorBatalla(VentanaBatalla vista,
                              Heroe heroe,
                              Villano villano,
                              int cantidadBatallas,
                              boolean ataquesSupremos) {
        this.vista = vista;
        this.heroe = heroe;
        this.villano = villano;
        this.cantidadBatallas = cantidadBatallas;
        this.ataquesSupremos = ataquesSupremos;

        heroe.setSupremosHabilitados(ataquesSupremos);
        villano.setSupremosHabilitados(ataquesSupremos);

        this.vidaInitH = heroe.getVida();
        this.fuerzaInitH = heroe.getFuerzaActual();
        this.defensaInitH = heroe.getDefensaActual();
        this.bendInitH = heroe.getPorcentajeBendicion();

        this.vidaInitV = villano.getVida();
        this.fuerzaInitV = villano.getFuerzaActual();
        this.defensaInitV = villano.getDefensaActual();
        this.bendInitV = villano.getPorcentajeBendicion();

        inicializarEventos();
        inicializarVista();
        iniciarTurno();
    }

    private void inicializarEventos() {
        vista.getGuardarPartida().addActionListener(e -> guardarPartida());
        vista.getSalir().addActionListener(e -> salir());
        vista.getPausar().addActionListener(e -> pausar());
        vista.getHistorialPartidas().addActionListener(e -> abrirReportes());
        vista.getEstadisticas().addActionListener(e -> abrirReportes());
        vista.getRanking().addActionListener(e -> abrirReportes());
    }

    private void inicializarVista() {
        Reportes.limpiarEventos();
        logBuffer.setLength(0);
        vista.getTxtEventos().setText("");

        vista.getLblBatallaActual().setText("Batalla actual: " + numeroBatalla + "/" + cantidadBatallas);
        vista.getLblTurnoActual().setText("Turno actual: " + turnoActual);

        vista.getLblNombre1().setText("Nombre: " + heroe.getNombre());
        vista.getLblNombre2().setText("Nombre: " + villano.getNombre());

        vista.getPbVida1().setMaximum(vidaInitH);
        vista.getPbVida2().setMaximum(vidaInitV);
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

        actualizarBarras();

        agregarEvento("🔥 Comienza la batalla entre " + heroe.getNombre() + " y " + villano.getNombre());
        if (!ataquesSupremos) agregarEvento(" Ataques supremos DESACTIVADOS para esta partida.");
        else agregarEvento(" Ataques supremos ACTIVADOS para esta partida.");
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
        Personaje defensor = (atacante == heroe) ? villano : heroe;

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
            GestorArchivos.registrarDanio(dano);
            if (atacante == heroe) {
                agregarEvento(heroe.getNombre() + " causa " + dano + " de daño a " + villano.getNombre());
            } else {
                agregarEvento(villano.getNombre() + " causa " + dano + " de daño a " + heroe.getNombre());
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
            agregarEvento(ev);
        }
        Reportes.limpiarEventos();
    }

    private void finalizarBatalla() {
        if (timer != null && timer.isRunning()) timer.stop();

        String ganador = heroe.estaVivo() ? heroe.getNombre() : villano.getNombre();
        agregarEvento("¡Ganador de la batalla: " + ganador + "!");

        GestorArchivos.guardarBatalla(heroe.getNombre(), villano.getNombre(), ganador, turnoActual);
        GestorArchivos.guardarTranscriptBatalla(
                Reportes.getEventos(), heroe.getNombre(), villano.getNombre(), ganador, turnoActual
        );
        GestorArchivos.guardarPersonaje(
                heroe.getNombre(), "Héroe", heroe.getVida(), heroe.estaVivo()?1:0, heroe.getSupremosUsados(), heroe.getArmasInvocadas().size()
        );
        GestorArchivos.guardarPersonaje(
                villano.getNombre(), "Villano", villano.getVida(), villano.estaVivo()?1:0, villano.getSupremosUsados(), villano.getArmasInvocadas().size()
        );

        JOptionPane.showMessageDialog(vista, "Ganador: " + ganador);

        if (numeroBatalla < cantidadBatallas) {
            numeroBatalla++;
            agregarEvento("➡️ Iniciando batalla " + numeroBatalla + "/" + cantidadBatallas + "…");
            reiniciarRonda();
            return;
        }

        VentanaReportes vr = new VentanaReportes();
        new ControladorReportes(vr);
        vr.setVisible(true);
        vista.dispose();
    }

    private void reiniciarRonda() {
        heroe.setVida(vidaInitH);
        heroe.setFuerzaActual(fuerzaInitH);
        heroe.setDefensaActual(defensaInitH);
        heroe.setPorcentajeBendicion(bendInitH);
        heroe.limpiarEstados();

        villano.setVida(vidaInitV);
        villano.setFuerzaActual(fuerzaInitV);
        villano.setDefensaActual(defensaInitV);
        villano.setPorcentajeBendicion(bendInitV);
        villano.limpiarEstados();

        turnoActual = 1;
        vista.getLblBatallaActual().setText("Batalla actual: " + numeroBatalla + "/" + cantidadBatallas);
        vista.getLblTurnoActual().setText("Turno actual: " + turnoActual);

        Reportes.limpiarEventos();
        logBuffer.setLength(0);
        vista.getTxtEventos().setText("");

        actualizarUI();

        if (timer != null) timer.stop();
        iniciarTurno();
    }

    private void actualizarBarras() {
        vista.getPbVida1().setValue(heroe.getVida());
        vista.getPbVida1().setString(String.valueOf(heroe.getVida()));
        vista.getPbVida2().setValue(villano.getVida());
        vista.getPbVida2().setString(String.valueOf(villano.getVida()));
    }

    private void agregarEvento(String texto) {
        if (logBuffer.length() > 0) logBuffer.append("\n");
        logBuffer.append(texto);
        vista.getTxtEventos().setText(logBuffer.toString());
        vista.getTxtEventos().setCaretPosition(vista.getTxtEventos().getDocument().getLength());
        System.out.println(texto);
    }

    private void guardarPartida() {
        GestorArchivos.PartidaGuardada s = new GestorArchivos.PartidaGuardada();
        s.apodoHeroe = heroe.getNombre();
        s.apodoVillano = villano.getNombre();
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

    private void salir() {
        vista.dispose();
    }

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
