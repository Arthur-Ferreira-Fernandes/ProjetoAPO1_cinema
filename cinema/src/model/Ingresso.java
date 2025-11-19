package model;

import java.time.LocalDateTime;

public class Ingresso {
    private int id;
    private String status;
    private LocalDateTime dataCompra;
    private int clienteId;
    private int sessaoId;
    private int poltronaId;

    public Ingresso(String status, LocalDateTime dataCompra, int clienteId, int sessaoId, int poltronaId) {
        this.status = status;
        this.dataCompra = dataCompra;
        this.clienteId = clienteId;
        this.sessaoId = sessaoId;
        this.poltronaId = poltronaId;
    }

    // getters
    public String getStatus() { return status; }
    public LocalDateTime getDataCompra() { return dataCompra; }
    public int getClienteId() { return clienteId; }
    public int getSessaoId() { return sessaoId; }
    public int getPoltronaId() { return poltronaId; }
}
