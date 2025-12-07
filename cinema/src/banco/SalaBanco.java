package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SalaBanco {

    private Connection conn;

    public SalaBanco(Connection conn) {
        this.conn = conn;
    }

    public boolean existeSala(int salaId) throws Exception {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]
        String sql = "SELECT * FROM Sala WHERE SalaId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, salaId);
        ResultSet rs = stmt.executeQuery();
        return rs.next(); // Retorna true se encontrou registro
    }

    public boolean salaDisponivel(int salaId) throws Exception {
        String sql = "SELECT Disponivel FROM Sala WHERE SalaId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, salaId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next())
            return rs.getBoolean("Disponivel");

        return false;
    }
}