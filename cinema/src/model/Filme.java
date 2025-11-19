package model;

public class Filme {

    private int id;
    private String nome;

    public Filme(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getIdFilme() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome; // Combo exibirá o nome
    }
}
