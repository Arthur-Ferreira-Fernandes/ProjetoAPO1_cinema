package banco;

import java.sql.*;
import java.util.*;
import model.Poltrona;

public class PoltronaBanco {

    // [ASSOCIAÇÃO]: Mantém uma referência para a conexão.
    private Connection conn;

    // [MÉTODO COM SOBRECARGA]: Construtor que recebe a conexão injetada.
    public PoltronaBanco(Connection conn) {
        this.conn = conn;
    }

    // [MÉTODO COM SOBRECARGA]: Construtor que cria sua própria conexão.
    public PoltronaBanco() {
        try {
            // [ASSOCIAÇÃO]: Cria instância de DBConnection.
            this.conn = new DBConnection().getConnection();
        } catch (Exception e) {
            // [TRATAMENTO DE ERROS]: Captura falha na conexão.
            e.printStackTrace();
            this.conn = null;
        }
    }

    // Atualiza a disponibilidade
    public void atualizarDisponibilidade(int poltronaId, boolean disponivel) {
        if (conn == null) return;
        
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Query de Update.
        String sql = "UPDATE Poltrona SET Disponibilidade = ? WHERE PoltronaId = ?";
        
        // [TRATAMENTO DE ERROS]: Try-with-resources (fecha stmt automaticamente).
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, poltronaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ocuparPoltrona(int poltronaId) {
        atualizarDisponibilidade(poltronaId, false);
    }

    public void liberarPoltrona(int poltronaId) {
        atualizarDisponibilidade(poltronaId, true);
    }

    // Lista TODAS as poltronas
    public List<Poltrona> listarPoltronas(int salaId) {
        if (conn == null) return Collections.emptyList();
        
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Query de Select.
        String sql = "SELECT PoltronaId, PoltronaNumero, Disponibilidade FROM Poltrona WHERE SalaId = ?";
        
        // [AGREGAÇÃO - LIST]: Cria uma lista de objetos Poltrona.
        List<Poltrona> lista = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // [ASSOCIAÇÃO]: Instancia objetos do Model baseados no ResultSet.
                    lista.add(new Poltrona(
                        rs.getString("PoltronaNumero"),
                        !rs.getBoolean("Disponibilidade")
                    ));
                }
            }
        } catch (SQLException e) {
            // [TRATAMENTO DE ERROS]
            e.printStackTrace();
        }
        return lista;
    }

    public int getPoltronaId(int salaId, String poltronaNumero) {
        if (conn == null) return -1;
        String sql = "SELECT PoltronaId FROM Poltrona WHERE SalaId = ? AND PoltronaNumero = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salaId);
            stmt.setString(2, poltronaNumero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("PoltronaId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<String> listarPoltronasDisponiveis(int salaId) {
        if (conn == null) return Collections.emptyList();
        String sql = "SELECT PoltronaNumero FROM Poltrona WHERE SalaId = ? AND Disponibilidade = 1 ORDER BY PoltronaNumero";
        List<String> lista = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("PoltronaNumero"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}