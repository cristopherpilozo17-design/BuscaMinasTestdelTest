package main.java.com.buscaminas.vista;

import main.java.com.buscaminas.modelo.Tablero;
import main.java.com.buscaminas.modelo.Casilla;

public class VistaConsola {
    public void mostrarTablero(Tablero tablero, boolean mostrarMinas) {
        System.out.print("   ");
        for (int i = 0; i < tablero.getTamano(); i++) {
            System.out.printf("%2s ", (char)('A' + i));
        }
        System.out.println();
        for (int i = 0; i < tablero.getTamano(); i++) {
            System.out.printf("%2d ", i+1);
            for (int j = 0; j < tablero.getTamano(); j++) {
                Casilla c = tablero.getCasilla(i, j);
                if (mostrarMinas && c.tieneMina()) {
                    System.out.print(" * ");
                } else if (!c.isDescubierta()) {
                    if (c.isMarcada()) {
                        System.out.print(" ? ");
                    } else {
                        System.out.print(" - ");
                    }
                } else {
                    if (c.tieneMina()) {
                        System.out.print(" X ");
                    } else {
                        int num = c.getMinasAlrededor();
                        if (num == 0) System.out.print("   ");
                        else System.out.printf(" %d ", num);
                    }
                }
            }
            System.out.println();
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarAyuda() {
        System.out.println("Comandos: <letra><numero> para descubrir, ej: A5");
        System.out.println("          M<letra><numero> para marcar/desmarcar, ej: MA5");
        System.out.println("          G guardar partida, C cargar, S salir");
    }
}