package controller;

import java.sql.SQLException;
import banco.SalaDAO;

public class SalaController {

    // [COMPOSIÇÃO]: O Controller possui uma instância exclusiva de SalaDAO.
    private SalaDAO salaDAO;

    public SalaController() {
        this.salaDAO = new SalaDAO();
    }

    public String verificarStatusSala(int salaId) {
        // [TRATAMENTO DE ERROS E EXCEÇÕES]: Bloco try-catch para lidar com erros de conexão ou consulta.
        try {
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Verifica disponibilidade (retorna boolean).
            boolean disponivel = salaDAO.isDisponivel(salaId);
            
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Busca detalhes descritivos da sala.
            String detalhes = salaDAO.getDetalhes(salaId);
            
            if (disponivel) {
                return detalhes + "\n\nSTATUS: ✅ DISPONÍVEL\nA sala está aberta para sessões.";
            } else {
                return detalhes + "\n\nSTATUS: ❌ INDISPONÍVEL\nA sala está fechada (Manutenção, Limpeza ou Inativa).";
            }
            
        } catch (SQLException e) {
            return "Erro ao verificar sala: " + e.getMessage();
        }
    }
}