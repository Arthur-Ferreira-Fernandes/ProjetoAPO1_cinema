package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import banco.DBConnection;
import model.Ingresso;

public class IngressoController {

    /**
     * Realiza a compra do ingresso garantindo a integridade dos dados.
     * Utiliza uma transação para garantir que a poltrona só seja ocupada
     * se o ingresso for gerado com sucesso.
     */
    public int comprarIngressoRetornandoId(Ingresso ingresso) {
        int ingressoId = -1; // Valor padrão de erro
        
        // [ASSOCIAÇÃO]: O Controller cria uma instância de conexão com o banco.
        DBConnection db = new DBConnection();
        Connection conn = db.getConnection();

        // [TRATAMENTO DE ERROS E EXCEÇÕES]: Bloco try-catch para capturar falhas SQL.
        try {
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS - TRANSAÇÃO]: 
            // Desliga o commit automático para tratar as duas operações (Update + Insert) como uma única transação atômica.
            conn.setAutoCommit(false);

            // 1️⃣ Passo 1: Atualiza poltrona como ocupada
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]
            String sqlPoltrona = "UPDATE Poltrona SET Disponibilidade = 0 WHERE PoltronaId = ?";
            PreparedStatement psPoltrona = conn.prepareStatement(sqlPoltrona);
            psPoltrona.setInt(1, ingresso.getPoltronaId());
            psPoltrona.executeUpdate();

            // 2️⃣ Passo 2: Insere o ingresso e solicita o ID gerado (RETURN_GENERATED_KEYS)
            String sqlIngresso = "INSERT INTO Ingresso(StatusIngresso, DataCompra, ClienteId, SessaoId, PoltronaId) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psIngresso = conn.prepareStatement(sqlIngresso, PreparedStatement.RETURN_GENERATED_KEYS);
            
            // Define os parâmetros do PreparedStatement
            psIngresso.setString(1, ingresso.getStatus());
            psIngresso.setObject(2, ingresso.getDataCompra()); // LocalDateTime é convertido automaticamente pelo driver JDBC moderno
            psIngresso.setInt(3, ingresso.getClienteId());
            psIngresso.setInt(4, ingresso.getSessaoId());
            psIngresso.setInt(5, ingresso.getPoltronaId());
            psIngresso.executeUpdate();

            // Recupera a chave primária (ID) gerada pelo banco
            ResultSet rs = psIngresso.getGeneratedKeys();
            if (rs.next()) {
                ingressoId = rs.getInt(1); // Pega o ID do ingresso gerado
            }

            // [BANCO DE DADOS - COMMIT]: Se tudo deu certo, confirma as alterações no banco.
            conn.commit();

            // Fecha os recursos para liberar memória
            psPoltrona.close();
            psIngresso.close();
            rs.close();
            conn.close();

        } catch (SQLException e) {
            // [TRATAMENTO DE ERROS - ROLLBACK]: 
            // Em caso de qualquer falha, desfaz todas as alterações (libera a poltrona novamente).
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace(); // Loga o erro no console
        }

        return ingressoId;
    }
}