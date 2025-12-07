package model;

import java.time.LocalDateTime;

public class Sessao {
    private int id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    
    private int filmeId;
    
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