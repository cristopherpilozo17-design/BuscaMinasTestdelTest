package test.java.com.buscaminas.modelo;

import main.java.com.buscaminas.excepciones.CasillaYaDescubiertaException;
import main.java.com.buscaminas.modelo.Tablero;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TableroTest {
    private Tablero tablero;

    @BeforeEach
    void setUp() {
        tablero = new Tablero();
    }

    @Test
    void testInicializacionTamano() {
        assertEquals(10, tablero.getTamano());
        assertFalse(tablero.isJuegoTerminado());
        assertFalse(tablero.isVictoria());
    }

    @Test
    void testCantidadMinas() {
        int contador = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getCasilla(i, j).tieneMina()) contador++;
            }
        }
        assertEquals(10, contador, "Debe haber exactamente 10 minas");
    }

    @Test
    void testDescubrirCasillaSinMina() throws CasillaYaDescubiertaException {
        // Buscar una casilla sin mina
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!tablero.getCasilla(i, j).tieneMina()) {
                    tablero.descubrirCasilla(i, j);
                    assertTrue(tablero.getCasilla(i, j).isDescubierta());
                    return;
                }
            }
        }
        fail("No se encontró casilla sin mina, imposible");
    }

    @Test
    void testDescubrirCasillaYaDescubiertaLanzaExcepcion() throws CasillaYaDescubiertaException {
        // Primero descubrimos una casilla sin mina
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!tablero.getCasilla(i, j).tieneMina()) {
                    final int finalI = i;
                    final int finalJ = j;
                    tablero.descubrirCasilla(finalI, finalJ);
                    assertThrows(CasillaYaDescubiertaException.class, () -> tablero.descubrirCasilla(finalI, finalJ));
                    return;
                }
            }
        }
    }

    @Test
    void testMarcarYDesmarcarCasilla() {
        tablero.marcarCasilla(0, 0);
        assertTrue(tablero.getCasilla(0, 0).isMarcada());
        tablero.marcarCasilla(0, 0);
        assertFalse(tablero.getCasilla(0, 0).isMarcada());
    }

    @Test
    void testNoSePuedeDescubrirCasillaMarcada() throws CasillaYaDescubiertaException {
        tablero.marcarCasilla(0, 0);
        tablero.descubrirCasilla(0, 0);
        assertFalse(tablero.getCasilla(0, 0).isDescubierta());
        assertTrue(tablero.getCasilla(0, 0).isMarcada()); // sigue marcada
    }

    @Test
    void testDescubrirMinaTerminaJuego() throws CasillaYaDescubiertaException {
        // Localizar una mina
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getCasilla(i, j).tieneMina()) {
                    tablero.descubrirCasilla(i, j);
                    assertTrue(tablero.isJuegoTerminado());
                    assertFalse(tablero.isVictoria());
                    return;
                }
            }
        }
    }

    @Test
    void testReveladoAutomaticoDeCeros() throws CasillaYaDescubiertaException {
        // Buscar casilla con cero minas alrededor (es probable que exista)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!tablero.getCasilla(i, j).tieneMina() && tablero.getCasilla(i, j).getMinasAlrededor() == 0) {
                    tablero.descubrirCasilla(i, j);
                    // Verificar que al menos una vecina también se descubrió
                    boolean algunaVecinaDescubierta = false;
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            if (di == 0 && dj == 0) continue;
                            int ni = i + di, nj = j + dj;
                            if (ni >= 0 && ni < 10 && nj >= 0 && nj < 10) {
                                if (tablero.getCasilla(ni, nj).isDescubierta()) {
                                    algunaVecinaDescubierta = true;
                                    break;
                                }
                            }
                        }
                    }
                    assertTrue(algunaVecinaDescubierta, "Debería haber revelado casillas adyacentes");
                    return;
                }
            }
        }
        // Si no hay ceros, la prueba se omite
    }
}

