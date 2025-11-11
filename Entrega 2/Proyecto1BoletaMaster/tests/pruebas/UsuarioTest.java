package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.*;

public class UsuarioTest {
	
	private Cliente cliente;
    private Organizador organizador;
    private Administrador administrador;
    
    @BeforeEach
    void setUp() throws Exception {
        cliente = new Cliente("cliente1", "pass123");
        organizador = new Organizador("org1", "pass456", 1);
        administrador = new Administrador("admin1", "pass789");
    }
    
    @AfterEach
    void tearDown() throws Exception {
        cliente = null;
        organizador = null;
        administrador = null;
    }
    
    @Test
    void testCrearCliente() {
        assertEquals("cliente1", cliente.getLogin(), 
                "El login del cliente no es el esperado");
        assertEquals("pass123", cliente.getPassword(), 
                "La contraseña del cliente no es la esperada");
        assertEquals(0, cliente.consultarSaldo(), 0.01, 
                "El saldo inicial debe ser 0");
    }
    
    @Test
    void testCrearOrganizador() {
        assertEquals("org1", organizador.getLogin(), 
                "El login del organizador no es el esperado");
        assertEquals(1, organizador.getIdO(), 
                "El ID del organizador no es el esperado");
        assertTrue(organizador.getEventos().isEmpty(), 
                "El organizador no debe tener eventos inicialmente");
    }
    
    @Test
    void testCrearAdministrador() {
        assertEquals("admin1", administrador.getLogin(), 
                "El login del administrador no es el esperado");
        assertNotNull(administrador.getEventos(), 
                "La lista de eventos no debe ser nula");
        assertNotNull(administrador.getVenues(), 
                "La lista de venues no debe ser nula");
    }
    
    @Test
    void testRecargarSaldo() {
        cliente.recargarSaldo(100.0);
        assertEquals(100.0, cliente.consultarSaldo(), 0.01, 
                "El saldo después de recargar no es correcto");
        
        cliente.recargarSaldo(50.0);
        assertEquals(150.0, cliente.consultarSaldo(), 0.01, 
                "El saldo después de la segunda recarga no es correcto");
    }
    
    @Test
    void testAutenticacion() {
        assertEquals("pass123", cliente.getPassword(), 
                "La contraseña debe coincidir");
        assertNotEquals("password_incorrecta", cliente.getPassword(), 
                "La contraseña incorrecta no debe coincidir");
    }
    
    @Test
    void testClienteSinTiquetes() {
        assertTrue(cliente.getTiquetes().isEmpty(), 
                "El cliente nuevo no debe tener tiquetes");
    }

}
