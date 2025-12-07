package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManutencaoBanco {

    private Connection conn;

    public ManutencaoBanco(Connection conn) {
        this.conn = conn;
    }

    // [TRATAMENTO DE ERROS]: Propagação de exceção.
    public void iniciarManutencao(int salaId) throws Exception {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]
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
        // 1. Atualiza status anterior
        String sql = "UPDATE Manutencao SET StatusManutencao = 'concluido' WHERE SalaId = ? AND StatusManutencao = 'EM ANDAMENTO'";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, salaId);
        stmt.executeUpdate();

        // 2. Insere log de conclusão
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