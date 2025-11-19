package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SalaDAO {

private Connection conn;

public SalaDAO(Connection conn) {
this.conn = conn;
}

public boolean existeSala(int salaId) throws Exception {
String sql = "SELECT * FROM Sala WHERE SalaId = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
ResultSet rs = stmt.executeQuery();
return rs.next();
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