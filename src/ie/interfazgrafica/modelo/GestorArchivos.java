package ie.interfazgrafica.modelo;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Persistencia simple en archivos de texto.
 * Formatos:
 * - historial_batallas.txt : fecha;heroe;villano;ganador;turnos
 * - personajes.txt         : nombre;tipo;vidaFinal;victorias;supremosUsados;armasInvocadas
 * - estadisticas_danio_max.txt : número (mayor daño histórico)
 * - jugadores.txt          : nombre;apodo;tipo
 */
public class GestorArchivos {

    // ===================== RUTAS Y FORMATOS =====================
    private static final String ARCHIVO_HISTORIAL   = "historial_batallas.txt";
    private static final String ARCHIVO_PERSONAJES  = "personajes.txt";
    private static final String ARCHIVO_DANIO_MAX   = "estadisticas_danio_max.txt";
    private static final String ARCHIVO_JUGADORES   = "jugadores.txt";

    private static final String DIR_ARCHIVOS            = "archivos";
    private static final String DIR_PARTIDAS_GUARDADAS  = DIR_ARCHIVOS + File.separator + "partidasGuardadas";

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Crea carpetas necesarias apenas se carga la clase
    static {
        asegurarDir(DIR_ARCHIVOS);
        asegurarDir(DIR_PARTIDAS_GUARDADAS);
    }

    // ========================= API PÚBLICA =========================
    // -------- HISTORIAL --------
    public static void guardarBatalla(String heroe, String villano, String ganador, int turnos) {
        String fecha = LocalDateTime.now().format(FMT);
        String linea = String.format("%s;%s;%s;%s;%d", fecha, heroe, villano, ganador, turnos);
        escribirLineaAppend(ARCHIVO_HISTORIAL, linea);
    }

    public static List<String[]> leerHistorial() {
        return leerArchivoCSV(ARCHIVO_HISTORIAL);
    }

    // -------- RANKING (PERSONAJES) --------
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

    // -------- ESTADÍSTICAS (MAYOR DAÑO) --------
    /** Actualiza el mayor daño histórico si 'danio' supera el almacenado. */
    public static void registrarDanio(int danio) {
        if (danio <= 0) return;
        int actual = leerMayorDanio();
        if (danio > actual) {
            escribirArchivoReemplazar(ARCHIVO_DANIO_MAX, String.valueOf(danio));
        }
    }

    /** Devuelve el mayor daño histórico (o 0 si no hay dato). */
    public static int leerMayorDanio() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_DANIO_MAX))) {
            String s = br.readLine();
            return (s == null) ? 0 : Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    // -------- TRANSCRIPTO (LOG COMPLETO DE BATALLA) --------
    public static File guardarTranscriptBatalla(List<String> eventos,
                                                String heroe, String villano,
                                                String ganador, int turnos) {
        String ts = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File out = new File(DIR_ARCHIVOS, "batalla_" + ts + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {
            bw.write("Heroe: " + heroe + " | Villano: " + villano); bw.newLine();
            bw.write("Ganador: " + ganador + " | Turnos: " + turnos); bw.newLine();
            bw.write("------------------------------------------------------------"); bw.newLine();
            for (String e : eventos) { bw.write(e); bw.newLine(); }
        } catch (IOException e) {
            System.err.println("Error guardando transcript: " + e.getMessage());
        }
        return out;
    }

    // -------- PARTIDAS GUARDADAS --------
    public static class PartidaGuardada {
        public String apodoHeroe, apodoVillano;
        public int vidaH, fuerzaH, defensaH, bendH;
        public int vidaV, fuerzaV, defensaV, bendV;
        public int cantidadBatallas;
        public boolean supremos;
    }

    public static File guardarPartidaGuardada(PartidaGuardada p) {
        String ts = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File out = new File(DIR_PARTIDAS_GUARDADAS, "partida_" + ts + ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {
            // formato simple clave=valor
            bw.write("apodoHeroe=" + nullSafe(p.apodoHeroe)); bw.newLine();
            bw.write("apodoVillano=" + nullSafe(p.apodoVillano)); bw.newLine();
            bw.write("vidaH=" + p.vidaH); bw.newLine();
            bw.write("fuerzaH=" + p.fuerzaH); bw.newLine();
            bw.write("defensaH=" + p.defensaH); bw.newLine();
            bw.write("bendH=" + p.bendH); bw.newLine();
            bw.write("vidaV=" + p.vidaV); bw.newLine();
            bw.write("fuerzaV=" + p.fuerzaV); bw.newLine();
            bw.write("defensaV=" + p.defensaV); bw.newLine();
            bw.write("bendV=" + p.bendV); bw.newLine();
            bw.write("cantidadBatallas=" + p.cantidadBatallas); bw.newLine();
            bw.write("supremos=" + p.supremos); bw.newLine();
        } catch (IOException e) {
            System.err.println("Error guardando partida: " + e.getMessage());
        }
        return out;
    }

    public static List<File> listarPartidasGuardadas() {
        File dir = new File(DIR_PARTIDAS_GUARDADAS);
        File[] arr = dir.listFiles((d, name) -> name.startsWith("partida_") && name.endsWith(".txt"));
        List<File> out = new ArrayList<>();
        if (arr != null) Arrays.sort(arr);
        if (arr != null) for (File f : arr) out.add(f);
        return out;
    }

    public static PartidaGuardada leerPartidaGuardada(File f) {
        PartidaGuardada p = new PartidaGuardada();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                int i = line.indexOf('=');
                if (i < 0) continue;
                String k = line.substring(0, i).trim();
                String v = line.substring(i + 1).trim();
                switch (k) {
                    case "apodoHeroe": p.apodoHeroe = v; break;
                    case "apodoVillano": p.apodoVillano = v; break;
                    case "vidaH": p.vidaH = parseInt(v); break;
                    case "fuerzaH": p.fuerzaH = parseInt(v); break;
                    case "defensaH": p.defensaH = parseInt(v); break;
                    case "bendH": p.bendH = parseInt(v); break;
                    case "vidaV": p.vidaV = parseInt(v); break;
                    case "fuerzaV": p.fuerzaV = parseInt(v); break;
                    case "defensaV": p.defensaV = parseInt(v); break;
                    case "bendV": p.bendV = parseInt(v); break;
                    case "cantidadBatallas": p.cantidadBatallas = parseInt(v); break;
                    case "supremos": p.supremos = Boolean.parseBoolean(v); break;
                }
            }
        } catch (IOException ignored) {}
        return p;
    }

    // -------- JUGADORES --------
    /** Carga un mapa apodo->nombre desde jugadores.txt. */
    public static Map<String,String> cargarJugadoresMapa() {
        Map<String,String> map = new LinkedHashMap<>();
        for (String[] r : leerArchivoCSV(ARCHIVO_JUGADORES)) {
            if (r.length >= 2) {
                String nombre = r[0];
                String apodo  = r[1];
                map.put(apodo, nombre);
            }
        }
        return map;
    }

    /** Formato guardado: nombre;apodo;tipo  (tipo = "Héroe" o "Villano") */
    public static void guardarJugador(String nombre, String apodo, String tipo) {
        if (existeJugador(apodo)) return; // evita duplicados por apodo
        String linea = String.format("%s;%s;%s", nullSafe(nombre), nullSafe(apodo), nullSafe(tipo));
        escribirLineaAppend(ARCHIVO_JUGADORES, linea);
    }

    public static boolean existeJugador(String apodo) {
        for (String[] j : leerJugadores()) {
            if (j.length >= 2 && j[1].equalsIgnoreCase(apodo)) return true;
        }
        return false;
    }

    public static List<String[]> leerJugadores() {
        return leerArchivoCSV(ARCHIVO_JUGADORES);
    }

    /** Borra un jugador por apodo reescribiendo el archivo filtrado. */
    public static void borrarJugadorPorApodo(String apodo) {
        List<String[]> todos = leerJugadores();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_JUGADORES, false))) {
            for (String[] j : todos) {
                if (j.length < 2) continue;
                if (j[1].equalsIgnoreCase(apodo)) continue; // salta el que se elimina
                bw.write(String.join(";", j));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al borrar jugador: " + e.getMessage());
        }
    }

    // ======================== HELPERS PRIVADOS ========================
    private static void asegurarDir(String d) {
        File f = new File(d);
        if (!f.exists()) f.mkdirs();
    }

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

    private static int parseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; }
    }
}
