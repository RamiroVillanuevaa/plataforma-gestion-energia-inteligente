package ar.edu.unahur.obj2.energia.operaciones;

import ar.edu.unahur.obj2.energia.Bateria;
import ar.edu.unahur.obj2.energia.LimiteReservaException;

public class OperacionConsumo extends Operacion {

    public OperacionConsumo(Bateria bateria, double kwh) {
        super(bateria, kwh);
    }

    @Override
        public void ejecutar() throws LimiteReservaException {
        if (!ejecutado) {
            bateria.consumir(kwh);
            ejecutado = Boolean.TRUE;
        }
    }

    @Override
    public void deshacer() throws LimiteReservaException {
        if (ejecutado) {
                bateria.cargar(kwh);
            ejecutado = Boolean.FALSE;
        }
    }
}
