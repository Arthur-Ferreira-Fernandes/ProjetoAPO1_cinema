package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import banco.ManutencaoDAO;
import model.Manutencao;

public class ManutencaoController {

    private ManutencaoDAO manutencaoDAO;

    public ManutencaoController() {
        this.manutencaoDAO = new ManutencaoDAO();
    }

    public String iniciarManutencao(int salaId) {
        try {
            Manutencao manutencao = new Manutencao();
            manutencao.setSalaId(salaId);
            manutencao.setDataManutencao(LocalDateTime.now());
            manutencao.setStatus("EM MANUTENÇÃO");
            manutencao.setObservacao("Bloqueio preventivo");

            manutencaoDAO.registrarInicio(manutencao);
            return "Manutenção iniciada na Sala " + salaId;
        } catch (SQLException e) {
            return "Erro ao iniciar manutenção: " + e.getMessage();
        }
    }

    public String finalizarManutencao(int salaId) {
        try {
            Manutencao manutencao = new Manutencao();
            manutencao.setSalaId(salaId);
            manutencao.setStatus("FINALIZADA");
            
            manutencaoDAO.registrarFim(manutencao);
            return "Manutenção finalizada na Sala " + salaId;
        } catch (SQLException e) {
            return "Erro ao finalizar manutenção: " + e.getMessage();
        }
    }

    public String verHistorico(int salaId) {
        return manutencaoDAO.buscarHistorico(salaId);
    }
}