package ar.edu.unahur.obj2.energia.notificaciones;

import ar.edu.unahur.obj2.energia.Bateria;

public class AlarmReservaCritica implements Notificable {

    @Override
    public void reaccionar(Bateria bateria, String tipoMovimiento, double kwh) {
        if (bateria.getNivelEnergia() < 0) {
            System.out.println("PRECAUCION: el nivel de bateria " + bateria.getIdentificador() + "esta en reserva critica. Cantida de bateria actual: " + bateria.getNivelEnergia() + " kWh.");
        }
    }
}
