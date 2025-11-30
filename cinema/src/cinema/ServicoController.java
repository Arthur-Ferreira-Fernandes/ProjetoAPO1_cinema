package cinema;

import java.sql.Connection;

import banco.LimpezaBanco;
import banco.ManutencaoBanco;
import banco.ReservaBanco;
import banco.PoltronaBanco;

public class ServicoController {

    private LimpezaBanco limpezaBanco;
    private ManutencaoBanco manutencaoBanco;

    private ReservaBanco reservaBanco;
    private PoltronaBanco poltronaBanco;

    public ServicoController(Connection conn) {
        this.limpezaBanco = new LimpezaBanco(conn);
        this.manutencaoBanco = new ManutencaoBanco(conn);

        this.reservaBanco = new ReservaBanco(conn);
        this.poltronaBanco = new PoltronaBanco(conn);
    }

    // ============================
    // LIMPEZA
    // ============================
    public String iniciarLimpeza(int salaId) throws Exception {
        limpezaBanco.iniciarLimpeza(salaId);
        return "Limpeza iniciada com sucesso!";
    }

    public String finalizarLimpeza(int salaId) throws Exception {
        String status = limpezaBanco.verificarStatus(salaId);

        if (status == null || !status.equals("EM ANDAMENTO"))
            return "Nenhuma limpeza em andamento.";

        limpezaBanco.atualizarStatus(salaId, "concluido");
        limpezaBanco.registrarConclusao(salaId);

        return "Limpeza finalizada com sucesso!";
    }

    // ============================
    // MANUTENÇÃO
    // ============================
    public String iniciarManutencao(int salaId) throws Exception {
        manutencaoBanco.iniciarManutencao(salaId);
        return "Manutenção iniciada com sucesso!";
    }

    public String finalizarManutencao(int salaId) throws Exception {
        String status = manutencaoBanco.verificarStatus(salaId);

        if (status == null || !status.equals("EM ANDAMENTO"))
            return "Nenhuma manutenção em andamento.";

        manutencaoBanco.finalizarManutencao(salaId);

        return "Manutenção finalizada com sucesso!";
    }

    // ============================
    // STATUS DA SALA
    // ============================
    public String verStatusSala(int salaId) throws Exception {
        String lim = limpezaBanco.verificarStatus(salaId);
        String man = manutencaoBanco.verificarStatus(salaId);

        String s = "\nStatus da Sala " + salaId + ":\n";

        s += "- Limpeza: " + (lim == null ? "Nenhuma" : lim) + "\n";
        s += "- Manutenção: " + (man == null ? "Nenhuma" : man) + "\n";

        return s;
    }

    // ============================
    // HISTÓRICO
    // ============================
    public String verHistorico(int salaId) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== HISTÓRICO DA SALA " + salaId + " ===\n\n");

        sb.append("--- LIMPEZA ---\n");
        var rs = limpezaBanco.historicoLimpeza(salaId);
        while (rs.next())
            sb.append(rs.getString("data") + " - " + rs.getString("status") + "\n");

        sb.append("\n--- MANUTENÇÃO ---\n");
        rs = manutencaoBanco.historicoManutencao(salaId);
        while (rs.next())
            sb.append(rs.getString("data") + " - " + rs.getString("status") + "\n");

        return sb.toString();
    }

    // ============================
    // CANCELAMENTO DE RESERVA
    // ============================
    public String cancelarReserva(int reservaId) throws Exception {

        String status = reservaBanco.verificarStatus(reservaId);

        if (status == null)
            return "Reserva não encontrada.";

        if (!status.equals("CONFIRMADA") && !status.equals("Comprado"))
            return "A reserva não pode ser cancelada.";

        // cancelar no banco
        reservaBanco.cancelar(reservaId);

        // liberar poltrona
        Integer poltronaId = reservaBanco.getPoltrona(reservaId);
        if (poltronaId != null)
            poltronaBanco.atualizarDisponibilidade(poltronaId, true);

        return "Reserva cancelada com sucesso!";
    }
    
 // ============================
 // VERIFICA SE EXISTE RESERVA
 // ============================
 public boolean existeReserva(int reservaId) throws Exception {
     String status = reservaBanco.verificarStatus(reservaId);
     return status != null;
 }
}