package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.*;
import ie.interfazgrafica.modelo.*;
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
    private final int numeroBatalla = 1;   // ← ahora final, porque no lo cambiás
    private final Random random = new Random();
    private Timer timer;

    public ControladorBatalla(VentanaBatalla vista, Heroe heroe, Villano villano, int cantidadBatallas, boolean ataquesSupremos) {
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
        Reportes.limpiarEventos(); 
        vista.getLblBatallaActual().setText("Batalla actual: " + numeroBatalla + "/" + cantidadBatallas);
        vista.getLblTurnoActual().setText("Turno actual: " + turnoActual);
        vista.getLblNombre1().setText("Nombre: " + heroe.getNombre());
        vista.getLblNombre2().setText("Nombre: " + villano.getNombre());

        actualizarBarras();
        agregarEvento("🔥 Comienza la batalla entre " + heroe.getNombre() + " y " + villano.getNombre());
        if (!ataquesSupremos)
            agregarEvento("❌ Ataques supremos DESACTIVADOS para esta partida.");
        else
            agregarEvento("✅ Ataques supremos ACTIVADOS para esta partida.");
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
    // Vida
    actualizarBarras();
    // Turno
    vista.getLblTurnoActual().setText("Turno actual: " + turnoActual);
    // Bendición
    vista.getPbBendicion1().setValue(heroe.getPorcentajeBendicion());
    vista.getPbBendicion1().setString(heroe.getPorcentajeBendicion() + "%");
    vista.getPbBendicion2().setValue(villano.getPorcentajeBendicion());
    vista.getPbBendicion2().setString(villano.getPorcentajeBendicion() + "%");
    // Arma y Estado
    vista.getLblArma1().setText("Arma: " + heroe.getArmaNombre());
    vista.getLblArma2().setText("Arma: " + villano.getArmaNombre());
    vista.getLblEstado1().setText("Estado: " + heroe.getEstadoTexto());
    vista.getLblEstado2().setText("Estado: " + villano.getEstadoTexto());
}

    // ======================================================
    // CONTROL DEL JUEGO
    // ======================================================
        private void iniciarTurno() {
          timer = new Timer(1500, e -> ejecutarTurno());
        timer.setRepeats(true);
        timer.start();
    }

        private void ejecutarTurno() {
             // Si ya terminó
            if (!heroe.estaVivo() || !villano.estaVivo()) {
                finalizarBatalla();
                return;
            }

            // Atacante/defensor según turno
            Personaje atacante = (turnoActual % 2 == 1) ? heroe : villano;
            Personaje defensor  = (atacante == heroe) ? villano : heroe;

            // 1) Estados al inicio (veneno, buffs, +10% bendición)
            atacante.aplicarEstadosAlInicioDelTurno();
            actualizarUI();

            if (!atacante.estaVivo()) { // puede morir por estado
                finalizarBatalla();
                return;
            }

            // 2) Acción según la estrategia del personaje
            int vidaAntes = defensor.getVida();
            atacante.decidirAccion(defensor);      // <-- esto activa invocar/ataques supremos/ataque normal
            int dano = Math.max(0, vidaAntes - defensor.getVida());

            // Mensaje por default si fue un golpe "normal"
            if (dano > 0) {
                if (atacante == heroe) {
                    agregarEvento("💥 " + heroe.getNombre() + " causa " + dano + " de daño a " + villano.getNombre());
                } else {
                    agregarEvento("⚔️ " + villano.getNombre() + " causa " + dano + " de daño a " + heroe.getNombre());
                }
            }

            // 3) Volcar al log los eventos especiales acumulados por Reportes (supremos, etc.)
            flushEventosEspeciales();

            // 4) Avanzar turno, refrescar, y chequear fin
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



        private int calcularDanio(Personaje atacante) {
        int base = atacante.getFuerzaActual() + atacante.getDanioArma();
        return Math.max(base, 1);
        }

  

    private void finalizarBatalla() {
         if (timer != null && timer.isRunning()) timer.stop(); // primero
        String ganador = heroe.estaVivo() ? heroe.getNombre() : villano.getNombre();
        agregarEvento("🏆 ¡Ganador de la batalla: " + ganador + "!");
        JOptionPane.showMessageDialog(vista, "Ganador: " + ganador);
        VentanaReportes vr = new VentanaReportes();
        new ControladorReportes(vr);
        vr.setVisible(true);
        vista.dispose();
    }

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
        JOptionPane.showMessageDialog(vista, "Funcionalidad de guardado pendiente.");
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
