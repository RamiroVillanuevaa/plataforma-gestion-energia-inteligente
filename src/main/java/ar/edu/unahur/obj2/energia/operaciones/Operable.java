package ar.edu.unahur.obj2.energia.operaciones;

import ar.edu.unahur.obj2.energia.LimiteReservaException;

public interface Operable {
    void ejecutar() throws LimiteReservaException;
    void deshacer() throws LimiteReservaException;
}
