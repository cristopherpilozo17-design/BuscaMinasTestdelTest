package main.java.com.buscaminas.vista;

import main.java.com.buscaminas.modelo.Casilla;
import main.java.com.buscaminas.modelo.Tablero;

public class VistaConsola {

    // Colores ANSI
    private static final String RESET = "\u001B[0m";
    private static final String ROJO = "\u001B[31m";
    private static final String VERDE = "\u001B[32m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String AZUL = "\u001B[34m";

    public void mostrarTablero(Tablero tablero, boolean mostrarMinas) {

        int tamano = tablero.getTamano();

        System.out.println("\n========== BUSCAMINAS ==========\n");

        System.out.print("    ");

        for (int i = 0; i < tamano; i++) {
            System.out.printf(" %c ", (char) ('A' + i));
        }

        System.out.println();

        for (int fila = 0; fila < tamano; fila++) {

            System.out.printf("%2d |", fila + 1);

            for (int columna = 0; columna < tamano; columna++) {

                Casilla casilla = tablero.getCasilla(fila, columna);
                System.out.print(obtenerSimbolo(casilla, mostrarMinas));
            }

            System.out.println("|");
        }

        System.out.println();
    }

    private String obtenerSimbolo(Casilla casilla, boolean mostrarMinas) {

        if (mostrarMinas && casilla.tieneMina()) {
            return ROJO + " * " + RESET;
        }

        if (!casilla.isDescubierta()) {

            if (casilla.isMarcada()) {
                return AMARILLO + " ? " + RESET;
            }

            return AZUL + " ■ " + RESET;
        }

        if (casilla.tieneMina()) {
            return ROJO + " X " + RESET;
        }

        int minas = casilla.getMinasAlrededor();

        if (minas == 0) {
            return "   ";
        }

        return VERDE + " " + minas + " " + RESET;
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println("\n" + mensaje + "\n");
    }

    public void mostrarAyuda() {

        System.out.println("\n========== AYUDA ==========");
        System.out.println("A5   -> Descubrir casilla");
        System.out.println("MA5  -> Marcar/Desmarcar");
        System.out.println("G    -> Guardar partida");
        System.out.println("C    -> Cargar partida");
        System.out.println("S    -> Salir");
        System.out.println("===========================\n");
    }
}