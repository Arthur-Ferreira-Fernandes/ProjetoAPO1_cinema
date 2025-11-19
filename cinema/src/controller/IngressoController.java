package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import banco.DBConnection;
import model.Ingresso;

public class IngressoController {

    // Compra de ingresso
	public boolean comprarIngresso(Ingresso ingresso) {
	    boolean sucesso = false;
	    DBConnection db = new DBConnection();
	    Connection conn = db.getConnection();

	    try {
	        conn.setAutoCommit(false);

	        // 1️⃣ Atualiza poltrona como ocupada
	        String sqlPoltrona = "UPDATE Poltrona SET Disponibilidade = 0 WHERE PoltronaId = ?";
	        PreparedStatement psPoltrona = conn.prepareStatement(sqlPoltrona);
	        psPoltrona.setInt(1, ingresso.getPoltronaId());
	        psPoltrona.executeUpdate();

	        // 2️⃣ Insere ingresso
	        String sqlIngresso = "INSERT INTO Ingresso(StatusIngresso, DataCompra, ClienteId, SessaoId, PoltronaId) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement psIngresso = conn.prepareStatement(sqlIngresso);
	        psIngresso.setString(1, ingresso.getStatus());
	        psIngresso.setObject(2, ingresso.getDataCompra());
	        psIngresso.setInt(3, ingresso.getClienteId());
	        psIngresso.setInt(4, ingresso.getSessaoId());
	        psIngresso.setInt(5, ingresso.getPoltronaId());
	        psIngresso.executeUpdate();

	        conn.commit();
	        sucesso = true;

	        psPoltrona.close();
	        psIngresso.close();
	        conn.close();

	    } catch (SQLException e) {
	        try {
	            conn.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    }

	    return sucesso;
	}

}
