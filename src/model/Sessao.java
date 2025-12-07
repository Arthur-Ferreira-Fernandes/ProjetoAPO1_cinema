package model;

import java.time.LocalDateTime;

// [BANCO DE DADOS]: Mapeia a tabela 'sessoes'.
public class Sessao {
    // [BANCO DE DADOS]: Chave Primária (PK).
    private int id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    
    // [BANCO DE DADOS / ASSOCIAÇÃO]: Foreign Key (FK) para tabela 'filmes'.
    // Relacionamento: Uma Sessão exibe um Filme.
    private int filmeId;
    
    // [BANCO DE DADOS / ASSOCIAÇÃO]: Foreign Key (FK) para tabela 'salas'.
    // Relacionamento: Uma Sessão ocorre em uma Sala.
    private int salaId;

    // [SOBRECARGA]: Construtor padrão.
    public Sessao() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDateTime getInicio() {
        return inicio;
    }
    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }
    public LocalDateTime getFim() {
        return fim;
    }
    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }
    public int getFilmeId() {
        return filmeId;
    }
    public void setFilmeId(int filmeId) {
        this.filmeId = filmeId;
    }
    public int getSalaId() {
        return salaId;
    }
    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }
}