package model;

import java.time.LocalDateTime;

public class Ingresso {
    private int id;
    private String status;
    private LocalDateTime dataCompra;
    
    // [ASSOCIAÇÃO]: A classe não compõe objetos inteiros (como private Cliente cliente),
    // mas sim associa-se através dos IDs (Chaves Estrangeiras), mantendo o acoplamento baixo
    // e facilitando a persistência no banco SQL.
    private int clienteId;   // Associação com Cliente
    private int sessaoId;    // Associação com Sessao
    private int poltronaId;  // Associação com Poltrona

    public Ingresso(String status, LocalDateTime dataCompra, int clienteId, int sessaoId, int poltronaId) {
        this.status = status;
        this.dataCompra = dataCompra;
        this.clienteId = clienteId;
        this.sessaoId = sessaoId;
        this.poltronaId = poltronaId;
    }

    // Getters (Acesso aos dados encapsulados)
    public String getStatus() { return status; }
    public LocalDateTime getDataCompra() { return dataCompra; }
    public int getClienteId() { return clienteId; }
    public int getSessaoId() { return sessaoId; }
    public int getPoltronaId() { return poltronaId; }
}