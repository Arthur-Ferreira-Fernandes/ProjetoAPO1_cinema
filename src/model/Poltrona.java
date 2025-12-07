package model;

// [BANCO DE DADOS]: Mapeia a tabela 'poltronas' do banco de dados.
public class Poltrona {
    
    // [BANCO DE DADOS]: Chave Primária (PK).
    private int id;
    private String numero; // Ex: A1, B5
    private boolean disponivel;
    
    // [BANCO DE DADOS / ASSOCIAÇÃO]: Foreign Key (FK) apontando para a tabela 'salas'.
    // Representa a relação: Uma Poltrona pertence a uma Sala.
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