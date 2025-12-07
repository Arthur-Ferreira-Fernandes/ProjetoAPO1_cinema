package controller;

import java.util.List;
import banco.SessaoDAO;
import model.Sessao;

public class SessaoController {

    // [COMPOSIÇÃO]: Instanciação direta do DAO no construtor.
    private SessaoDAO sessaoDAO;

    public SessaoController() {
        this.sessaoDAO = new SessaoDAO();
    }

    // [AGREGAÇÃO - List]: Retorna uma lista de objetos Sessao recuperados do banco.
    public List<Sessao> listarPorFilme(int filmeId) {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Delega a consulta SQL para o DAO.
        return sessaoDAO.listarPorFilme(filmeId);
    }
}