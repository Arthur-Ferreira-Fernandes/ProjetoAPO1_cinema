package controller;

import java.sql.SQLException;
import banco.SalaDAO;
import model.Sala;

public class SalaController {

    private SalaDAO salaDAO;

    public SalaController() {
        this.salaDAO = new SalaDAO();
    }

    public Sala buscarSala(int salaId) {
        try {
            // Usa o método existente no seu SalaDAO atual
            return salaDAO.buscarPorId(salaId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Método auxiliar para texto simples, caso precise
    public String verificarStatusSala(int salaId) {
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