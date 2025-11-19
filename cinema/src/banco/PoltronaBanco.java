package banco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PoltronaBanco {

    public List<String> listarPoltronasDisponiveis(int salaId) {
        List<String> lista = new ArrayList<>();

        try {
            DBConnection db = new DBConnection();
            Connection conn = db.getConnection();

            String sql = "SELECT PoltronaNumero FROM Poltrona WHERE SalaId = ? AND Disponibilidade = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, salaId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("PoltronaNumero"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public int getPoltronaId(int salaId, String poltronaNumero) {
        int id = -1;
        try {
            DBConnection db = new DBConnection();
            Connection conn = db.getConnection();

            String sql = "SELECT PoltronaId FROM Poltrona WHERE SalaId = ? AND PoltronaNumero = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, salaId);
            ps.setString(2, poltronaNumero);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("PoltronaId");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

}
