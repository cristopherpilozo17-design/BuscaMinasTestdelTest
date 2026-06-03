package main.java.com.buscaminas.persistencia;

import main.java.com.buscaminas.modelo.Tablero;
import java.io.*;

public class GestorArchivos {
    private static final String ARCHIVO = "partida.ser";

    public boolean guardarPartida(Tablero tablero) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(tablero);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Tablero cargarPartida() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (Tablero) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
