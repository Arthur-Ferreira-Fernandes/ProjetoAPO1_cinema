package controller;

import java.sql.SQLException;
import java.util.List;
import banco.ClienteDAO;
import banco.IngressoDAO;
import banco.PoltronaDAO;
import model.Cliente;
import model.Ingresso;
import model.Poltrona;
import model.Reserva;

public class IngressoController {

    // [Composição]: Este controller agrega múltiplos DAOs para realizar
    // uma transação complexa (Cliente + Ingresso + Poltrona).
    private ClienteDAO clienteDAO;
    private IngressoDAO ingressoDAO;
    private PoltronaDAO poltronaDAO;

    public IngressoController() {
        this.clienteDAO = new ClienteDAO();
        this.ingressoDAO = new IngressoDAO();
        this.poltronaDAO = new PoltronaDAO();
    }

    // [Associação]: Recebe objeto 'Poltrona' como parâmetro.
    public int comprarIngresso(String nome, String email, String telefone, int sessaoId, Poltrona poltrona) {
        // [TRATAMENTO DE ERROS]
        try {
            if (nome.isEmpty() || email.isEmpty() || poltrona == null) return -1;

            // [Associação]: Criação de objeto Cliente (Model)
            Cliente cliente = new Cliente(nome, email, telefone);
            
            int clienteId = clienteDAO.salvarOuBuscar(cliente);
            if (clienteId == -1) return -1;

            // [Associação]: Criação de objeto Ingresso (Model)
            Ingresso ingresso = new Ingresso();
            ingresso.setClienteId(clienteId);
            ingresso.setSessaoId(sessaoId);
            ingresso.setPoltronaId(poltrona.getId());
            ingresso.setStatus("PAGO");

            return ingressoDAO.inserir(ingresso); 

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Reserva buscarReserva(int ingressoId) {
        return ingressoDAO.buscarPorId(ingressoId);
    }

    public String cancelarReserva(int ingressoId) {
        // [TRATAMENTO DE ERROS]
        try {
            Reserva reserva = ingressoDAO.buscarPorId(ingressoId);
            if (reserva != null) {
                return ingressoDAO.cancelar(reserva);
            } else {
                return "Reserva não encontrada.";
            }
        } catch (SQLException e) {
            return "Erro ao cancelar: " + e.getMessage();
        }
    }

    // [Agregação - List]: Retorna lista de poltronas da sala.
    public List<Poltrona> listarPoltronasPorSala(int salaId) {
        return poltronaDAO.listarPorSala(salaId);
    }
}