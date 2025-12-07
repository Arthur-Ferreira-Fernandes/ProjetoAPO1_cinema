package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Limpeza;

public class LimpezaDAO {

    public void registrarInicio(Limpeza limpeza) throws SQLException {
        // [CORREÇÃO]: Usa a Procedure do banco que bloqueia a sala automaticamente
        String sql = "{call sp_IniciarLimpeza(?)}";
        
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limpeza.getSalaId());
            ps.execute();
        }
    }

    public void registrarFim(Limpeza limpeza) throws SQLException {
        // [CORREÇÃO]: Usa a Procedure que libera a sala
        String sql = "{call sp_FinalizarLimpeza(?)}";
        
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limpeza.getSalaId());
            ps.execute();
        }
    }

    public String buscarUltimoStatus(int salaId) {
        // [CORREÇÃO]: Coluna StatusLimpeza
        String sql = "SELECT StatusLimpeza FROM Limpeza WHERE SalaId = ? ORDER BY LimpezaId DESC LIMIT 1";
        String status = "Desconhecido";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, salaId);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("StatusLimpeza");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public String buscarHistorico(int salaId) {
        StringBuilder sb = new StringBuilder();
        // [CORREÇÃO]: Colunas DataLimpeza e StatusLimpeza
        String sql = "SELECT * FROM Limpeza WHERE SalaId = ?";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, salaId);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    sb.append("Data: ").append(rs.getObject("DataLimpeza"))
                      .append(" - Status: ").append(rs.getString("StatusLimpeza"))
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