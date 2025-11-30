package model;

public class Poltrona {

    private String numero;
    private boolean ocupada;

    public Poltrona(String numero) {
        this.numero = numero;
        this.ocupada = false; // padrão
    }

    public Poltrona(String numero, boolean ocupada) {
        this.numero = numero;
        this.ocupada = ocupada;
    }

    // Getter e Setter
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isDisponivel() {
        return !ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    @Override
    public String toString() {
        return "Poltrona{" +
                "numero='" + numero + '\'' +
                ", ocupada=" + ocupada +
                '}';
    }
}
