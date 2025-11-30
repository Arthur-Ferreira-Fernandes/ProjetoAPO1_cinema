package cinema;

public class Reserva {

    private int id;
    private String status;
    private int poltronaId;

    public Reserva(int id, String status, int poltronaId) {
        this.id = id;
        this.status = status;
        this.poltronaId = poltronaId;
    }

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