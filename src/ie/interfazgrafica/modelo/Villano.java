/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.interfazgrafica.modelo;


public class Villano extends Personaje {

    private boolean leviatanActivo = false;
    private int leviatanTurnosRestantes = 0;
    private Personaje leviatanObjetivo;

    public Villano(String nombre, int vida, int fuerza, int defensa, int porcentajeBendicion) {
        super(nombre, vida, fuerza, defensa, new BendicionDelVacio(), porcentajeBendicion);
    }

    // Inicia el casteo del Leviatán
    public void iniciarCasteoLeviatan(Personaje objetivo, int turnos) {
        leviatanActivo = true;
        leviatanTurnosRestantes = turnos;
        leviatanObjetivo = objetivo;
    }

    @Override
    public void aplicarEstadosAlInicioDelTurno() {
        super.aplicarEstadosAlInicioDelTurno();

        if (leviatanActivo) {
            // caster muerto → interrumpido
            if (!this.estaVivo()) {
                Reportes.registrarEvento(getNombre() + " invoco \"Leviatan del Vacio\" (interrumpido)");

                leviatanActivo = false;
                leviatanObjetivo = null;
                return;
            }

            // objetivo muerto → interrumpido
            if (leviatanObjetivo == null || !leviatanObjetivo.estaVivo()) {
                Reportes.registrarEvento(getNombre() + " invoco \"Leviatan del Vacio\" (interrumpido: objetivo caido)");

                leviatanActivo = false;
                leviatanObjetivo = null;
                return;
            }

            // avanza casteo
            leviatanTurnosRestantes--;

            // impacto al terminar
            if (leviatanTurnosRestantes <= 0) {
                int danio = leviatanObjetivo.getVida(); // 100% de la vida actual
                leviatanObjetivo.recibirDanioDirecto(danio);
                Reportes.registrarEvento(getNombre() + " invoco \"Leviatan del Vacio\" (interrumpido: objetivo caido)");

                leviatanActivo = false;
                leviatanObjetivo = null;
            }
        }
    }

    @Override
    public void decidirAccion(Personaje enemigo) {
        // Usa Leviatán solo si está habilitado
        if (!leviatanActivo && porcentajeBendicion == 100 && getSupremosUsados() == 0 && isSupremosHabilitados()) {
            new LeviatanDelVacio(this).ejecutar(enemigo);
            return;
        }
        if (porcentajeBendicion == 100 && getSupremosUsados() == 0 && !isSupremosHabilitados()) {
    Reportes.registrarEvento(getNombre() + " alcanzo 100% de maldicion pero los ataques supremos estan DESACTIVADOS.");
}
        // Si no, combate normal
        if (armaActual == null || rnd.nextDouble() < 0.3) {
            invocarArma();
        } else {
            atacar(enemigo);
        }
    }   
}