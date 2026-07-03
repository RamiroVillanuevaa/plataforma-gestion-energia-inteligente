package ar.edu.unahur.obj2.energia.notificaciones;

import ar.edu.unahur.obj2.energia.Bateria;

public interface Notificable {
    void reaccionar(Bateria bateria, String tipoMovimiento, double kwh);
}
