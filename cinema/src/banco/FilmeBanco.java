package banco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Filme;

public class FilmeBanco {

    public List<Filme> listarFilmes() {

        // [AGREGAÇÃO - LIST]: Lista que conterá os filmes do banco.
        List<Filme> filmes = new ArrayList<>();

        // [TRATAMENTO DE ERROS]: Bloco try-catch genérico.
        try {
            DBConnection db = new DBConnection();
            Connection conn = db.getConnection();

            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]
            String sql = "SELECT FilmeId, Titulo FROM Filme";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // [ASSOCIAÇÃO]: Criação de objetos Filme a partir dos dados relacionais.
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