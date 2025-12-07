package banco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Poltrona;

public class PoltronaDAO {

    public List<Poltrona> listarPorSala(int salaId) {
        List<Poltrona> lista = new ArrayList<>();
        String sql = "SELECT * FROM Poltrona WHERE SalaId = ?";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, salaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Poltrona p = new Poltrona();
                p.setId(rs.getInt("PoltronaId"));
                p.setNumero(rs.getString("PoltronaNumero")); // [CORREÇÃO]
                p.setDisponivel(rs.getBoolean("Disponibilidade"));
                p.setSalaId(rs.getInt("SalaId"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}