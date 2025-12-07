package model;

public class Sala {

    // [ATRIBUTOS]: Privados para garantir encapsulamento
    private int salaId;
    private int numeroSala;
    private int capacidade;
    private String tipoSala;
    private boolean disponivel;

    // [MÉTODOS]: Getters e Setters para acesso controlado aos atributos
    public int getSalaId() { return salaId; }
    public void setSalaId(int salaId) { this.salaId = salaId; }

    public int getNumeroSala() { return numeroSala; }
    public void setNumeroSala(int numeroSala) { this.numeroSala = numeroSala; }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }

    public String getTipoSala() { return tipoSala; }
    public void setTipoSala(String tipoSala) { this.tipoSala = tipoSala; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}