package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Manutencao;

public class ManutencaoDAO {

    // [Associação]: Recebe o objeto Manutencao para persistência
    public void registrarInicio(Manutencao manutencao) throws SQLException {
        String sql = "{call sp_IniciarManutencao(?)}";
        
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, manutencao.getSalaId());
            ps.execute();
        }
    }

    public void registrarFim(Manutencao manutencao) throws SQLException {
        String sql = "{call sp_FinalizarManutencao(?)}";
        
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, manutencao.getSalaId());
            ps.execute();
        }
    }
    
    public String buscarHistorico(int salaId) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM Manutencao WHERE SalaId = ?";

        // [Tratamento de Erros]
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, salaId);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    sb.append("Início: ").append(rs.getObject("DataManutencao"))
                      .append(" | Status: ").append(rs.getString("StatusManutencao"))
                      .append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao buscar histórico.";
        }
        return sb.length() > 0 ? sb.toString() : "Nenhum registro encontrado.";
    }
}