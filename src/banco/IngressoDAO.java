package banco;

import java.sql.*;
import model.Ingresso;

public class IngressoDAO {

    // [MODIFICAÇÃO]: Agora chama a procedure sp_ComprarIngresso
    public int inserir(Ingresso ingresso) throws SQLException {
        String sql = "{call sp_ComprarIngresso(?, ?, ?, ?, ?)}";
        
        try (Connection conn = new DBConnection().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            // Parâmetros de Entrada (IN)
            cs.setInt(1, ingresso.getClienteId());
            cs.setInt(2, ingresso.getSessaoId());
            cs.setInt(3, ingresso.getPoltronaId());
            cs.setString(4, ingresso.getStatus());

            // Parâmetro de Saída (OUT) - O ID gerado ou -1 se falhar
            cs.registerOutParameter(5, Types.INTEGER);

            cs.execute();

            return cs.getInt(5); // Retorna o ID do ingresso
        }
    }

    public boolean existeReserva(int ingressoId) {
        String sql = "SELECT IngressoId FROM Ingresso WHERE IngressoId = ?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ingressoId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // [MODIFICAÇÃO]: Agora chama a procedure sp_CancelarReserva
    public String cancelarReserva(int ingressoId) throws SQLException {
        String sql = "{call sp_CancelarReserva(?, ?)}";

        try (Connection conn = new DBConnection().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, ingressoId);
            
            // Registra o parâmetro de saída (mensagem de texto do banco)
            cs.registerOutParameter(2, Types.VARCHAR);
            
            cs.execute();

            return cs.getString(2); // Retorna a mensagem do banco (ex: "Sucesso")
        }
    }
}