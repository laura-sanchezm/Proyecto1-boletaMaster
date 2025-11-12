package pruebas;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import modelo.*;
import pagos.*;
import Tiquetes.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EstadosFinancierosTest {
    
    private EstadosFinancieros estados;
    private Pago pago;
    private Localidad localidad;
    private Evento evento;
    private Venue venue;
    private Organizador organizador;
    
    @BeforeEach
    void setUp() {
        estados = new EstadosFinancieros();
        
        venue = new Venue("Test Arena", 1000, "Arena", "Bogotá", "Ninguna");
        organizador = new Organizador("org1", "pass", 1);
        evento = new Evento(1, "Test Evento", LocalDate.now(), LocalTime.of(20, 0),
                           "Concierto", estadoEvento.PROGRAMADO, venue, organizador);
        
        localidad = new Localidad(1, "VIP", 100000.0, 1000);
        evento.addLocalidad(localidad);
        
        TiqueteSimple tiquete = new TiqueteSimple(1, true, "cliente1", 
                                                   estadoTiquete.DISPONIBLE, "SIMPLE", evento, localidad);
        ArrayList<Tiquete> tiquetes = new ArrayList<>();
        tiquetes.add(tiquete);
        
        pago = new Pago(1, LocalDate.now(), metodoPago.PASARELA_EXTERNA, 
                       0.10, 5000.0, tiquetes);
    }
    
    @AfterEach
    void tearDown() {
        estados = null;
        pago = null;
        localidad = null;
        evento = null;
        venue = null;
        organizador = null;
    }
    
    @Test
    void testGetTransaccionesInicial() {
        assertNotNull(estados.getTransacciones());
        assertTrue(estados.getTransacciones().isEmpty());
    }
    
    @Test
    void testRegistrarTransaccion() {
        estados.registrarTransaccion(pago);
        
        assertEquals(1, estados.getTransacciones().size());
        assertTrue(estados.getTransacciones().contains(pago));
    }
    
    @Test
    void testRegistrarTransaccionNula() {
        estados.registrarTransaccion(null);
        
        assertTrue(estados.getTransacciones().isEmpty());
    }
    
    @Test
    void testTotalGananciasPorFecha() {
        pago.procesar();
        estados.registrarTransaccion(pago);
        
        LocalDate hoy = LocalDate.now();
        double ganancias = estados.totalGananciasPorFecha(hoy, hoy);
        
        assertTrue(ganancias > 0);
    }
}
