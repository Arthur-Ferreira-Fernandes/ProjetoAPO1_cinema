package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import banco.LimpezaDAO;
import model.Limpeza;

public class LimpezaController {

    // [Composição]
    private LimpezaDAO limpezaDAO;

    public LimpezaController() {
        this.limpezaDAO = new LimpezaDAO();
    }

    public String iniciarLimpeza(int salaId) {
        // [TRATAMENTO DE ERROS]
        try {
            // [Associação]: Instancia e configura o objeto Modelo
            Limpeza limpeza = new Limpeza();
            limpeza.setSalaId(salaId);
            limpeza.setDataLimpeza(LocalDateTime.now());
            limpeza.setStatus("EM ANDAMENTO");
            limpeza.setObservacao("Início de rotina");

            limpezaDAO.registrarInicio(limpeza); 
            return "Limpeza iniciada com sucesso na Sala " + salaId;
        } catch (SQLException e) {
            return "Erro ao iniciar limpeza: " + e.getMessage();
        }
    }

    public String finalizarLimpeza(int salaId) {
        try {
            Limpeza limpeza = new Limpeza();
            limpeza.setSalaId(salaId);
            limpeza.setDataLimpeza(LocalDateTime.now());
            limpeza.setStatus("CONCLUÍDA");

            limpezaDAO.registrarFim(limpeza);
            return "Limpeza finalizada com sucesso na Sala " + salaId;
        } catch (SQLException e) {
            return "Erro ao finalizar limpeza: " + e.getMessage();
        }
    }

    public String verHistorico(int salaId) {
        return limpezaDAO.buscarHistorico(salaId);
    }
}