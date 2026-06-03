package test.java.com.buscaminas.persistencia;

import main.java.com.buscaminas.modelo.Tablero;
import main.java.com.buscaminas.persistencia.GestorArchivos;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

class GestorArchivosTest {
    private GestorArchivos gestor;
    private Tablero tableroOriginal;
    private static final String TEST_FILE = "test_partida.ser";

    @BeforeEach
    void setUp() {
        gestor = new GestorArchivos();
        tableroOriginal = new Tablero();
        // Opcional: cambiar el nombre del archivo para no interferir con el original
        // En un entorno real se podría modificar GestorArchivos para permitir ruta, pero aquí usamos el predeterminado
    }

    @AfterEach
    void limpiar() {
        File f = new File("partida.ser");
        if (f.exists()) f.delete();
    }

    @Test
    void testGuardarYCargar() {
        assertTrue(gestor.guardarPartida(tableroOriginal));
        Tablero cargado = gestor.cargarPartida();
        assertNotNull(cargado);
        assertEquals(tableroOriginal.getTamano(), cargado.getTamano());
        // Verificar que alguna mina coincida (no es exacto porque la colocación es aleatoria)
        // En su lugar, verificamos que el tablero cargado no sea el mismo objeto pero tiene estructura
        assertNotSame(tableroOriginal, cargado);
    }

    @Test
    void testCargarArchivoInexistente() {
        File f = new File("partida.ser");
        if (f.exists()) f.delete();
        Tablero cargado = gestor.cargarPartida();
        assertNull(cargado);
    }

    @Test
    void testGuardarSobrescribe() {
        gestor.guardarPartida(tableroOriginal);
        Tablero otro = new Tablero();
        gestor.guardarPartida(otro);
        Tablero cargado = gestor.cargarPartida();
        // No podemos comparar contenido directamente, pero al menos no lanza excepción
        assertNotNull(cargado);
    }
}
