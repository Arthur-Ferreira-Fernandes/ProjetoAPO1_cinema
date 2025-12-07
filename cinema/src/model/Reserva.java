package model;

// [MODELO]: Esta classe funciona como uma representação simplificada de 'Ingresso'
// usada especificamente para operações de busca e cancelamento.
public class Reserva {

    private int id;
    private String status;
    
    // [ASSOCIAÇÃO]: Conexão com a Poltrona para liberação posterior
    private int poltronaId;

    // [MÉTODO CONSTRUTOR]
    public Reserva(int id, String status, int poltronaId) {
        this.id = id;
        this.status = status;
        this.poltronaId = poltronaId;
    }

    // Apenas Getters (Objeto Imutável neste contexto)
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