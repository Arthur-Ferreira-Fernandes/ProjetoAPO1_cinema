package model;

import java.time.LocalDateTime;

public class Manutencao {
    // [BANCO DE DADOS]: Chave Primária (PK).
    private int id;
    private LocalDateTime dataManutencao;
    private String status;
    private String observacao;
    
    private int salaId;

    // [SOBRECARGA]: Construtor padrão.
    public Manutencao() {}

    // [SOBRECARGA]: Construtor com parâmetros.
    public Manutencao(LocalDateTime dataManutencao, String status, String observacao, int salaId) {
        this.dataManutencao = dataManutencao;
        this.status = status;
        this.observacao = observacao;
        this.salaId = salaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataManutencao() {
        return dataManutencao;
    }

    public void setDataManutencao(LocalDateTime dataManutencao) {
        this.dataManutencao = dataManutencao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getSalaId() {
        return salaId;
    }

    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }
}