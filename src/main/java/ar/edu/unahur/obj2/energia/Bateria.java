package ar.edu.unahur.obj2.energia;

import ar.edu.unahur.obj2.energia.notificaciones.Notificable;

import java.util.ArrayList;
import java.util.List;

public class Bateria {

    private final String identificador;
    private double nivelEnergia;
    private final double LIMITE_RESERVA = 5000;
    private List<Notificable> suscriptores;

    public Bateria(String identificador, double nivelEnergia) {
        this.identificador = identificador;
        this.nivelEnergia = nivelEnergia;
        this.suscriptores = new ArrayList<>();
    }

    public String getIdentificador() {
        return identificador;
    }

    public double getNivelEnergia() {
        return nivelEnergia;
    }

    public void cargar(double kwh) throws LimiteReservaException {
        if (kwh <= 0) {
            throw new EnergiaInvalidaException("el valor de la carga debe de ser mayor a cero");
        }
        nivelEnergia += kwh;
        notificarSuscriptores("carga", kwh);
    }

    public void consumir(double kwh) throws LimiteReservaException {
        if (kwh <= 0) {
            throw new EnergiaInvalidaException("el valor de consumo debe de ser mayor a cero");
        }
        if ((nivelEnergia - kwh) < -LIMITE_RESERVA) {
            throw new LimiteReservaException(
                "el consumo supera el límite de reserva de la batería " + identificador
            );
        }
        nivelEnergia -= kwh;
        notificarSuscriptores("consumo", kwh);
    }

    public void agregarSuscriptor(Notificable suscriptor) {
        suscriptores.add(suscriptor);
    }

    public void removerSuscriptor(Notificable suscriptor) {
        suscriptores.remove(suscriptor);
    }

    public List<Notificable> getSuscriptores() {
        return suscriptores;
    }

    private void notificarSuscriptores(String tipoMovimiento, double kwh) {
        suscriptores.forEach(s -> s.reaccionar(this, tipoMovimiento, kwh));
    }
}