package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Sala;

public class SalaDAO {

    public Sala buscarPorId(int salaId) throws SQLException {
        String sql = "SELECT * FROM Sala WHERE SalaId = ?";
        Sala sala = null;

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, salaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sala = new Sala();
                    sala.setId(rs.getInt("SalaId"));
                    // [CORREÇÃO]: Nomes das colunas conforme seu novo SQL
                    sala.setNumero(rs.getInt("NumeroSala")); 
                    sala.setCapacidade(rs.getInt("Capacidade"));
                    sala.setTipo(rs.getString("TipoSala")); 
                    sala.setDisponivel(rs.getBoolean("Disponivel"));
                }
            }
        }
        return sala;
    }
}