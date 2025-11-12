package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import modelo.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class AdministradorTest {
    
    private Administrador administrador;
    private Venue venue;
    private Evento evento;
    private Organizador organizador;
    
    @BeforeEach
    void setUp() {
        administrador = new Administrador("admin1", "passAdmin");
        venue = new Venue("Movistar Arena", 15000, "Estadio", "Bogotá", "Sin restricciones");
        organizador = new Organizador("org1", "pass", 1);
        
        LocalDate fecha = LocalDate.of(2025, 12, 15);
        LocalTime hora = LocalTime.of(20, 0);
        evento = new Evento(1, "Concierto", fecha, hora, "Concierto", 
                           estadoEvento.PROGRAMADO, venue, organizador);
    }
    
    @AfterEach
    void tearDown() {
        administrador = null;
        venue = null;
        evento = null;
        organizador = null;
    }
    
    @Test
    void testGetLogin() {
        assertEquals("admin1", administrador.getLogin());
    }
    
    @Test
    void testVenuesInicialmenteVacio() {
        assertNotNull(administrador.getVenues());
        assertTrue(administrador.getVenues().isEmpty());
    }
    
    @Test
    void testRegistrarVenue() {
        administrador.registrarVenue(venue);
        
        assertEquals(1, administrador.getVenues().size());
        assertTrue(administrador.getVenues().contains(venue));
    }
    
    @Test
    void testAprobarVenue() {
        administrador.registrarVenue(venue);
        assertFalse(venue.getAprobado());
        
        LocalDate fecha = LocalDate.of(2025, 12, 15);
        administrador.aprobarVenue("Movistar Arena", fecha);
        
        assertTrue(venue.getAprobado());
    }
    
    @Test
    void testRegistrarEvento() {
        administrador.registrarEvento(evento);
        
        assertEquals(1, administrador.getEventos().size());
        assertTrue(administrador.getEventos().contains(evento));
    }
    
    @Test
    void testCancelarEvento() {
        administrador.registrarEvento(evento);
        
        administrador.cancelarEvento(1);
        
        assertEquals(estadoEvento.CANCELADO, evento.getEstado());
    }
    
    @Test
    void testFijarCargoServicio() {
        administrador.fijarCargoServicio(10.0);
        assertEquals(0.10, administrador.getCargoServicio(), 0.001);
    }
    
    @Test
    void testFijarCargoImpresion() {
        administrador.fijarCargoImpresion(5000.0);
        assertEquals(5000.0, administrador.getCargoImpresion(), 0.01);
    }
    
    @Test
    void testGenerarIdPago() {
        int id1 = administrador.generarIdPago();
        int id2 = administrador.generarIdPago();
        
        assertTrue(id1 > 0);
        assertEquals(id1 + 1, id2);
    }
    
    @Test
    void testGetEstadosFinancieros() {
        assertNotNull(administrador.getEstadosFinancieros());
    }
}