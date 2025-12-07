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

    // [SOBRESCRITA / OVERRIDE]: Permite que o JComboBox exiba apenas o nome do filme
    // ao invés de 'model.Filme@1a2b3c'.
    @Override
    public String toString() {
        return nome; 
    }
}