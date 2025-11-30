package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LimpezaBanco {

private Connection conn;

public LimpezaBanco(Connection conn) {
this.conn = conn;
}

public void iniciarLimpeza(int salaId) throws Exception {
String sql = "INSERT INTO Limpeza (DataLimpeza, StatusLimpeza, SalaId) VALUES (NOW(), 'EM ANDAMENTO', ?)";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
stmt.executeUpdate();
}

public String verificarStatus(int salaId) throws Exception {
String sql = "SELECT StatusLimpeza FROM Limpeza WHERE SalaId = ? ORDER BY LimpezaId DESC LIMIT 1";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
ResultSet rs = stmt.executeQuery();

if (rs.next())
return rs.getString("StatusLimpeza");

return null;
}

public void atualizarStatus(int salaId, String status) throws Exception {
String sql = "UPDATE Limpeza SET StatusLimpeza = ? WHERE SalaId = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, status);
stmt.setInt(2, salaId);
stmt.executeUpdate();
}

public void registrarConclusao(int salaId) throws Exception {
String sql = "INSERT INTO Limpeza (DataLimpeza, StatusLimpeza, SalaId) VALUES (NOW(), 'concluido', ?)";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
stmt.executeUpdate();
}

public ResultSet historicoLimpeza(int salaId) throws Exception {
String sql = "SELECT DataLimpeza AS data, StatusLimpeza AS status FROM Limpeza WHERE SalaId = ? ORDER BY DataLimpeza DESC";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
return stmt.executeQuery();
}
}