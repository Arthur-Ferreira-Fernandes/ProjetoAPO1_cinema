package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManutencaoDAO {

private Connection conn;

public ManutencaoDAO(Connection conn) {
this.conn = conn;
}

public void iniciarManutencao(int salaId) throws Exception {
String sql = "INSERT INTO Manutencao (DataManutencao, StatusManutencao, SalaId) VALUES (NOW(), 'EM ANDAMENTO', ?)";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
stmt.executeUpdate();
}

public String verificarStatus(int salaId) throws Exception {
String sql = "SELECT StatusManutencao FROM Manutencao WHERE SalaId = ? ORDER BY ManutencaoId DESC LIMIT 1";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
ResultSet rs = stmt.executeQuery();

if (rs.next())
return rs.getString("StatusManutencao");

return null;
}

public void finalizarManutencao(int salaId) throws Exception {
String sql = "UPDATE Manutencao SET StatusManutencao = 'concluido' WHERE SalaId = ? AND StatusManutencao = 'EM ANDAMENTO'";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
stmt.executeUpdate();

sql = "INSERT INTO Manutencao (DataManutencao, StatusManutencao, SalaId) VALUES (NOW(), 'concluido', ?)";
stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
stmt.executeUpdate();
}

public ResultSet historicoManutencao(int salaId) throws Exception {
String sql = "SELECT DataManutencao AS data, StatusManutencao AS status FROM Manutencao WHERE SalaId = ? ORDER BY DataManutencao DESC";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, salaId);
return stmt.executeQuery();
}
}