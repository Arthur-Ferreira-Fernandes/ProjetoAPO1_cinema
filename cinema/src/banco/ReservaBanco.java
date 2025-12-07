package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReservaBanco {

    private Connection conn;

    public ReservaBanco(Connection conn) {
        this.conn = conn;
    }

    // [TRATAMENTO DE ERROS]: throws Exception.
    public String verificarStatus(int reservaId) throws Exception {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT StatusIngresso FROM Ingresso WHERE IngressoId = ?"
        );
        stmt.setInt(1, reservaId);

        ResultSet rs = stmt.executeQuery();
        if (rs.next())
            return rs.getString("StatusIngresso");

        return null;
    }

    public Integer getPoltrona(int reservaId) throws Exception {
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT PoltronaId FROM Ingresso WHERE IngressoId = ?"
        );
        stmt.setInt(1, reservaId);

        ResultSet rs = stmt.executeQuery();
        if (rs.next())
            return rs.getInt("PoltronaId"); // Auto-boxing int -> Integer

        return null;
    }

    public void cancelar(int reservaId) throws Exception {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Update lógico (soft delete/status change).
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Ingresso SET StatusIngresso = 'CANCELADA' WHERE IngressoId = ?"
        );
        stmt.setInt(1, reservaId);

        stmt.executeUpdate();
    }
}