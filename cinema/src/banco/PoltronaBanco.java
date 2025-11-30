package banco;

import java.sql.*;
import java.util.*;
import model.Poltrona;

public class PoltronaBanco {

    private Connection conn;

    public PoltronaBanco(Connection conn) {
        this.conn = conn;
    }

    public PoltronaBanco() {
        try {
            this.conn = new DBConnection().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            this.conn = null;
        }
    }

    // Atualiza a disponibilidade
    public void atualizarDisponibilidade(int poltronaId, boolean disponivel) {
        if (conn == null) return;
        String sql = "UPDATE Poltrona SET Disponibilidade = ? WHERE PoltronaId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, poltronaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Marca como ocupada
    public void ocuparPoltrona(int poltronaId) {
        atualizarDisponibilidade(poltronaId, false);
    }

    // Marca como livre
    public void liberarPoltrona(int poltronaId) {
        atualizarDisponibilidade(poltronaId, true);
    }

    // Lista TODAS as poltronas
    public List<Poltrona> listarPoltronas(int salaId) {
        if (conn == null) return Collections.emptyList();
        String sql = "SELECT PoltronaId, PoltronaNumero, Disponibilidade FROM Poltrona WHERE SalaId = ?";
        List<Poltrona> lista = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Poltrona(
                        rs.getString("PoltronaNumero"),
                        !rs.getBoolean("Disponibilidade")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Obter ID da poltrona pelo número
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

    // Apenas lista números disponíveis
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
