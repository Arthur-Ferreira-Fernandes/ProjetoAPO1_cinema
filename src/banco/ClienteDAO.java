package banco;

import java.sql.*;
import model.Cliente;

public class ClienteDAO {

    public int salvarOuBuscar(Cliente c) throws SQLException {
        int id = buscarIdPorEmail(c.getEmail());
        if (id != -1) {
            return id;
        }
        return inserir(c);
    }

    private int buscarIdPorEmail(String email) throws SQLException {
        String sql = "SELECT ClienteId FROM Cliente WHERE Email = ?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ClienteId");
            }
        }
        return -1;
    }

    private int inserir(Cliente c) throws SQLException {
        // [CORREÇÃO]: Coluna NomeCliente
        String sql = "INSERT INTO Cliente (NomeCliente, Email, Telefone) VALUES (?, ?, ?)";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Erro ao inserir cliente.");
    }
}