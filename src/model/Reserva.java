package model;

// [MODELO / DTO]: Representação simplificada para visualização/transporte de dados.
public class Reserva {

    private int id;
    private String status;
    
    // [ASSOCIAÇÃO]: Referência ao ID de uma Poltrona (relacionamento lógico).
    private int poltronaId;

    // [SOBRECARGA]: Construtor único definido.
    public Reserva(int id, String status, int poltronaId) {
        this.id = id;
        this.status = status;
        this.poltronaId = poltronaId;
    }

    // Apenas Getters (Encapsulamento para leitura)
    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int getPoltronaId() {
        return poltronaId;
    }
}