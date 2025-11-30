package cinema;

public class Poltrona {

    private int id;
    private String numero;
    private boolean disponivel;

    public Poltrona(int id, String numero, boolean disponivel) {
        this.id = id;
        this.numero = numero;
        this.disponivel = disponivel;
    }

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

}