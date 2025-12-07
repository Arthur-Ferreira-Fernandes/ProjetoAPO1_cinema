package controller;

import java.sql.SQLException;
import banco.ManutencaoDAO;

public class ManutencaoController {

    // [COMPOSIÇÃO]: O Controller é responsável por instanciar e manter o DAO.
    // Se o Controller deixar de existir, essa instância específica do DAO também deixa.
    private ManutencaoDAO manutencaoDAO;

    public ManutencaoController() {
        this.manutencaoDAO = new ManutencaoDAO();
    }

    public String iniciarManutencao(int salaId) {
        // [TRATAMENTO DE ERROS E EXCEÇÕES]: Captura SQLException caso o banco falhe.
        try {
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Chama o método do DAO que executa o UPDATE/INSERT.
            manutencaoDAO.registrarInicio(salaId);
            return "Manutenção iniciada na Sala " + salaId + ". Bloqueio de vendas sugerido.";
        } catch (SQLException e) {
            return "Erro ao iniciar manutenção: " + e.getMessage();
        }
    }

    public String finalizarManutencao(int salaId) {
        // [TRATAMENTO DE ERROS E EXCEÇÕES]: Proteção contra falhas SQL.
        try {
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Chama o DAO para registrar o fim da manutenção.
            manutencaoDAO.registrarFim(salaId);
            return "Manutenção finalizada na Sala " + salaId + ". Sala liberada.";
        } catch (SQLException e) {
            return "Erro ao finalizar manutenção: " + e.getMessage();
        }
    }

    public String verHistorico(int salaId) {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Busca a lista formatada de manutenções anteriores.
        return manutencaoDAO.buscarHistorico(salaId);
    }
}