package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import modelo.*;
import Tiquetes.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalidadTest {
    
    private Localidad localidad;
    private Evento evento;
    private Venue venue;
    private Organizador organizador;
    private TiqueteSimple tiquete;
    
    @BeforeEach
    void setUp() {
        venue = new Venue("Test Arena", 1000, "Arena", "Bogotá", "Ninguna");
        organizador = new Organizador("org1", "pass", 1);
        evento = new Evento(1, "Test Evento", LocalDate.now(), LocalTime.of(20, 0),
                           "Concierto", estadoEvento.PROGRAMADO, venue, organizador);
        
        localidad = new Localidad(1, "VIP", 100000.0, 1000);
        evento.addLocalidad(localidad);
        
        tiquete = new TiqueteSimple(1, true, null, estadoTiquete.DISPONIBLE, "SIMPLE", evento, localidad);
    }
    
    @AfterEach
    void tearDown() {
        localidad = null;
        tiquete = null;
        evento = null;
        venue = null;
        organizador = null;
    }
    
    @Test
    void testGetId() {
        assertEquals(1, localidad.getIdL());
    }
    
    @Test
    void testGetNombre() {
        assertEquals("VIP", localidad.getNombreL());
    }
    
    @Test
    void testGetPrecioBase() {
        assertEquals(100000.0, localidad.getPrecioBase(), 0.01);
    }
    
    @Test
    void testGetCapacidad() {
        assertEquals(1000, localidad.getCapacidadL());
    }
    
    @Test
    void testNumeradaInicialmenteFalso() {
        assertFalse(localidad.isNumerada());
    }
    
    @Test
    void testSetNumerada() {
        localidad.setNumerada(true);
        assertTrue(localidad.isNumerada());
    }
    
    @Test
    void testGetTiquetes() {
        assertNotNull(localidad.getTiquetes());
        assertTrue(localidad.getTiquetes().isEmpty());
    }
    
    @Test
    void testAddTiquete() {
        localidad.addTiquete(tiquete);
        
        assertTrue(localidad.getTiquetes().contains(tiquete));
    }
    
    @Test
    void testPuedeVenderConTiquetesDisponibles() {
        localidad.addTiquete(tiquete);
        
        assertTrue(localidad.puedeVender());
    }
    
    @Test
    void testNoPuedeVenderSinTiquetes() {
        assertFalse(localidad.puedeVender());
    }
    
    @Test
    void testAsignarAsientoLocalidadNumerada() {
        localidad.setNumerada(true);
        
        int asiento = localidad.asignarAsientoDisponible();
        
        assertTrue(asiento > 0 && asiento <= 1000);
    }
    
    @Test
    void testAsignarAsientoLocalidadNoNumerada() {
        int asiento = localidad.asignarAsientoDisponible();
        
        assertEquals(-1, asiento);
    }
    
    @Test
    void testNoHayOfertaInicial() {
        assertFalse(localidad.hayOferta());
        assertNull(localidad.getOferta());
    }
    
    @Test
    void testAplicarOferta() {
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now().plusDays(7);
        Oferta oferta = new Oferta(1, 1, 0.20, inicio, fin);
        
        localidad.aplicarOferta(oferta);
        
        assertEquals(80000.0, localidad.getPrecioBase(), 0.01);
    }
    
    @Test
    void testBuscarTiquete() {
        localidad.addTiquete(tiquete);
        
        Tiquete encontrado = localidad.buscarTiquete(1);
        
        assertNotNull(encontrado);
        assertEquals(1, encontrado.getIdT());
    }
}
