package main.java.com.buscaminas.controlador;

import main.java.com.buscaminas.modelo.Tablero;
import main.java.com.buscaminas.controlador.JuegoControlador;
import main.java.com.buscaminas.vista.VistaConsola;
import main.java.com.buscaminas.excepciones.*;
import main.java.com.buscaminas.persistencia.GestorArchivos;
import java.util.Scanner;

public class JuegoControlador {
    private Tablero tablero;
    private VistaConsola vista;
    private Scanner scanner;
    private GestorArchivos gestor;

    public JuegoControlador() {
        this.vista = new VistaConsola();
        this.scanner = new Scanner(System.in);
        this.gestor = new GestorArchivos();
        this.tablero = new Tablero();
    }

    public void iniciar() {
        vista.mostrarMensaje("=== BUSCAMINAS ===");
        boolean salir = false;
        while (!salir && !tablero.isJuegoTerminado()) {
            vista.mostrarTablero(tablero, false);
            vista.mostrarAyuda();
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            switch (input.toUpperCase()) {
                case "S": salir = true; vista.mostrarMensaje("¡Hasta luego!"); break;
                case "G": guardarPartida(); break;
                case "C": cargarPartida(); break;
                default:
                    try { procesarComando(input); }
                    catch (CoordenadaInvalidaException | CasillaYaDescubiertaException e) {
                        vista.mostrarMensaje("Error: " + e.getMessage());
                    }
            }
        }
        if (tablero.isJuegoTerminado()) {
            vista.mostrarTablero(tablero, true);
            vista.mostrarMensaje(tablero.isVictoria() ? "¡VICTORIA!" : "¡BOOM! Has perdido.");
        }
    }

    public void procesarComando(String input) throws CoordenadaInvalidaException, CasillaYaDescubiertaException {
        boolean esMarcar = input.toUpperCase().startsWith("M");
        String coord = esMarcar ? input.substring(1) : input;
        if (coord.length() < 2) throw new CoordenadaInvalidaException("Formato inválido");
        char letra = Character.toUpperCase(coord.charAt(0));
        int num;
        try { num = Integer.parseInt(coord.substring(1)); }
        catch (NumberFormatException e) { throw new CoordenadaInvalidaException("Número inválido"); }
        int fila = num - 1;
        int columna = letra - 'A';
        if (fila < 0 || fila >= tablero.getTamano() || columna < 0 || columna >= tablero.getTamano())
            throw new CoordenadaInvalidaException("Coordenada fuera del tablero");
        if (esMarcar) tablero.marcarCasilla(fila, columna);
        else tablero.descubrirCasilla(fila, columna);
    }

    private void guardarPartida() {
        if (gestor.guardarPartida(tablero)) vista.mostrarMensaje("Partida guardada.");
        else vista.mostrarMensaje("Error al guardar.");
    }

    private void cargarPartida() {
        Tablero t = gestor.cargarPartida();
        if (t != null) { tablero = t; vista.mostrarMensaje("Partida cargada."); }
        else vista.mostrarMensaje("No se pudo cargar.");
    }

    public Tablero getTablero() {
        return tablero;
    }
}