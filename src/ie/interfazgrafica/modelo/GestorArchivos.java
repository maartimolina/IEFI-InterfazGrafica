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
    public static void guardarPersonaje(String nombre, String tipo,
                                        int vidaFinal, int victorias,
                                        int supremosUsados, int armasInvocadas) {
        String linea = String.format("%s;%s;%d;%d;%d;%d",
                nullSafe(nombre), nullSafe(tipo),
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
}
