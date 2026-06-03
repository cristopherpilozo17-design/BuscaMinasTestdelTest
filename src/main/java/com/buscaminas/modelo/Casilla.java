package main.java.com.buscaminas.modelo;

public class Casilla {
    private boolean tieneMina;
    private boolean descubierta;
    private boolean marcada;
    private int minasAlrededor;

    public Casilla() {
        this.tieneMina = false;
        this.descubierta = false;
        this.marcada = false;
        this.minasAlrededor = 0;
    }

    public boolean tieneMina() { return tieneMina; }
    public void setTieneMina(boolean tieneMina) { this.tieneMina = tieneMina; }
    public boolean isDescubierta() { return descubierta; }
    public void setDescubierta(boolean descubierta) { this.descubierta = descubierta; }
    public boolean isMarcada() { return marcada; }
    public void setMarcada(boolean marcada) { this.marcada = marcada; }
    public int getMinasAlrededor() { return minasAlrededor; }
    public void setMinasAlrededor(int minasAlrededor) { this.minasAlrededor = minasAlrededor; }

    public void alternarMarcado() {
        if (!descubierta) {
            marcada = !marcada;
        }
    }
}