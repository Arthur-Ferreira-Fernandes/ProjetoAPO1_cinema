package controller;

import java.sql.SQLException;
import banco.LimpezaDAO;

public class LimpezaController {

    // [COMPOSIÇÃO]: Controller possui o DAO de limpeza.
    private LimpezaDAO limpezaDAO;

    public LimpezaController() {
        this.limpezaDAO = new LimpezaDAO();
    }

    public String iniciarLimpeza(int salaId) {
        // [TRATAMENTO DE ERROS E EXCEÇÕES]
        try {
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Registra início da limpeza.
            limpezaDAO.registrarInicio(salaId);
            return "Limpeza iniciada com sucesso na Sala " + salaId;
        } catch (SQLException e) {
            return "Erro ao iniciar limpeza: " + e.getMessage();
        }
    }

    public String finalizarLimpeza(int salaId) {
        // [TRATAMENTO DE ERROS E EXCEÇÕES]
        try {
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Atualiza registro com data fim e status.
            limpezaDAO.registrarFim(salaId);
            return "Limpeza finalizada com sucesso na Sala " + salaId;
        } catch (SQLException e) {
            return "Erro ao finalizar limpeza: " + e.getMessage();
        }
    }

    public String verStatus(int salaId) {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Busca status atual.
        return limpezaDAO.buscarUltimoStatus(salaId);
    }

    // [NOVO MÉTODO]
    public String verHistorico(int salaId) {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Busca histórico completo.
        return limpezaDAO.buscarHistorico(salaId);
    }
}