package main.java.com.buscaminas.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PartidaGuardada implements Serializable {
    private static final long serialVersionUID = 1L;

    private Tablero tablero;
    private LocalDateTime fechaGuardado;

    public PartidaGuardada(Tablero tablero, LocalDateTime fechaGuardado) {
        this.tablero = tablero;
        this.fechaGuardado = fechaGuardado;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public LocalDateTime getFechaGuardado() {
        return fechaGuardado;
    }
}
