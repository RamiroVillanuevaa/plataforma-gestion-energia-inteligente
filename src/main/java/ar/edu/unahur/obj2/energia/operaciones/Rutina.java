package ar.edu.unahur.obj2.energia.operaciones;

import ar.edu.unahur.obj2.energia.LimiteReservaException;

import java.util.ArrayList;
import java.util.List;

public class Rutina implements Operable {

    private List<Operable> operaciones = new ArrayList<>();
    private Boolean ejecutado = Boolean.FALSE;

    public void agregarOperacion(Operable operacion) {
        operaciones.add(operacion);
    }

    public void vaciarRutina() {
        operaciones.clear();
            ejecutado = Boolean.FALSE;
    }

    @Override
    public void ejecutar() throws LimiteReservaException {
        if (!ejecutado) {
            List<Operable> ejecutadas = new ArrayList<>();
            try {
                for (Operable operacion : operaciones) {
                    operacion.ejecutar();
                    ejecutadas.add(operacion);
                }
                ejecutado = Boolean.TRUE;
            } catch (LimiteReservaException e) {
                for (int i = ejecutadas.size() - 1; i >= 0; i--) {
                    ejecutadas.get(i).deshacer();
                }
                throw e;
            }
        }
    }

    @Override
    public void deshacer() throws LimiteReservaException {
        if (ejecutado) {
            for (int i = operaciones.size() - 1; i >= 0; i--) {
                operaciones.get(i).deshacer();
            }
            ejecutado = Boolean.FALSE;
        }
    }
}
