package model;

// [BANCO DE DADOS]: Mapeia a tabela 'clientes'.
public class Cliente {
    // [BANCO DE DADOS]: Chave Primária (PK).
    private int id;
    private String nome;
    private String email;
    private String telefone;

    // [SOBRECARGA]: Construtor vazio.
    public Cliente() {}

    // [SOBRECARGA]: Construtor utilitário (assinatura com argumentos) para criação rápida no Controller.
    public Cliente(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}