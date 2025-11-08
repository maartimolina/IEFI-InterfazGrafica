package ie.interfazgrafica.modelo;

import java.util.List;
import ie.interfazgrafica.vistas.VentanaReportes;
public class Reportes {
    public class ControladorReportes {
  private final VentanaReportes vista;
  public ControladorReportes(VentanaReportes v) {
    this.vista = v;
    // Ejemplo mínimo:
    var eventos = Reportes.getEventos();
    if (!eventos.isEmpty()) {
      String ultimo = eventos.get(eventos.size()-1);
      vista.getLblAtaquesEjecutados().setText("Último evento: " + ultimo);
    }
    // TODO: poblar tabla ranking con nombre, apodo/tipo, vida final, etc.
  }
}
    public static String generar(Personaje heroe, Personaje villano,
                                 List<String> eventosEspeciales,
                                 List<String> historial, int turnosTotales) {
        StringBuilder sb = new StringBuilder();

        int vidaH = Math.max(0, heroe.getVida());
        int vidaV = Math.max(0, villano.getVida());

        sb.append("===========================================\n");
        sb.append("        REPORTE FINAL DE LA BATALLA\n");
        sb.append("===========================================\n");
        sb.append("Heroe: apodo ").append(heroe.getNombre())
          .append(" - Vida final: ").append(vidaH).append("\n");
        sb.append("Villano: apodo ").append(villano.getNombre())
          .append(" - Vida final: ").append(vidaV).append("\n\n");

        // --- ARMAS INVOCADAS ---
        sb.append("--- ARMAS INVOCADAS ---\n");
        sb.append(heroe.getNombre()).append(": ")
          .append(formatearArmas(heroe.getArmasInvocadas())).append("\n");
        sb.append(villano.getNombre()).append(": ")
          .append(formatearArmas(villano.getArmasInvocadas())).append("\n\n");

        // --- ATAQUES ESPECIALES ---
        sb.append("--- ATAQUES ESPECIALES ---\n");
        if (eventosEspeciales.isEmpty()) {
            sb.append("(ninguno)\n");
        } else {
            for (String ev : eventosEspeciales) sb.append(ev).append("\n");
        }
        sb.append("\n");

        // --- HISTORIAL RECIENTE (ultimos 5, de mas nuevo a mas viejo) ---
        sb.append("--- HISTORIAL RECIENTE ---\n");
        int numero = 1;
        for (int i = historial.size() - 1; i >= 0; i--) {
            sb.append("BATALLA #").append(numero++).append(" - ")
              .append(historial.get(i)).append("\n");
        }
        sb.append("=======================================\n");

        return sb.toString();
    }
    private static final java.util.List<String> EVENTOS = new java.util.ArrayList<>();

    public static void registrarEvento(String e) { EVENTOS.add(e); }
    public static java.util.List<String> getEventos() { return EVENTOS; }
    public static void limpiarEventos() { EVENTOS.clear(); }
    private static String formatearArmas(List<Arma> armas) {
        if (armas.isEmpty()) return "(ninguna)";
        java.util.LinkedHashMap<String,Integer> conteo = new java.util.LinkedHashMap<>();
        for (Arma a : armas) conteo.put(a.getNombre(), conteo.getOrDefault(a.getNombre(), 0) + 1);

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (var e : conteo.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(e.getKey()).append(" (").append(e.getValue()).append(")");
            first = false;
        }
        return sb.toString();
    }
}
