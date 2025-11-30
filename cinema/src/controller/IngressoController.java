package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banco.DBConnection;
import model.Ingresso;

public class IngressoController {

    // Compra de ingresso e retorna o ID gerado
    public int comprarIngressoRetornandoId(Ingresso ingresso) {
        int ingressoId = -1; // -1 indica erro
        DBConnection db = new DBConnection();
        Connection conn = db.getConnection();

        try {
            conn.setAutoCommit(false);

            // 1️⃣ Atualiza poltrona como ocupada
            String sqlPoltrona = "UPDATE Poltrona SET Disponibilidade = 0 WHERE PoltronaId = ?";
            PreparedStatement psPoltrona = conn.prepareStatement(sqlPoltrona);
            psPoltrona.setInt(1, ingresso.getPoltronaId());
            psPoltrona.executeUpdate();

            // 2️⃣ Insere ingresso e retorna ID gerado
            String sqlIngresso = "INSERT INTO Ingresso(StatusIngresso, DataCompra, ClienteId, SessaoId, PoltronaId) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psIngresso = conn.prepareStatement(sqlIngresso, PreparedStatement.RETURN_GENERATED_KEYS);
            psIngresso.setString(1, ingresso.getStatus());
            psIngresso.setObject(2, ingresso.getDataCompra());
            psIngresso.setInt(3, ingresso.getClienteId());
            psIngresso.setInt(4, ingresso.getSessaoId());
            psIngresso.setInt(5, ingresso.getPoltronaId());
            psIngresso.executeUpdate();

            ResultSet rs = psIngresso.getGeneratedKeys();
            if (rs.next()) {
                ingressoId = rs.getInt(1); // pega o ID do ingresso gerado
            }

            conn.commit();

            psPoltrona.close();
            psIngresso.close();
            rs.close();
            conn.close();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return ingressoId;
    }
}
