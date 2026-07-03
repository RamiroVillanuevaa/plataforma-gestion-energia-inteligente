package ar.edu.unahur.obj2.energia.controlador;

import ar.edu.unahur.obj2.energia.LimiteReservaException;
import ar.edu.unahur.obj2.energia.operaciones.Operable;
import ar.edu.unahur.obj2.energia.operaciones.Rutina;

public class Controlador {

    private Operable ultimaOperacion;
    private Rutina rutinaActual = new Rutina();

    public void ejecutar(Operable operacion) throws LimiteReservaException {
        operacion.ejecutar();
            ultimaOperacion = operacion;
    }

    public void deshacer() throws LimiteReservaException {
        if (ultimaOperacion != null) {
            ultimaOperacion.deshacer();
            ultimaOperacion = null;
        }
    }

    public void agregarARutina(Operable operacion) {
        rutinaActual.agregarOperacion(operacion);
    }

    public void ejecutarRutina() throws LimiteReservaException {
        ejecutar(rutinaActual);
    }

    public void vaciarRutina() {
            rutinaActual.vaciarRutina();
    }
}
