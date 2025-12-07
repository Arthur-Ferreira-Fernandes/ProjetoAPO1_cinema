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
                p.setNumero(rs.getString("PoltronaNumero"));
                p.setDisponivel(rs.getBoolean("Disponibilidade"));
                p.setSalaId(rs.getInt("SalaId"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public int buscarId(int salaId, String numero) {
        String sql = "SELECT PoltronaId FROM Poltrona WHERE SalaId = ? AND PoltronaNumero = ?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, salaId);
            ps.setString(2, numero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("PoltronaId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Método extra para marcar poltrona como ocupada
    public void atualizarDisponibilidade(int poltronaId, boolean disponivel) throws SQLException {
        String sql = "UPDATE Poltrona SET Disponibilidade = ? WHERE PoltronaId = ?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, disponivel);
            ps.setInt(2, poltronaId);
            ps.executeUpdate();
        }
    }
}