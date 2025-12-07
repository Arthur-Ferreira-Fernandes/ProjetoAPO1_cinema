package banco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Filme;

public class FilmeDAO {

    public List<Filme> listarTodos() {
        List<Filme> lista = new ArrayList<>();
        String sql = "SELECT * FROM Filme";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Filme f = new Filme();
                f.setId(rs.getInt("FilmeId"));
                f.setNome(rs.getString("Titulo"));
                f.setDuracao(rs.getInt("Duracao"));
                f.setGenero(rs.getString("Genero"));
                f.setClassificacao(rs.getString("Classificacao"));
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}