package ar.edu.unahur.obj2.energia.operaciones;

import ar.edu.unahur.obj2.energia.Bateria;
import ar.edu.unahur.obj2.energia.EnergiaInvalidaException;

public abstract class Operacion implements Operable {

    protected final Bateria bateria;
    protected final double kwh;
    protected Boolean ejecutado = Boolean.FALSE;

    public Operacion(Bateria bateria, double kwh) {
            if (kwh <= 0) {
            throw new EnergiaInvalidaException("el valor debe de ser mayor a cero");
        }
        this.bateria = bateria;
        this.kwh = kwh;
    }
}
