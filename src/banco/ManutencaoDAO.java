package banco;

import java.sql.*;

public class ManutencaoDAO {

    public void registrarInicio(int salaId) throws SQLException {
        String sql = "{call sp_IniciarManutencao(?)}";
        try (Connection conn = new DBConnection().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, salaId);
            cs.execute();
        }
    }

    public void registrarFim(int salaId) throws SQLException {
        String sql = "{call sp_FinalizarManutencao(?)}";
        try (Connection conn = new DBConnection().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, salaId);
            cs.execute();
        }
    }
    
    // Mantém SELECT normal para consulta
    public String buscarHistorico(int salaId) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT StatusManutencao, DataManutencao, ObservacaoManutencao FROM Manutencao WHERE SalaId = ? ORDER BY ManutencaoId DESC LIMIT 10";
        
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, salaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String obs = rs.getString("ObservacaoManutencao");
                if (obs == null) obs = "-";
                
                sb.append("Data: ").append(rs.getString("DataManutencao"))
                  .append(" - Status: ").append(rs.getString("StatusManutencao"))
                  .append(" (").append(obs).append(")\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao buscar histórico.";
        }
        return sb.length() > 0 ? sb.toString() : "Nenhum histórico encontrado.";
    }
}