package model;

// [BANCO DE DADOS]: Representa a tabela 'filmes' no banco de dados.
public class Filme {
    
    // [BANCO DE DADOS]: Chave Primária (PK).
    private int id;
    
    // [BANCO DE DADOS]: Mapeia a coluna 'Titulo' da tabela.
    private String nome; // No banco é 'Titulo'
    
    // [BANCO DE DADOS]: Mapeia a coluna 'duracao' (geralmente em minutos).
    private int duracao;
    
    private String genero;
    private String classificacao;
    private String sinopse;

    // [SOBRECARGA]: Construtor padrão (sem argumentos).
    // Necessário para frameworks de persistência ou instanciação vazia.
    public Filme() {}

    // [SOBRECARGA]: Construtor com argumentos.
    // Permite criar o objeto já com os dados principais preenchidos.
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
    // Isso é fundamental para que, ao adicionar o objeto Filme em um JComboBox (na View),
    // apareça apenas o nome do filme e não o endereço de memória do objeto.
    @Override
    public String toString() {
        return this.nome; 
    }
}