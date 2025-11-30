package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReservaBanco {

    private Connection conn;

    public ReservaBanco(Connection conn) {
        this.conn = conn;
    }

    public String verificarStatus(int reservaId) throws Exception {
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
            return rs.getInt("PoltronaId");

        return null;
    }

    public void cancelar(int reservaId) throws Exception {
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Ingresso SET StatusIngresso = 'CANCELADA' WHERE IngressoId = ?"
        );
        stmt.setInt(1, reservaId);

        stmt.executeUpdate();
    }
}