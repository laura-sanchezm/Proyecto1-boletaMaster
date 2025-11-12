package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class VenueTest {
	
	private Venue venue;
    private LocalDate fecha1;
    private LocalDate fecha2;
    
    @BeforeEach
    void setUp() {
        venue = new Venue("Movistar Arena", 14000, "Arena", "Bogotá", "No mascotas");
        fecha1 = LocalDate.of(2025, 12, 15);
        fecha2 = LocalDate.of(2025, 12, 20);
    }
    
    // revisa que venue inicie sin necesidad de ser aprobado
    @Test
    void testVenueIniciaNoAprobado() {
        assertFalse(venue.getAprobado(), "Venue debería iniciar sin aprobación");
    }
    
    // Venue puede ser aprobado
    @Test
    void testAprobarVenue() {
        venue.setAprobado(true);
        assertTrue(venue.getAprobado(), "Venue debería estar aprobado");
    }
    
    // revisa que venue este disponible cundo no haya fechas ocupadas
    @Test
    void testDisponibilidadFechaLibre() {
        assertTrue(venue.consultarDisponibilidad(fecha1), 
            "Venue debería estar disponible en fecha sin ocupar");
    }
    
    // revisa que venue no slaga disponible al tener fechas ocupadas
    @Test
    void testDisponibilidadFechaOcupada() {
        venue.addFechaOcupada(fecha1);
        assertFalse(venue.consultarDisponibilidad(fecha1), 
            "Venue no debería estar disponible en fecha ocupada");
    }
    
    //venue puede tener varias fechas distintas de ocupacion
    @Test
    void testMultiplesFechasOcupadas() {
        venue.addFechaOcupada(fecha1);
        venue.addFechaOcupada(fecha2);
        
        assertAll("Verificar múltiples fechas",
            () -> assertFalse(venue.consultarDisponibilidad(fecha1)),
            () -> assertFalse(venue.consultarDisponibilidad(fecha2))
        );
    }
    
    
    // revisa que la capacidad del venue se mantenga y sea la indicada
    @Test
    void testCapacidadVenue() {
        assertEquals(14000, venue.getCapacidad(), 
            "La capacidad del venue debe ser 14000");
    }

}
