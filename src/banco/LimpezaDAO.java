package banco;

import java.sql.*;

public class LimpezaDAO {

    public void registrarInicio(int salaId) throws SQLException {
        String sql = "{call sp_IniciarLimpeza(?)}";
        try (Connection conn = new DBConnection().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, salaId);
            cs.execute();
        }
    }

    public void registrarFim(int salaId) throws SQLException {
        String sql = "{call sp_FinalizarLimpeza(?)}";
        try (Connection conn = new DBConnection().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, salaId);
            cs.execute();
        }
    }
    
    // Método existente para ver status atual
    public String buscarUltimoStatus(int salaId) {
        String sql = "SELECT StatusLimpeza, DataLimpeza FROM Limpeza WHERE SalaId = ? ORDER BY LimpezaId DESC LIMIT 1";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, salaId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("StatusLimpeza") + " em " + rs.getString("DataLimpeza");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Sem registros recentes.";
    }

    // [NOVO MÉTODO]: Busca o histórico completo de limpezas
    public String buscarHistorico(int salaId) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT StatusLimpeza, DataLimpeza, ObservacaoLimpeza FROM Limpeza WHERE SalaId = ? ORDER BY LimpezaId DESC LIMIT 10";
        
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, salaId);
            ResultSet rs = ps.executeQuery();
            
            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                String obs = rs.getString("ObservacaoLimpeza");
                if (obs == null) obs = "-";
                
                sb.append("Data: ").append(rs.getString("DataLimpeza"))
                  .append(" - Status: ").append(rs.getString("StatusLimpeza"))
                  .append(" (").append(obs).append(")\n");
            }
            if (!encontrou) {
                return "Nenhum registro de limpeza encontrado.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao buscar histórico de limpeza.";
        }
        return sb.toString();
    }
}