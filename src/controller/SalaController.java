package controller;

import java.sql.SQLException;
import banco.SalaDAO;
import model.Sala;

public class SalaController {

    // [Composição]: O Controller possui um DAO para acesso a dados.
    private SalaDAO salaDAO;

    public SalaController() {
        this.salaDAO = new SalaDAO();
    }

    // [Associação]: Retorna um objeto do tipo 'Sala'.
    public Sala buscarSala(int salaId) {
        // [TRATAMENTO DE ERROS]
        try {
            return salaDAO.buscarPorId(salaId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String verificarStatusSala(int salaId) {
        // [TRATAMENTO DE ERROS]: Tratamento robusto para feedback ao usuário em String.
        try {
            Sala s = salaDAO.buscarPorId(salaId);
            if (s != null) {
                return "Sala " + s.getNumero() + " - Disponível: " + (s.isDisponivel() ? "SIM" : "NÃO");
            }
            return "Sala não encontrada.";
        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }
}