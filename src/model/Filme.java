package model;

public class Filme {
    
    private int id;
    
    private String nome;
    
    private int duracao;
    
    private String genero;
    private String classificacao;
    private String sinopse;

    // [SOBRECARGA]
    public Filme() {}

    // [SOBRECARGA]
    public Filme(String nome, int duracao, String genero, String classificacao) {
        this.nome = nome;
        this.duracao = duracao;
        this.genero = genero;
        this.classificacao = classificacao;
    }

    // --- Getters e Setters (Encapsulamento) ---

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getDuracao() {
        return duracao;
    }
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getClassificacao() {
        return classificacao;
    }
    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }
    public String getSinopse() {
        return sinopse;
    }
    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    // [SOBRESCRITA]: Reescreve o método toString() da classe Object.
    @Override
    public String toString() {
        return this.nome; 
    }
}