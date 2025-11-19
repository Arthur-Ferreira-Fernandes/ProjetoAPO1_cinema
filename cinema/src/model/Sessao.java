package model;

import java.time.LocalDateTime;

public class Sessao {
    private int id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private int filmeId;
    private int salaId;

    public Sessao(int id, LocalDateTime inicio, LocalDateTime fim, int filmeId, int salaId) {
        this.id = id;
        this.inicio = inicio;
        this.fim = fim;
        this.filmeId = filmeId;
        this.salaId = salaId;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public int getFilmeId() {
        return filmeId;
    }

    public int getSalaId() {
        return salaId;
    }
}
