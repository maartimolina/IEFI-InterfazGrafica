package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.VentanaReportes;
import ie.interfazgrafica.modelo.GestorArchivos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class ControladorReportes {

    private final VentanaReportes vista;

    public ControladorReportes(VentanaReportes vista) {
        this.vista = vista;
        inicializarVista();
    }

    // ======================== CONFIG INICIAL ========================
    private void inicializarVista() {
        vista.setTitle("Reportes de Batalla");
        vista.setLocationRelativeTo(null);

        cargarRanking();
        cargarEstadisticas();
        cargarHistorial();
    }

    // ======================== RANKING (consolidado) ========================
    private void cargarRanking() {
        JTable tabla = vista.getTablaRanking();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        List<String[]> registros = GestorArchivos.leerRanking(); // apodo;tipo;vida;vict;sup;armas
        if (registros.isEmpty()) {
            modelo.addRow(new Object[]{"(sin datos)", "-", "-", "-", "-", "-"});
            return;
        }

        // apodo -> nombreReal (si no existe, usamos el propio apodo)
        Map<String, String> nombresPorApodo = GestorArchivos.cargarJugadoresMapa();

        // Consolidar por apodo
        Map<String, FilaRanking> map = new LinkedHashMap<>();
        for (String[] r : registros) {
            if (r.length < 6) continue;

            String apodo   = r[0];
            String nombre  = nombresPorApodo.getOrDefault(apodo, apodo);
            String tipo    = r[1];
            int vida       = parseIntSafe(r[2]);
            int victorias  = parseIntSafe(r[3]);
            int supremos   = parseIntSafe(r[4]);
            int armas      = parseIntSafe(r[5]);

            FilaRanking fr = map.get(apodo);
            if (fr == null) {
                fr = new FilaRanking(nombre, apodo, tipo);
                map.put(apodo, fr);
            }
            fr.victorias      += victorias;
            fr.supremosUsados += supremos;
            fr.armasInvocadas += armas;
            fr.vidaFinal       = vida; // última vida registrada
        }

        List<FilaRanking> lista = new ArrayList<>(map.values());
        lista.sort((a, b) -> {
            int cmp = Integer.compare(b.victorias, a.victorias);
            return (cmp != 0) ? cmp : a.apodo.compareToIgnoreCase(b.apodo);
        });

        // Cargar tabla: Nombre | Apodo | Tipo | Vida Final | Victorias | Ataques Usados
        for (FilaRanking fr : lista) {
            modelo.addRow(new Object[]{
                fr.nombreReal, fr.apodo, fr.tipo, fr.vidaFinal, fr.victorias, fr.supremosUsados
            });
        }
    }

    // ======================== ESTADÍSTICAS ========================
    private void cargarEstadisticas() {
        int mayorDanio = GestorArchivos.leerMayorDanio();

        int batallaMasLarga = 0;
        for (String[] h : GestorArchivos.leerHistorial()) {
            if (h.length >= 5) batallaMasLarga = Math.max(batallaMasLarga, parseIntSafe(h[4]));
        }

        int totalArmas = 0;
        int totalSupremos = 0;

        for (String[] r : GestorArchivos.leerRanking()) {
            if (r.length >= 6) {
                totalSupremos += parseIntSafe(r[4]);
                totalArmas    += parseIntSafe(r[5]);
            }
        }

        vista.getLblMayorDaño().setText("Mayor daño en un solo ataque: " + mayorDanio);
        vista.getLblBatallaLarga().setText("Batalla más larga: " + batallaMasLarga + " turnos");
        vista.getLblArmasInvocadas1().setText("Total de armas invocadas: " + totalArmas);
        vista.getLblAtaquesEjecutados().setText("Ataques supremos ejecutados: " + totalSupremos);
    }

    // ======================== HISTORIAL (textarea) ========================
    private void cargarHistorial() {
        JTextArea area = vista.getTxtHistorial();
        List<String[]> batallas = GestorArchivos.leerHistorial();

        if (batallas.isEmpty()) {
            area.setText("No hay batallas registradas todavía.");
            area.setCaretPosition(0);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Últimas batallas registradas:\n")
          .append("------------------------------------------------------------\n");

        // fecha;heroe;villano;ganador;turnos
        for (String[] b : batallas) {
            if (b.length >= 5) {
                sb.append("Fecha: ").append(b[0])
                  .append(" | Héroe: ").append(b[1])
                  .append(" | Villano: ").append(b[2])
                  .append(" | Ganador: ").append(b[3])
                  .append(" | Turnos: ").append(b[4])
                  .append("\n");
            }
        }

        area.setText(sb.toString());
        area.setCaretPosition(0);
    }

    // ======================== HELPERS ========================
    private int parseIntSafe(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; }
    }

    // ↓↓↓ SOLO UNA definición de esta clase interna ↓↓↓
    private static class FilaRanking {
        String nombreReal, apodo, tipo;
        int vidaFinal, victorias, supremosUsados, armasInvocadas;

        FilaRanking(String nombreReal, String apodo, String tipo) {
            this.nombreReal = nombreReal;
            this.apodo = apodo;
            this.tipo = tipo;
        }
    }
}
