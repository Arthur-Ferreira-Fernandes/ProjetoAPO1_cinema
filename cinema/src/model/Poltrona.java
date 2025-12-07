package model;

public class Poltrona {

    // [ATRIBUTOS]: Encapsulamento (private) para proteger o estado do objeto.
    private String numero;
    private boolean ocupada;

    // [MÉTODO COM SOBRECARGA]: Construtor 1 - Recebe apenas o número (assume livre).
    public Poltrona(String numero) {
        this.numero = numero;
        this.ocupada = false; // Valor padrão
    }

    // [MÉTODO COM SOBRECARGA]: Construtor 2 - Recebe número e status explícito.
    public Poltrona(String numero, boolean ocupada) {
        this.numero = numero;
        this.ocupada = ocupada;
    }

    // Getters e Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    // Método utilitário para facilitar leitura de regra de negócio
    public boolean isDisponivel() {
        return !ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    // [SOBRESCRITA / OVERRIDE]: Reescreve o método da classe Object.
    // Essencial para que ComboBoxes e Lists mostrem o texto legível e não o hash da memória.
    @Override
    public String toString() {
        return "Poltrona{" +
                "numero='" + numero + '\'' +
                ", ocupada=" + ocupada +
                '}';
    }
}