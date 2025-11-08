/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.interfazgrafica.modelo;

public class Heroe extends Personaje {

    public Heroe(String nombre, int vida, int fuerza, int defensa, int porcentajeBendicion) {
        super(nombre, vida, fuerza, defensa, new BendicionCelestial(), porcentajeBendicion);
    }

    @Override
    public void decidirAccion(Personaje enemigo) {
        // Usa ataque supremo solo si está habilitado
       if (porcentajeBendicion == 100 && getSupremosUsados() == 0 && isSupremosHabilitados()) {
           new CastigoBendito(this).ejecutar(enemigo);
           return;
       }
       if (porcentajeBendicion == 100 && getSupremosUsados() == 0 && !isSupremosHabilitados()) {
    Reportes.registrarEvento(getNombre() + " alcanzó 100% de bendición pero los ataques supremos están DESACTIVADOS.");
}

       // Si no, ataca normalmente
       if (armaActual == null || rnd.nextDouble() < 0.3) {
           invocarArma();
       } else {
           atacar(enemigo);
       }
    }
}
