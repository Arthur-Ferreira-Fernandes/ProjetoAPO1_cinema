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

    private ClienteDAO clienteDAO;
    private IngressoDAO ingressoDAO;
    private PoltronaDAO poltronaDAO;

    public IngressoController() {
        this.clienteDAO = new ClienteDAO();
        this.ingressoDAO = new IngressoDAO();
        this.poltronaDAO = new PoltronaDAO();
    }

    public int comprarIngresso(String nome, String email, String telefone, int sessaoId, Poltrona poltrona) {
        try {
            if (nome.isEmpty() || email.isEmpty() || poltrona == null) return -1;

            Cliente cliente = new Cliente(nome, email, telefone);
            int clienteId = clienteDAO.salvarOuBuscar(cliente);
            if (clienteId == -1) return -1;

            Ingresso ingresso = new Ingresso();
            ingresso.setClienteId(clienteId);
            ingresso.setSessaoId(sessaoId);
            ingresso.setPoltronaId(poltrona.getId());
            ingresso.setStatus("PAGO");

            // O DAO agora chama a procedure que valida se a Sala está Disponível
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

    public List<Poltrona> listarPoltronasPorSala(int salaId) {
        return poltronaDAO.listarPorSala(salaId);
    }
}