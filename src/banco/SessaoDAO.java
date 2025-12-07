package banco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Sessao;

public class SessaoDAO {

    public List<Sessao> listarPorFilme(int filmeId) {
        List<Sessao> lista = new ArrayList<>();
        String sql = "SELECT * FROM Sessao WHERE FilmeId = ?";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, filmeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sessao s = new Sessao();
                s.setId(rs.getInt("SessaoId"));
                s.setInicio(rs.getTimestamp("HorarioInicio").toLocalDateTime());
                s.setFim(rs.getTimestamp("HorarioFim").toLocalDateTime());
                s.setSalaId(rs.getInt("SalaId"));
                s.setFilmeId(rs.getInt("FilmeId"));
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}