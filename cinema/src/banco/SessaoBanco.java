package banco;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import model.Sessao;

public class SessaoBanco {

    public List<Sessao> listarPorFilme(int filmeId) {
        List<Sessao> lista = new ArrayList<>();

        try {
            DBConnection db = new DBConnection();
            Connection conn = db.getConnection();

            String sql = "SELECT * FROM Sessao WHERE FilmeId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, filmeId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sessao s = new Sessao(
                    rs.getInt("SessaoId"),
                    rs.getTimestamp("HorarioInicio").toLocalDateTime(),
                    rs.getTimestamp("HorarioFim").toLocalDateTime(),
                    rs.getInt("FilmeId"),
                    rs.getInt("SalaId")
                );
                lista.add(s);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
