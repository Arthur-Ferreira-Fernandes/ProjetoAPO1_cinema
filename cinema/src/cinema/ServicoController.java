package cinema;

import java.sql.Connection;
import java.sql.ResultSet;

import banco.LimpezaDAO;
import banco.ManutencaoDAO;

public class ServicoController {

private LimpezaDAO limpezaDAO;
private ManutencaoDAO manutencaoDAO;

public ServicoController(Connection conn) {
this.limpezaDAO = new LimpezaDAO(conn);
this.manutencaoDAO = new ManutencaoDAO(conn);
}

// LIMPEZA
public String iniciarLimpeza(int salaId) throws Exception {
limpezaDAO.iniciarLimpeza(salaId);
return "Limpeza iniciada com sucesso!";
}

public String finalizarLimpeza(int salaId) throws Exception {
String status = limpezaDAO.verificarStatus(salaId);

if (status == null || !status.equals("EM ANDAMENTO"))
return "Nenhuma limpeza em andamento.";

limpezaDAO.atualizarStatus(salaId, "concluido");
limpezaDAO.registrarConclusao(salaId);

return "Limpeza finalizada com sucesso!";
}

// MANUTENÇÃO
public String iniciarManutencao(int salaId) throws Exception {
manutencaoDAO.iniciarManutencao(salaId);
return "Manutenção iniciada com sucesso!";
}

public String finalizarManutencao(int salaId) throws Exception {
String status = manutencaoDAO.verificarStatus(salaId);

if (status == null || !status.equals("EM ANDAMENTO"))
return "Nenhuma manutenção em andamento.";

manutencaoDAO.finalizarManutencao(salaId);

return "Manutenção finalizada com sucesso!";
}

// STATUS DA SALA
public String verStatusSala(int salaId) throws Exception {
String lim = limpezaDAO.verificarStatus(salaId);
String man = manutencaoDAO.verificarStatus(salaId);

String s = "\nStatus da Sala " + salaId + ":\n";

s += "- Limpeza: " + (lim == null ? "Nenhuma" : lim) + "\n";
s += "- Manutenção: " + (man == null ? "Nenhuma" : man) + "\n";

return s;
}

// HISTÓRICO
public String verHistorico(int salaId) throws Exception {

StringBuilder sb = new StringBuilder();
sb.append("\n=== HISTÓRICO DA SALA " + salaId + " ===\n\n");

// Limpeza
sb.append("--- LIMPEZA ---\n");
ResultSet rs = limpezaDAO.historicoLimpeza(salaId);
while (rs.next())
sb.append(rs.getString("data") + " - " + rs.getString("status") + "\n");

// Manutenção
sb.append("\n--- MANUTENÇÃO ---\n");
rs = manutencaoDAO.historicoManutencao(salaId);
while (rs.next())
sb.append(rs.getString("data") + " - " + rs.getString("status") + "\n");

return sb.toString();
}
}