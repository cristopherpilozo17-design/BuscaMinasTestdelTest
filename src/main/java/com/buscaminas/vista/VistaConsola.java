package main.java.com.buscaminas.vista;

import main.java.com.buscaminas.modelo.Casilla;
import main.java.com.buscaminas.modelo.Tablero;

public class VistaConsola {

    public void mostrarTablero(Tablero tablero, boolean mostrarMinas) {

        imprimirEncabezado(tablero.getTamano());

        for (int fila = 0; fila < tablero.getTamano(); fila++) {

            System.out.printf("%2d ", fila + 1);

            for (int columna = 0; columna < tablero.getTamano(); columna++) {

                Casilla casilla = tablero.getCasilla(fila, columna);
                System.out.print(obtenerSimbolo(casilla, mostrarMinas));
            }

            System.out.println();
        }
    }

    private void imprimirEncabezado(int tamano) {

        System.out.print("   ");

        for (int i = 0; i < tamano; i++) {
            System.out.printf("%2c ", (char) ('A' + i));
        }

        System.out.println();
    }

    private String obtenerSimbolo(Casilla casilla, boolean mostrarMinas) {

        if (mostrarMinas && casilla.tieneMina()) {
            return " * ";
        }

        if (!casilla.isDescubierta()) {
            return casilla.isMarcada() ? " ? " : " - ";
        }

        if (casilla.tieneMina()) {
            return " X ";
        }

        int minas = casilla.getMinasAlrededor();

        return minas == 0 ? "   " : String.format(" %d ", minas);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarAyuda() {
        System.out.println("\n=== AYUDA ===");
        System.out.println("A5   -> Descubrir casilla");
        System.out.println("MA5  -> Marcar casilla");
        System.out.println("G    -> Guardar partida");
        System.out.println("C    -> Cargar partida");
        System.out.println("S    -> Salir");
        System.out.println("==============\n");
    }
}