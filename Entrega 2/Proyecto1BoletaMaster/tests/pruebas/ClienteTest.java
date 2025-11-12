package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import modelo.*;
import Tiquetes.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClienteTest {
    
    private Cliente cliente;
    private Localidad localidad;
    private Evento evento;
    private Venue venue;
    private Organizador organizador;
    private TiqueteSimple tiquete;
    
    @BeforeEach
    void setUp() {
        // Constructor correcto: solo login y password
        cliente = new Cliente("cliente1", "pass123");
        
        // Crear evento necesario para TiqueteSimple
        venue = new Venue("Test Arena", 1000, "Arena", "Bogotá", "Ninguna");
        organizador = new Organizador("org1", "pass", 1);
        evento = new Evento(1, "Test Evento", LocalDate.now(), LocalTime.of(20, 0),
                           "Concierto", estadoEvento.PROGRAMADO, venue, organizador);
        
        localidad = new Localidad(1, "VIP", 100000.0, 1000);
        evento.addLocalidad(localidad);
        
        // Constructor correcto: 7 parámetros (id, transferible, propietario, status, tipo, evento, localidad)
        tiquete = new TiqueteSimple(1, true, null, estadoTiquete.DISPONIBLE, "SIMPLE", evento, localidad);
    }
    
    @AfterEach
    void tearDown() {
        cliente = null;
        localidad = null;
        tiquete = null;
        evento = null;
        venue = null;
        organizador = null;
    }
    
    @Test
    void testGetLogin() {
        assertEquals("cliente1", cliente.getLogin());
    }
    
    @Test
    void testConsultarSaldoInicial() {
        assertEquals(0.0, cliente.consultarSaldo(), 0.01);
    }
    
    @Test
    void testRecargarSaldo() {
        cliente.recargarSaldo(50000.0);
        assertEquals(50000.0, cliente.consultarSaldo(), 0.01);
    }
    
    @Test
    void testGetTiquetes() {
        assertNotNull(cliente.getTiquetes());
        assertTrue(cliente.getTiquetes().isEmpty());
    }
    
    @Test
    void testAddTiquete() {
        cliente.addTiquete(tiquete);
        
        assertEquals(1, cliente.getTiquetes().size());
        assertTrue(cliente.getTiquetes().contains(tiquete));
    }
    
    @Test
    void testAddMultiplesTiquetes() {
        TiqueteSimple t2 = new TiqueteSimple(2, true, null, estadoTiquete.DISPONIBLE, "SIMPLE", evento, localidad);
        TiqueteSimple t3 = new TiqueteSimple(3, true, null, estadoTiquete.DISPONIBLE, "SIMPLE", evento, localidad);
        
        cliente.addTiquete(tiquete);
        cliente.addTiquete(t2);
        cliente.addTiquete(t3);
        
        assertEquals(3, cliente.getTiquetes().size());
    }
    
    @Test
    void testBuscarTiquetePorId() {
        cliente.addTiquete(tiquete);
        
        Tiquete encontrado = cliente.buscarTiquetePorId(1);
        
        assertNotNull(encontrado);
        assertEquals(1, encontrado.getIdT());
    }
    
    @Test
    void testBuscarTiqueteInexistente() {
        Tiquete encontrado = cliente.buscarTiquetePorId(999);
        
        assertNull(encontrado);
    }
}

