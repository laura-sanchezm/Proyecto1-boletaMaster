package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Tiquetes.Tiquete;
import Tiquetes.TiqueteMultiple;
import Tiquetes.TiqueteSimple;
import Tiquetes.estadoTiquete;
import modelo.Administrador;
import modelo.Cliente;
import modelo.Evento;
import modelo.Localidad;
import modelo.Organizador;
import modelo.Venue;
import pagos.metodoPago;

public class VentaTiquetesTest {
	
	private Cliente cliente;
    private Organizador organizador;
    private Administrador administrador;
    private Evento evento;
    private Localidad localidad;
    
    @BeforeEach
    void setUp() throws Exception {
        cliente = new Cliente("cliente1", "pass123");
        organizador = new Organizador("org1", "pass456", 1);
        administrador = new Administrador("admin1", "adminpass");
        
        // Configurar cargos
        administrador.fijarCargoServicio(10); 
        administrador.fijarCargoImpresion(5000); 
        
        // Crear venue y evento
        Venue venue = new Venue("Movistar Arena", 15000, "Estadio", "Bogotá", "Mayores de 18");
        administrador.registrarVenue(venue);
        administrador.aprobarVenue("Movistar Arena", LocalDate.of(2025, 12, 15));
        
        evento = organizador.crearEvento(1, "Concierto Rock", 
                LocalDate.of(2025, 12, 15), 
                LocalTime.of(20, 0), 
                "Concierto", 
                venue);
        
        // Crear localidad y tiquetes
        localidad = new Localidad(1, "VIP", 100000, 10);
        evento.addLocalidad(localidad);
        
        // Generar tiquetes disponibles
        for (int i = 1; i <= 10; i++) {
            TiqueteSimple t = new TiqueteSimple(i, true, null, 
                    estadoTiquete.DISPONIBLE, "simple", evento, localidad);
            localidad.addTiquete(t);
        }
        
        // Cliente con saldo
        cliente.recargarSaldo(500000);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        cliente = null;
        organizador = null;
        administrador = null;
        evento = null;
        localidad = null;
    }
    
    @Test
    void testComprarTiqueteSimple() {
        int cantidadInicial = cliente.getTiquetes().size();
        double saldoInicial = cliente.consultarSaldo();
        
        cliente.comprarTiqueteSimple(evento, localidad, 1, metodoPago.SALDO, administrador);
        
        assertEquals(cantidadInicial + 1, cliente.getTiquetes().size(), 
                "El cliente debe tener un tiquete más");
        assertTrue(cliente.consultarSaldo() < saldoInicial, 
                "El saldo debe disminuir después de la compra");
    }
    
    @Test
    void testComprarMultiplesTiquetes() {
        cliente.comprarTiqueteSimple(evento, localidad, 3, metodoPago.SALDO, administrador);
        
        assertEquals(3, cliente.getTiquetes().size(), 
                "El cliente debe tener 3 tiquetes");
    }
    
    @Test
    void testComprarSinSaldo() {
        Cliente clientePobre = new Cliente("pobre", "pass");
        clientePobre.recargarSaldo(1000);
        
        assertThrows(IllegalStateException.class, () -> {
            clientePobre.comprarTiqueteSimple(evento, localidad, 1, metodoPago.SALDO, administrador);
        }, "Debe lanzar excepción por saldo insuficiente");
    }
    
    @Test
    void testComprarMasDeLosDisponibles() {
        assertThrows(IllegalArgumentException.class, () -> {
            cliente.comprarTiqueteSimple(evento, localidad, 15, metodoPago.SALDO, administrador);
        }, "Debe lanzar excepción cuando no hay suficientes tiquetes");
    }
    
    @Test
    void testEstadoTiqueteComprado() {
        cliente.comprarTiqueteSimple(evento, localidad, 1, metodoPago.SALDO, administrador);
        
        Tiquete comprado = cliente.getTiquetes().iterator().next();
        assertEquals(estadoTiquete.COMPRADO, comprado.getStatus(), 
                "El estado del tiquete debe ser COMPRADO");
        assertEquals("cliente1", comprado.getPropietario(), 
                "El propietario debe ser cliente1");
    }
    
    @Test
    void testAsientoNumerado() {
        localidad.setNumerada(true);
        cliente.comprarTiqueteSimple(evento, localidad, 1, metodoPago.SALDO, administrador);
        
        Tiquete comprado = cliente.getTiquetes().iterator().next();
        if (comprado instanceof TiqueteSimple) {
            TiqueteSimple simple = (TiqueteSimple) comprado;
            assertTrue(simple.getNumAsiento() > 0, 
                    "Debe tener asiento asignado");
            assertTrue(simple.getNumAsiento() <= localidad.getCapacidadL(), 
                    "El asiento debe estar dentro de la capacidad");
        }
    }
    
    @Test
    void testComprarTiqueteMultiple() {
    	// Crear segundo evento
        Venue venue2 = new Venue("Teatro", 500, "Teatro", "Bogotá", "Ninguna");
        administrador.registrarVenue(venue2);
        administrador.aprobarVenue("Teatro", LocalDate.of(2025, 12, 20));
        
        Evento evento2 = organizador.crearEvento(2, "Obra", 
                LocalDate.of(2025, 12, 20), 
                LocalTime.of(19, 0), 
                "Teatro", 
                venue2);
        
        Localidad loc2 = new Localidad(2, "Platea", 50000, 10);
        evento2.addLocalidad(loc2);
        
        // Crear paquete
        TiqueteSimple t1 = new TiqueteSimple(100, true, null, 
                estadoTiquete.DISPONIBLE, "simple", evento, localidad);
        TiqueteSimple t2 = new TiqueteSimple(101, true, null, 
                estadoTiquete.DISPONIBLE, "simple", evento2, loc2);
        
        TiqueteMultiple paquete = new TiqueteMultiple(200, true, null, 
                estadoTiquete.DISPONIBLE, "multiple");
        // Si tu clase tiene addEntrada, usa ese. Si no, usa getEntradas().add()
        paquete.getEntradas().add(t1);
        paquete.getEntradas().add(t2);
        
        cliente.comprarTiqueteMultiple(evento, paquete, metodoPago.SALDO, administrador);
        
        assertTrue(cliente.getTiquetes().contains(paquete), 
                "El cliente debe tener el paquete");
        assertEquals(estadoTiquete.COMPRADO, paquete.getStatus(), 
                "El estado del paquete debe ser COMPRADO");
    }

}
