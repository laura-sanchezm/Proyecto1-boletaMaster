package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import modelo.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class OrganizadorTest {
    
    private Organizador organizador;
    private Venue venue;
    private Evento evento;
    
    @BeforeEach
    void setUp() {
        // Constructor correcto: login, password, id
        organizador = new Organizador("org1", "passOrg", 1);
        venue = new Venue("Movistar Arena", 15000, "Estadio", "Bogotá", "Sin restricciones");
        venue.setAprobado(true);
    }
    
    @AfterEach
    void tearDown() {
        organizador = null;
        venue = null;
        evento = null;
    }
    
    @Test
    void testGetLogin() {
        assertEquals("org1", organizador.getLogin());
    }
    
    @Test
    void testGetIdO() {
        assertEquals(1, organizador.getIdO());
    }
    
    @Test
    void testEventosInicialmenteVacio() {
        assertNotNull(organizador.getEventos());
        assertTrue(organizador.getEventos().isEmpty());
    }
    
    @Test
    void testCrearEvento() {
        LocalDate fecha = LocalDate.of(2025, 12, 15);
        LocalTime hora = LocalTime.of(20, 0);
        
        Evento evento = organizador.crearEvento(1, "Concierto Rock", fecha, hora, "Concierto", venue);
        
        assertNotNull(evento);
        assertEquals("Concierto Rock", evento.getNombreE());
        assertEquals(1, organizador.getEventos().size());
        assertTrue(organizador.getEventos().contains(evento));
    }
    
    @Test
    void testVerIngresosInicial() {
        double ingresos = organizador.verIngresos();
        assertEquals(0.0, ingresos, 0.01);
    }
    
    @Test
    void testPorcentajeVentaTotalSinEventos() {
        double porcentaje = organizador.porcentajeVentaTotal();
        assertEquals(0.0, porcentaje, 0.01);
    }
    
    @Test
    void testSetIdO() {
        organizador.setIdO(5);
        assertEquals(5, organizador.getIdO());
    }
}




