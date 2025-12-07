package model;

import java.time.LocalDateTime;

// [BANCO DE DADOS]: Mapeia a tabela 'ingressos'.
public class Ingresso {
    // [BANCO DE DADOS]: Chave Primária.
    private int id;
    private String status;
    private LocalDateTime dataCompra;
    
    // [BANCO DE DADOS / ASSOCIAÇÃO]: Foreign Keys para Cliente, Sessão e Poltrona.
    // Relaciona os dados da venda.
    private int clienteId;
    private int sessaoId;
    private int poltronaId;

    // [SOBRECARGA]: Construtor vazio.
    public Ingresso() {}

    // [SOBRECARGA]: Construtor completo com parâmetros.
    public Ingresso(String status, LocalDateTime dataCompra, int clienteId, int sessaoId, int poltronaId) {
        this.status = status;
        this.dataCompra = dataCompra;
        this.clienteId = clienteId;
        this.sessaoId = sessaoId;
        this.poltronaId = poltronaId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getDataCompra() {
        return dataCompra;
    }
    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }
    public int getClienteId() {
        return clienteId;
    }
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    public int getSessaoId() {
        return sessaoId;
    }
    public void setSessaoId(int sessaoId) {
        this.sessaoId = sessaoId;
    }
    public int getPoltronaId() {
        return poltronaId;
    }
    public void setPoltronaId(int poltronaId) {
        this.poltronaId = poltronaId;
    }
}