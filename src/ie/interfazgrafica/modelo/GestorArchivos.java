package ie.interfazgrafica.modelo;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Persistencia simple en archivos de texto.
 * - historial_batallas.txt : fecha;heroe;villano;ganador;turnos
 * - personajes.txt         : nombre;tipo;vidaFinal;victorias;supremosUsados;armasInvocadas
 * - estadisticas_danio_max.txt : número (mayor daño histórico)
 */
public class GestorArchivos {

    private static final String ARCHIVO_HISTORIAL  = "historial_batallas.txt";
    private static final String ARCHIVO_PERSONAJES = "personajes.txt";
    private static final String ARCHIVO_DANIO_MAX  = "estadisticas_danio_max.txt";
    private static final String ARCHIVO_JUGADORES = "jugadores.txt";

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // -------------------- HISTORIAL --------------------
    public static void guardarBatalla(String heroe, String villano, String ganador, int turnos) {
        String fecha = LocalDateTime.now().format(FMT);
        String linea = String.format("%s;%s;%s;%s;%d", fecha, heroe, villano, ganador, turnos);
        escribirLineaAppend(ARCHIVO_HISTORIAL, linea);
    }

    public static List<String[]> leerHistorial() {
        return leerArchivoCSV(ARCHIVO_HISTORIAL);
    }

    // -------------------- RANKING ----------------------
    public static void guardarPersonaje(String apodo, String tipo,
                                        int vidaFinal, int victorias,
                                        int supremosUsados, int armasInvocadas) {
        String linea = String.format("%s;%s;%d;%d;%d;%d",
                nullSafe(apodo), nullSafe(tipo),
                vidaFinal, victorias, supremosUsados, armasInvocadas);
        escribirLineaAppend(ARCHIVO_PERSONAJES, linea);
    }

    public static List<String[]> leerRanking() {
        return leerArchivoCSV(ARCHIVO_PERSONAJES); 
    }
    

    // -------------------- ESTADÍSTICAS -----------------
    /** Actualiza el mayor daño histórico si 'danio' es mayor al guardado. */
    public static void registrarDanio(int danio) {
        if (danio <= 0) return;
        int actual = leerMayorDanio();
        if (danio > actual) {
            escribirArchivoReemplazar(ARCHIVO_DANIO_MAX, String.valueOf(danio));
        }
    }

    /** Devuelve el mayor daño histórico; si no existe el archivo, retorna 0. */
    public static int leerMayorDanio() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_DANIO_MAX))) {
            String s = br.readLine();
            return (s == null) ? 0 : Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    // -------------------- HELPERS ----------------------
    private static List<String[]> leerArchivoCSV(String ruta) {
        List<String[]> filas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) filas.add(linea.split(";", -1));
            }
        } catch (IOException ignored) { }
        return filas;
    }

    private static void escribirLineaAppend(String ruta, String linea) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir (" + ruta + "): " + e.getMessage());
        }
    }

    private static void escribirArchivoReemplazar(String ruta, String contenido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, false))) {
            bw.write(contenido);
        } catch (IOException e) {
            System.err.println("Error al reemplazar (" + ruta + "): " + e.getMessage());
        }
    }

    private static String nullSafe(String s) {
        return (s == null) ? "" : s.replaceAll("[\\r\\n]", " ").trim();
    }
    private static final String DIR_ARCHIVOS   = "archivos";
    private static final String DIR_SNAPSHOTS  = DIR_ARCHIVOS + File.separator + "snapshots";

    static {
        asegurarDir(DIR_ARCHIVOS);
        asegurarDir(DIR_SNAPSHOTS);
    }

    private static void asegurarDir(String d) {
        File f = new File(d);
        if (!f.exists()) f.mkdirs();
    }

    // === NUEVO: guardar transcript de una batalla ===
    // Guarda log completo de eventos + resumen al final.
    public static File guardarTranscriptBatalla(List<String> eventos,
                                                String heroe, String villano,
                                                String ganador, int turnos) {
        String ts = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File out = new File(DIR_ARCHIVOS, "batalla_" + ts + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {
            bw.write("Héroe: " + heroe + " | Villano: " + villano);
            bw.newLine();
            bw.write("Ganador: " + ganador + " | Turnos: " + turnos);
            bw.newLine();
            bw.write("------------------------------------------------------------");
            bw.newLine();
            for (String e : eventos) {
                bw.write(e);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando transcript: " + e.getMessage());
        }
        return out;
    }

    // === SNAPSHOT de partida (para Guardar/Cargar) ===
    public static class Snapshot {
        public String apodoHeroe, apodoVillano;
        public int vidaH, fuerzaH, defensaH, bendH;
        public int vidaV, fuerzaV, defensaV, bendV;
        public int cantidadBatallas;
        public boolean supremos;
    }

    public static File guardarSnapshot(Snapshot s) {
        String ts = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File out = new File(DIR_SNAPSHOTS, "snap_" + ts + ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {
            // formato simple clave=valor
            bw.write("apodoHeroe=" + nullSafe(s.apodoHeroe)); bw.newLine();
            bw.write("apodoVillano=" + nullSafe(s.apodoVillano)); bw.newLine();
            bw.write("vidaH=" + s.vidaH); bw.newLine();
            bw.write("fuerzaH=" + s.fuerzaH); bw.newLine();
            bw.write("defensaH=" + s.defensaH); bw.newLine();
            bw.write("bendH=" + s.bendH); bw.newLine();
            bw.write("vidaV=" + s.vidaV); bw.newLine();
            bw.write("fuerzaV=" + s.fuerzaV); bw.newLine();
            bw.write("defensaV=" + s.defensaV); bw.newLine();
            bw.write("bendV=" + s.bendV); bw.newLine();
            bw.write("cantidadBatallas=" + s.cantidadBatallas); bw.newLine();
            bw.write("supremos=" + s.supremos); bw.newLine();
        } catch (IOException e) {
            System.err.println("Error guardando snapshot: " + e.getMessage());
        }
        return out;
    }

    public static java.util.List<File> listarSnapshots() {
        File dir = new File(DIR_SNAPSHOTS);
        File[] arr = dir.listFiles((d, name) -> name.startsWith("snap_") && name.endsWith(".txt"));
        java.util.List<File> out = new java.util.ArrayList<>();
        if (arr != null) java.util.Arrays.sort(arr);
        if (arr != null) for (File f : arr) out.add(f);
        return out;
    }

    public static Snapshot leerSnapshot(File f) {
        Snapshot s = new Snapshot();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                int i = line.indexOf('=');
                if (i < 0) continue;
                String k = line.substring(0, i).trim();
                String v = line.substring(i+1).trim();
                switch (k) {
                    case "apodoHeroe": s.apodoHeroe = v; break;
                    case "apodoVillano": s.apodoVillano = v; break;
                    case "vidaH": s.vidaH = parseInt(v); break;
                    case "fuerzaH": s.fuerzaH = parseInt(v); break;
                    case "defensaH": s.defensaH = parseInt(v); break;
                    case "bendH": s.bendH = parseInt(v); break;
                    case "vidaV": s.vidaV = parseInt(v); break;
                    case "fuerzaV": s.fuerzaV = parseInt(v); break;
                    case "defensaV": s.defensaV = parseInt(v); break;
                    case "bendV": s.bendV = parseInt(v); break;
                    case "cantidadBatallas": s.cantidadBatallas = parseInt(v); break;
                    case "supremos": s.supremos = Boolean.parseBoolean(v); break;
                }
            }
        } catch (IOException ignored) {}
        return s;
    }

    private static int parseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; }
    }
        // ====== JUGADORES (persistencia lista de registro) ======
    public static java.util.Map<String,String> cargarJugadoresMapa() {
    java.util.Map<String,String> map = new java.util.LinkedHashMap<>();
    for (String[] r : leerArchivoCSV(ARCHIVO_JUGADORES)) {
        if (r.length >= 2) {
            String nombre = r[0];
            String apodo  = r[1];
            map.put(apodo, nombre);
        }
    }
    return map;
}
    // Formato: nombre;apodo;tipo  (tipo = "Héroe" o "Villano")

    public static void guardarJugador(String nombre, String apodo, String tipo) {
        // evitar duplicados por apodo
        if (existeJugador(apodo)) return;
        String linea = String.format("%s;%s;%s", nullSafe(nombre), nullSafe(apodo), nullSafe(tipo));
        escribirLineaAppend(ARCHIVO_JUGADORES, linea);
    }

    public static boolean existeJugador(String apodo) {
        for (String[] j : leerJugadores()) {
            if (j.length >= 2 && j[1].equalsIgnoreCase(apodo)) return true;
        }
        return false;
    }

    public static java.util.List<String[]> leerJugadores() {
        return leerArchivoCSV(ARCHIVO_JUGADORES); // reutilizamos helper CSV
    }

    /** Borra jugador por apodo reescribiendo el archivo filtrado */
    public static void borrarJugadorPorApodo(String apodo) {
        java.util.List<String[]> todos = leerJugadores();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_JUGADORES, false))) {
            for (String[] j : todos) {
                if (j.length < 2) continue;
                if (j[1].equalsIgnoreCase(apodo)) continue; // saltar el que se elimina
                bw.write(String.join(";", j));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al borrar jugador: " + e.getMessage());
        }
    }
}
