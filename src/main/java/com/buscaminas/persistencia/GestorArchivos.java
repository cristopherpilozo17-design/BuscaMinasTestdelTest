package main.java.com.buscaminas.persistencia;

import main.java.com.buscaminas.modelo.Tablero;
import main.java.com.buscaminas.modelo.PartidaGuardada;
import java.io.*;
import java.time.LocalDateTime;

public class GestorArchivos {
    private static final String ARCHIVO = "partida.ser";

    public boolean guardarPartida(Tablero tablero) {
        return guardarPartida(tablero, 1);
    }

    public Tablero cargarPartida() {
        return cargarPartida(1);
    }

    public boolean guardarPartida(Tablero tablero, int slot) {
        String nombreArchivo = (slot == 1) ? ARCHIVO : "partida_" + slot + ".ser";
        PartidaGuardada pg = new PartidaGuardada(tablero, LocalDateTime.now());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(pg);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Tablero cargarPartida(int slot) {
        PartidaGuardada pg = cargarDatosPartida(slot);
        return pg != null ? pg.getTablero() : null;
    }

    public PartidaGuardada cargarDatosPartida(int slot) {
        String nombreArchivo = (slot == 1) ? ARCHIVO : "partida_" + slot + ".ser";
        File f = new File(nombreArchivo);
        if (!f.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            Object obj = ois.readObject();
            if (obj instanceof Tablero) {
                // Legacy compatibility: raw Tablero was saved
                return new PartidaGuardada((Tablero) obj, LocalDateTime.now());
            } else if (obj instanceof PartidaGuardada) {
                return (PartidaGuardada) obj;
            }
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existePartida(int slot) {
        String nombreArchivo = (slot == 1) ? ARCHIVO : "partida_" + slot + ".ser";
        return new File(nombreArchivo).exists();
    }

    /**
     * Guarda automáticamente en el primer slot vacío (1→2→3).
     * Si todos están llenos, rota: Slot3 ← Slot2 ← Slot1 ← nueva.
     * Devuelve el número de slot donde se guardó, o -1 si hubo error.
     */
    public int guardarPartidaAuto(Tablero tablero) {
        // Buscar primer slot vacío
        for (int slot = 1; slot <= 3; slot++) {
            if (!existePartida(slot)) {
                return guardarPartida(tablero, slot) ? slot : -1;
            }
        }
        // Todos llenos: rotar Slot3 ← Slot2 ← Slot1 ← nueva
        PartidaGuardada pg1 = cargarDatosPartida(1);
        PartidaGuardada pg2 = cargarDatosPartida(2);
        if (pg2 != null) guardarPartidaGuardada(pg2, 3);
        if (pg1 != null) guardarPartidaGuardada(pg1, 2);
        return guardarPartida(tablero, 1) ? 1 : -1;
    }

    private boolean guardarPartidaGuardada(PartidaGuardada pg, int slot) {
        String nombreArchivo = (slot == 1) ? ARCHIVO : "partida_" + slot + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(pg);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
