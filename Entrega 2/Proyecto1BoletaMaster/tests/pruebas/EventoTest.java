package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import modelo.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventoTest {
    
    private Evento evento;
    private Venue venue;
    private Organizador organizador;
    private Localidad localidad1;
    private Localidad localidad2;
    
    @BeforeEach
    void setUp() {
        venue = new Venue("Movistar Arena", 15000, "Estadio", "Bogotá", "Sin restricciones");
        venue.setAprobado(true);
        organizador = new Organizador("org1", "pass", 1);
        
        LocalDate fecha = LocalDate.of(2025, 12, 15);
        LocalTime hora = LocalTime.of(20, 0);
        evento = new Evento(1, "Concierto Rock", fecha, hora, "Concierto", 
                           estadoEvento.PROGRAMADO, venue, organizador);
        
        localidad1 = new Localidad(1, "VIP", 200000.0, 1000);
        localidad2 = new Localidad(2, "General", 100000.0, 10000);
    }
    
    @AfterEach
    void tearDown() {
        evento = null;
        venue = null;
        organizador = null;
        localidad1 = null;
        localidad2 = null;
    }
    
    @Test
    void testGetId() {
        assertEquals(1, evento.getIdE());
    }
    
    @Test
    void testGetNombre() {
        assertEquals("Concierto Rock", evento.getNombreE());
    }
    
    @Test
    void testGetFecha() {
        assertEquals(LocalDate.of(2025, 12, 15), evento.getFecha());
    }
    
    @Test
    void testGetHora() {
        assertEquals(LocalTime.of(20, 0), evento.getHora());
    }
    
    @Test
    void testGetTipo() {
        assertEquals("Concierto", evento.getTipoE());
    }
    
    @Test
    void testGetEstado() {
        assertEquals(estadoEvento.PROGRAMADO, evento.getEstado());
    }
    
    @Test
    void testGetVenue() {
        assertEquals(venue, evento.getVenue());
    }
    
    @Test
    void testGetOrganizador() {
        assertEquals(organizador, evento.getOrganizador());
    }
    
    @Test
    void testLocalidadesInicial() {
        assertNotNull(evento.getLocalidades());
        assertTrue(evento.getLocalidades().isEmpty());
    }
    
    @Test
    void testAddLocalidad() {
        evento.addLocalidad(localidad1);
        
        assertEquals(1, evento.getLocalidades().size());
        assertTrue(evento.getLocalidades().contains(localidad1));
    }
    
    @Test
    void testAforoDisponible() {
        assertEquals(15000, evento.aforoDisponible());
    }
    
    @Test
    void testCapacidadOcupada() {
        evento.addLocalidad(localidad1);
        evento.addLocalidad(localidad2);
        
        assertEquals(11000, evento.capacidadOcupada());
    }
    
    @Test
    void testSetEstado() {
        evento.setEstado(estadoEvento.CANCELADO);
        assertEquals(estadoEvento.CANCELADO, evento.getEstado());
    }
    
    @Test
    void testCancelacionSolicitada() {
        evento.setCancelacionSolicitada(true);
        assertTrue(evento.getCancelacionSolicitada());
    }
    
    @Test
    void testMotivoCancelacion() {
        evento.setMotivoCancelacion("Fuerza mayor");
        assertEquals("Fuerza mayor", evento.getMotivoCancelacion());
    }
}