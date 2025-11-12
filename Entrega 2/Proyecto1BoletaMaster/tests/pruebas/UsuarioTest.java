package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.*;

public class UsuarioTest {
	
private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        usuario = new Usuario("user123", "password456");
    }
    
    @AfterEach
    void tearDown() {
        usuario = null;
    }
    
    @Test
    void testGetLogin() {
        assertEquals("user123", usuario.getLogin(), 
            "El login no es el esperado");
    }
    
    @Test
    void testGetPassword() {
        assertEquals("password456", usuario.getPassword(), 
            "El password no es el esperado");
    }
    
    @Test
    void testSetLogin() {
        usuario.setLogin("nuevoLogin");
        assertEquals("nuevoLogin", usuario.getLogin(), 
            "El login no se actualizó correctamente");
    }
    
    @Test
    void testSetPassword() {
        usuario.setPassword("nuevaPass");
        assertEquals("nuevaPass", usuario.getPassword(), 
            "El password no se actualizó correctamente");
    }
    
    @Test
    void testCrearUsuarioConParametrosNulos() {
        assertDoesNotThrow(() -> new Usuario(null, null),
            "Debe permitir crear usuario con parámetros nulos");
    }

}
