package model;

import java.time.LocalDateTime;

public class Limpeza {
    private int id;
    private LocalDateTime dataLimpeza;
    private String status;
    private String observacao;
    
    private int salaId;

    // [SOBRECARGA]: Construtor padrão (sem argumentos).
    public Limpeza() {}

    // [SOBRECARGA]: Construtor com argumentos (assinatura diferente).
    public Limpeza(LocalDateTime dataLimpeza, String status, String observacao, int salaId) {
        this.dataLimpeza = dataLimpeza;
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

    public LocalDateTime getDataLimpeza() {
        return dataLimpeza;
    }

    public void setDataLimpeza(LocalDateTime dataLimpeza) {
        this.dataLimpeza = dataLimpeza;
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