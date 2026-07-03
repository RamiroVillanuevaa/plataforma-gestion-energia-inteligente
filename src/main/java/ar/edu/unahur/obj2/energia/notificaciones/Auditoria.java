package ar.edu.unahur.obj2.energia.notificaciones;

import ar.edu.unahur.obj2.energia.Bateria;

import java.util.ArrayList;
import java.util.List;

public class Auditoria implements Notificable {

    private List<String> movimientos = new ArrayList<>();

    @Override
    public void reaccionar(Bateria bateria, String tipoMovimiento, double kwh) {
        String registro = "movimiento registrado: " + tipoMovimiento + " de " + kwh + " kWh en bateria " + bateria.getIdentificador();
            movimientos.add(registro);
        System.out.println(registro);
    }

    public List<String> getMovimientos() {
        return movimientos;
    }
}
