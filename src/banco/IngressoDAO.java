package banco;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import model.Ingresso;
import model.Reserva;

public class IngressoDAO {

    public int inserir(Ingresso ingresso) throws SQLException {
        String sql = "{call sp_ComprarIngresso(?, ?, ?, ?, ?)}";
        int idGerado = -1;

        try (Connection conn = new DBConnection().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            // Parâmetros de Entrada (IN)
            cs.setInt(1, ingresso.getClienteId());
            cs.setInt(2, ingresso.getSessaoId());
            cs.setInt(3, ingresso.getPoltronaId());
            cs.setString(4, ingresso.getStatus()); 
            
            cs.registerOutParameter(5, Types.INTEGER);
            
            cs.execute();
            
            // Recupera o ID gerado pela procedure
            idGerado = cs.getInt(5);
        }
        return idGerado;
    }

    public Reserva buscarPorId(int ingressoId) {
        String sql = "SELECT IngressoId, StatusIngresso, PoltronaId FROM Ingresso WHERE IngressoId = ?";
        Reserva reserva = null;

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, ingressoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // [Associação]: Instanciando objeto Reserva
                    reserva = new Reserva(
                        rs.getInt("IngressoId"),
                        rs.getString("StatusIngresso"),
                        rs.getInt("PoltronaId")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reserva;
    }

    public String cancelar(Reserva reserva) throws SQLException {
        String sql = "{call sp_CancelarReserva(?, ?)}"; 
        String resultado = "Erro";

        try (Connection conn = new DBConnection().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, reserva.getId());
            cs.registerOutParameter(2, Types.VARCHAR);
            
            cs.execute();
            
            resultado = cs.getString(2);
        }
        return resultado;
    }
}