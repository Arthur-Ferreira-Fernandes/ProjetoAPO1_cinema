package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Filme;

public class FilmeDAO {

    // [Agregação]: Retorna uma List de Filmes. O DAO agrega múltiplos objetos Filme.
    public List<Filme> listarTodos() {
        List<Filme> lista = new ArrayList<>();
        String sql = "SELECT * FROM Filme";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // [Associação]: Criação de instâncias de Filme dentro do loop
                Filme f = new Filme();
                f.setId(rs.getInt("FilmeId"));
                f.setNome(rs.getString("Titulo")); 
                f.setDuracao(rs.getInt("Duracao"));
                f.setGenero(rs.getString("Genero"));
                f.setClassificacao(rs.getString("Classificacao"));
                f.setSinopse(rs.getString("Sinopse"));
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}