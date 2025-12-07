package model;

import java.util.Date;

public class Limpeza {

    private int id;
    private Date data;
    private String status;
    private String observacao;
    
    // [ASSOCIAÇÃO]: Referência à Sala via chave estrangeira (salaId)
    private int salaId;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public int getSalaId() { return salaId; }
    public void setSalaId(int salaId) { this.salaId = salaId; }
}