package model;

public class Poltrona {
    
    private int id;
    private String numero; // Ex: A1, B5
    private boolean disponivel;
    
    private int salaId;

    // [SOBRECARGA]: Construtor padrão (vazio).
    public Poltrona() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public boolean isDisponivel() {
        return disponivel;
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    public int getSalaId() {
        return salaId;
    }
    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }
}