package controller;

import java.util.List;
import banco.FilmeDAO;
import model.Filme;

public class FilmeController {

    // [COMPOSIÇÃO]: O Controller gerencia o ciclo de vida do FilmeDAO.
    private FilmeDAO filmeDAO;

    public FilmeController() {
        this.filmeDAO = new FilmeDAO();
    }

    // [AGREGAÇÃO - List]: Retorna uma lista de objetos Filme para a View.
    public List<Filme> listarTodos() {
        return filmeDAO.listarTodos();
    }
}