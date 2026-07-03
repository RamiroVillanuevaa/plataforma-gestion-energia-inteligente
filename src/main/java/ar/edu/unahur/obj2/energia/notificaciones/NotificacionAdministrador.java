package ar.edu.unahur.obj2.energia.notificaciones;

import ar.edu.unahur.obj2.energia.Bateria;

public class NotificacionAdministrador implements Notificable {

    @Override
    public void reaccionar(Bateria bateria, String tipoMovimiento, double kwh) {
        if (tipoMovimiento.equals("carga")) {
            System.out.println("se cargo " + kwh + " kWh de bateria.");
        } else {
                System.out.println("se consumieron " + kwh + "de la bateria.");
        }
    }
}
