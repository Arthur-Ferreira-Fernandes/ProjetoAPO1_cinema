package banco;

import java.sql.*;

public class SalaDAO {

    public boolean isDisponivel(int salaId) throws SQLException {
        String sql = "SELECT Disponivel FROM Sala WHERE SalaId = ?";
        
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, salaId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getBoolean("Disponivel");
            }
        }
        // Se não achar a sala, consideramos indisponível ou lança erro
        throw new SQLException("Sala não encontrada.");
    }
    
    // Método opcional para buscar detalhes extras se quiser exibir
    public String getDetalhes(int salaId) {
        String sql = "SELECT NumeroSala, TipoSala, Capacidade FROM Sala WHERE SalaId = ?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, salaId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "Sala " + rs.getInt("NumeroSala") + " (" + rs.getString("TipoSala") + ") - Cap: " + rs.getInt("Capacidade");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Sala Desconhecida";
    }
}