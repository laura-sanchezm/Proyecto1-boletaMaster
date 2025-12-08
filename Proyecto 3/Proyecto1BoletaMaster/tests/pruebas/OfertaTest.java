package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Oferta;

public class OfertaTest {
    
    private Oferta oferta;
    
    @BeforeEach
    void setUp() {
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now().plusDays(7);
        oferta = new Oferta(1, 1, 0.20, inicio, fin);
    }
    
    @AfterEach
    void tearDown() {
        oferta = null;
    }
    
    @Test
    void testGetIdOferta() {
        assertEquals(1, oferta.getIdOferta());
    }
    
    @Test
    void testGetIdLocalidad() {
        assertEquals(1, oferta.getIdL());
    }
    
    @Test
    void testGetPorcentajeDescuento() {
        assertEquals(0.20, oferta.getPorcentajeDescuento(), 0.001);
    }
    
    @Test
    void testGetFechaInicio() {
        assertEquals(LocalDate.now(), oferta.getFechaInicio());
    }
    
    @Test
    void testGetFechaFin() {
        assertEquals(LocalDate.now().plusDays(7), oferta.getFechaFin());
    }
    
    @Test
    void testEstaVigente() {
        assertTrue(oferta.estaVigente());
    }
    
    @Test
    void testOfertaExpirada() {
        LocalDate inicio = LocalDate.now().minusDays(10);
        LocalDate fin = LocalDate.now().minusDays(3);
        Oferta expirada = new Oferta(2, 1, 0.15, inicio, fin);
        
        assertFalse(expirada.estaVigente());
    }
    
    @Test
    void testAplicarDescuentoVigente() {
        double precio = oferta.aplicarDescuento(100000.0);
        
        assertEquals(80000.0, precio, 0.01);
    }
    
    @Test
    void testAplicarDescuentoNoVigente() {
        LocalDate inicio = LocalDate.now().minusDays(10);
        LocalDate fin = LocalDate.now().minusDays(3);
        Oferta expirada = new Oferta(2, 1, 0.15, inicio, fin);
        
        double precio = expirada.aplicarDescuento(100000.0);
        
        assertEquals(100000.0, precio, 0.01);
    }
    
    @Test
    void testSetPorcentajeDescuento() {
        oferta.setPorcentajeDescuento(0.30);
        assertEquals(0.30, oferta.getPorcentajeDescuento(), 0.001);
    }
}
