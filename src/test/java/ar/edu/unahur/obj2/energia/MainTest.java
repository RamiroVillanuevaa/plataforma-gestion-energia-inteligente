package ar.edu.unahur.obj2.energia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.energia.controlador.Controlador;
import ar.edu.unahur.obj2.energia.notificaciones.Auditoria;
import ar.edu.unahur.obj2.energia.notificaciones.AlarmReservaCritica;
import ar.edu.unahur.obj2.energia.notificaciones.NotificacionAdministrador;
import ar.edu.unahur.obj2.energia.operaciones.OperacionCarga;
import ar.edu.unahur.obj2.energia.operaciones.OperacionConsumo;
import ar.edu.unahur.obj2.energia.operaciones.Rutina;

public class MainTest {

    private Bateria bateria1;
    private Controlador controlador;
    private Auditoria auditoria;
    private NotificacionAdministrador notificacionAdmin;
    private AlarmReservaCritica alarma;

    @BeforeEach
    void ObjetosParaTest() {
        bateria1 = new Bateria("BAT-01", 10000);
        controlador = new Controlador();
        auditoria = new Auditoria();
        notificacionAdmin = new NotificacionAdministrador();
        alarma = new AlarmReservaCritica();
    }

    @Test
    void dadaUnaBateria_CuandoSeCarga_ElNivelAumenta() throws LimiteReservaException {
        controlador.ejecutar(new OperacionCarga(bateria1, 2000));
        assertEquals(12000, bateria1.getNivelEnergia());
    }

    @Test
    void dadaUnaBateria_CuandoSeConsume_ElNivelDisminuye() throws LimiteReservaException {
        controlador.ejecutar(new OperacionConsumo(bateria1, 3000));
        assertEquals(7000, bateria1.getNivelEnergia());
    }

    @Test
    void dadaUnaOperacionCarga_CuandoSeDesha_ElNivelVuelveAlOriginal() throws LimiteReservaException {
        OperacionCarga carga = new OperacionCarga(bateria1, 2000);
        controlador.ejecutar(carga);
        controlador.deshacer();
        assertEquals(10000, bateria1.getNivelEnergia());
    }

    @Test
    void dadaUnaOperacionConsumo_CuandoSeDeshace_ElNivelVuelveAlOriginal() throws LimiteReservaException {
        OperacionConsumo consumo = new OperacionConsumo(bateria1, 3000);
        controlador.ejecutar(consumo);
        controlador.deshacer();
        assertEquals(10000, bateria1.getNivelEnergia());
    }

    @Test
    void dadaUnaRutina_CuandoSeEjecuta_ElNivelEsElEsperado() throws LimiteReservaException {
        controlador.agregarARutina(new OperacionCarga(bateria1, 5000));
        controlador.agregarARutina(new OperacionConsumo(bateria1, 2000));
        controlador.ejecutarRutina();
        assertEquals(13000, bateria1.getNivelEnergia());
    }

    @Test
    void dadaUnaRutina_CuandoSeDeshace_ElNivelVuelveAlOriginal() throws LimiteReservaException {
        controlador.agregarARutina(new OperacionCarga(bateria1, 5000));
        controlador.agregarARutina(new OperacionConsumo(bateria1, 2000));
        controlador.ejecutarRutina();
        controlador.deshacer();
        assertEquals(10000, bateria1.getNivelEnergia());
    }

   @Test

    void dadaUnaRutina_CuandoUnaOperacionFalla_SeReviertenLasAnteriores() {
     controlador.agregarARutina(new OperacionCarga(bateria1, 5000));
        controlador.agregarARutina(new OperacionConsumo(bateria1, 20000));

        try {
            controlador.ejecutarRutina();
        } catch (LimiteReservaException e) {
            assertEquals(10000, bateria1.getNivelEnergia());
        }
    }

    @Test
    void dadaUnaOperacionConValorCero_SeLanzaEnergiaInvalidaException() {
        assertThrows(EnergiaInvalidaException.class, () -> new OperacionCarga(bateria1, 0));
    }

    @Test
    void dadaUnaBateria_CuandoElConsumoSuperaElLimite_SeLanzaLimiteReservaException() {
        assertThrows(LimiteReservaException.class, () ->
            controlador.ejecutar(new OperacionConsumo(bateria1, 20000))
        );
    }

    @Test
    void dadaUnaBateriaConSuscriptores_CuandoSeCarga_AuditoriaRegistraElMovimiento() throws LimiteReservaException {
        bateria1.agregarSuscriptor(auditoria);
        controlador.ejecutar(new OperacionCarga(bateria1, 2000));
        assertTrue(auditoria.getMovimientos().contains(
            "movimiento registrado: carga de 2000.0 kWh en bateria BAT-01"
        ));
    }

    @Test
    void dadaUnaBateriaConSuscriptores_CuandoSeAgregaYRemueve_LaListaCambia() {
        bateria1.agregarSuscriptor(auditoria);
        bateria1.agregarSuscriptor(alarma);
        bateria1.removerSuscriptor(auditoria);
        assertEquals(1, bateria1.getSuscriptores().size());
        assertTrue(bateria1.getSuscriptores().contains(alarma));
    }
}
