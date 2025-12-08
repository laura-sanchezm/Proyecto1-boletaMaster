package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;
import modelo.Administrador;
import modelo.Cliente;
import modelo.Evento;
import modelo.Localidad;
import modelo.Organizador;
import modelo.Venue;
import pagos.metodoPago;

public class TransferenciaTest {
	
	private Cliente cliente1;
    private Cliente cliente2;
    private Organizador organizador;
    private Administrador administrador;
    private Evento evento;
    private Localidad localidad;
    
    @BeforeEach
    void setUp() throws Exception {
        cliente1 = new Cliente("cliente1", "pass123");
        cliente2 = new Cliente("cliente2", "pass456");
        organizador = new Organizador("org1", "orgpass", 1);
        administrador = new Administrador("admin1", "adminpass");
        
        administrador.fijarCargoServicio(10);
        administrador.fijarCargoImpresion(5000);
        
        Venue venue = new Venue("Arena", 10000, "Estadio", "Bogotá", "Ninguna");
        administrador.registrarVenue(venue);
        administrador.aprobarVenue("Arena", LocalDate.of(2025, 12, 15));
        
        evento = organizador.crearEvento(1, "Concierto", 
                LocalDate.of(2025, 12, 15), 
                LocalTime.of(20, 0), 
                "Concierto", 
                venue);
        
        localidad = new Localidad(1, "General", 50000, 10);
        evento.addLocalidad(localidad);
        
        for (int i = 1; i <= 10; i++) {
            TiqueteSimple t = new TiqueteSimple(i, true, null, 
                    estadoTiquete.DISPONIBLE, "simple", evento, localidad);
            localidad.addTiquete(t);
        }
        
        cliente1.recargarSaldo(200000);
        cliente1.comprarTiqueteSimple(evento, localidad, 1, metodoPago.SALDO, administrador);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        cliente1 = null;
        cliente2 = null;
        organizador = null;
        administrador = null;
        evento = null;
        localidad = null;
    }
    
    @Test
    void testTransferenciaExitosa() {
        Tiquete tiquete = cliente1.getTiquetes().iterator().next();
        int idT = tiquete.getIdT();
        
        boolean resultado = cliente1.transferirTiquete(idT, "cliente2", "pass123");
        
        assertTrue(resultado, 
                "La transferencia debe ser exitosa");
        assertEquals("cliente2", tiquete.getPropietario(), 
                "El nuevo propietario debe ser cliente2");
    }
    
    @Test
    void testTransferenciaPasswordIncorrecto() {
        Tiquete tiquete = cliente1.getTiquetes().iterator().next();
        int idT = tiquete.getIdT();
        
        boolean resultado = cliente1.transferirTiquete(idT, "cliente2", "password_incorrecta");
        
        assertFalse(resultado, 
                "La transferencia debe fallar con contraseña incorrecta");
        assertEquals("cliente1", tiquete.getPropietario(), 
                "El propietario debe seguir siendo cliente1");
    }
    
    @Test
    void testTransferirNoTransferible() {
        TiqueteSimple noTransferible = new TiqueteSimple(100, false, "cliente1", 
                estadoTiquete.COMPRADO, "simple", evento, localidad);
        cliente1.addTiquete(noTransferible);
        
        boolean resultado = cliente1.transferirTiquete(100, "cliente2", "pass123");
        
        assertFalse(resultado, 
                "No debe permitir transferir tiquete no transferible");
    }
    
    @Test
    void testTransferirTiqueteInexistente() {
        boolean resultado = cliente1.transferirTiquete(9999, "cliente2", "pass123");
        
        assertFalse(resultado, 
                "Debe fallar al transferir tiquete inexistente");
    }
    
    @Test
    void testTransferirTiqueteExpirado() {
        Tiquete tiquete = cliente1.getTiquetes().iterator().next();
        tiquete.setStatus(estadoTiquete.EXPIRADO);
        
        boolean resultado = cliente1.transferirTiquete(tiquete.getIdT(), "cliente2", "pass123");
        
        assertFalse(resultado, 
                "No debe permitir transferir tiquete expirado");
    }
    

}
