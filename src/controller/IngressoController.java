package controller;

import java.sql.SQLException;
import java.util.List;

import banco.ClienteDAO;
import banco.IngressoDAO;
import banco.PoltronaDAO;
import model.Cliente;
import model.Ingresso;
import model.Poltrona;

public class IngressoController {

    // [COMPOSIÇÃO]: Este Controller atua como uma fachada (Facade) coordenando três DAOs diferentes.
    private ClienteDAO clienteDAO;
    private IngressoDAO ingressoDAO;
    private PoltronaDAO poltronaDAO;

    public IngressoController() {
        this.clienteDAO = new ClienteDAO();
        this.ingressoDAO = new IngressoDAO();
        this.poltronaDAO = new PoltronaDAO();
    }

    public int comprarIngresso(String nome, String email, String telefone, int sessaoId, Poltrona poltrona) {
        // [TRATAMENTO DE ERROS E EXCEÇÕES]: Gerencia falhas transacionais SQL.
        try {
            if (nome.isEmpty() || email.isEmpty() || poltrona == null) {
                return -1;
            }

            // 1. Resolve o Cliente (Lógica de Negócio)
            // [ASSOCIAÇÃO]: Criação de objeto temporário Cliente para persistência.
            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Verifica se cliente existe ou cria novo.
            int clienteId = clienteDAO.salvarOuBuscar(cliente);
            if (clienteId == -1) return -1;

            // 2. Prepara objeto Ingresso
            // [ASSOCIAÇÃO]: Criação do objeto Ingresso associando IDs recuperados.
            Ingresso ingresso = new Ingresso();
            ingresso.setClienteId(clienteId);
            ingresso.setSessaoId(sessaoId);
            ingresso.setPoltronaId(poltrona.getId());
            ingresso.setStatus("PAGO");

            // 3. Chama a Procedure via DAO
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Executa INSERT do ingresso e UPDATE da poltrona.
            // [NOTA]: A procedure já marca a poltrona como ocupada automaticamente!
            int ingressoId = ingressoDAO.inserir(ingresso);

            return ingressoId;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // [AGREGAÇÃO - List]: Retorna lista de poltronas da sala.
    public List<Poltrona> listarPoltronasPorSala(int salaId) {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Consulta tabela de poltronas.
        return poltronaDAO.listarPorSala(salaId);
    }

    public boolean existeReserva(int ingressoId) {
        // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Verifica existência (SELECT count).
        return ingressoDAO.existeReserva(ingressoId);
    }

    public String cancelarReserva(int ingressoId) {
        try {
            // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: Chama procedure sp_CancelarReserva (Deleta ingresso e libera poltrona).
            return ingressoDAO.cancelarReserva(ingressoId);
        } catch (SQLException e) {
            // [TRATAMENTO DE ERROS]: Retorna mensagem amigável em caso de erro SQL.
            return "Erro ao cancelar: " + e.getMessage();
        }
    }
}