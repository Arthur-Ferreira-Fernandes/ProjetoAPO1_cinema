package banco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Filme;

public class FilmeBanco {

    public List<Filme> listarFilmes() {

        List<Filme> filmes = new ArrayList<>();

        try {
            DBConnection db = new DBConnection();
            Connection conn = db.getConnection();

            String sql = "SELECT FilmeId, Titulo FROM Filme";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Filme filme = new Filme(
                    rs.getInt("FilmeId"),
                    rs.getString("Titulo")
                );
                filmes.add(filme);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filmes;
    }
}
