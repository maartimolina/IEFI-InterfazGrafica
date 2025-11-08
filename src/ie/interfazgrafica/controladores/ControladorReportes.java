package ie.interfazgrafica.controladores;

import ie.interfazgrafica.vistas.VentanaReportes;
import ie.interfazgrafica.modelo.GestorArchivos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;

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

        List<String[]> registros = GestorArchivos.leerRanking();
        if (registros.isEmpty()) {
            modelo.addRow(new Object[]{"(sin datos)", "-", "-", "-", "-", "-"});
            return;
        }

        // Consolidar por nombre
        Map<String, FilaRanking> map = new LinkedHashMap<>();
        for (String[] r : registros) {
            if (r.length < 6) continue;

            String nombre  = r[0];
            String tipo    = r[1];
            int vida       = parseIntSafe(r[2]);
            int victorias  = parseIntSafe(r[3]);
            int supremos   = parseIntSafe(r[4]);
            int armas      = parseIntSafe(r[5]);

            FilaRanking fr = map.get(nombre);
            if (fr == null) {
                fr = new FilaRanking(nombre, tipo);
                map.put(nombre, fr);
            }
            fr.victorias      += victorias;
            fr.supremosUsados += supremos;
            fr.armasInvocadas += armas;
            fr.vidaFinal       = vida; // tomamos la última vida final registrada
        }

        // Orden por victorias desc, nombre asc
        List<FilaRanking> lista = new ArrayList<>(map.values());
        lista.sort((a, b) -> {
            int cmp = Integer.compare(b.victorias, a.victorias);
            return (cmp != 0) ? cmp : a.nombre.compareToIgnoreCase(b.nombre);
        });

        // Cargar tabla: Nombre | Apodo | Tipo | Vida Final | Victorias | Ataques Usados
        for (FilaRanking fr : lista) {
            modelo.addRow(new Object[]{
                fr.nombre, "-",
                fr.tipo,
                fr.vidaFinal,
                fr.victorias,
                fr.supremosUsados
            });
        }
    }

    // ======================== ESTADÍSTICAS ========================
    private void cargarEstadisticas() {
        // Mayor daño histórico
        int mayorDanio = GestorArchivos.leerMayorDanio();

        // Batalla más larga (de historial)
        int batallaMasLarga = 0;
        for (String[] h : GestorArchivos.leerHistorial()) {
            if (h.length >= 5) batallaMasLarga = Math.max(batallaMasLarga, parseIntSafe(h[4]));
        }

        // Totales desde ranking crudo
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

    private static class FilaRanking {
        String nombre;
        String tipo;
        int vidaFinal;
        int victorias;
        int supremosUsados;
        int armasInvocadas;

        FilaRanking(String nombre, String tipo) {
            this.nombre = nombre;
            this.tipo = tipo;
        }
    }
}