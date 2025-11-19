package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import banco.DBConnection;
import cinema.ServicoController;

public class LimpezaView {

private ServicoController controller;

public LimpezaView(Display display) {

// -----------------------------
// Conexão e Controller
// -----------------------------
DBConnection db = new DBConnection();
controller = new ServicoController(db.getConnection());

// -----------------------------
// SHELL (Janela)
// -----------------------------
Shell shell = new Shell(display);
shell.setText("Sistema do Cinema - Serviços");
shell.setSize(650, 500);
shell.setLayout(null);

// -----------------------------
// CAMPO DE SALA
// -----------------------------
Label lblSala = new Label(shell, SWT.NONE);
lblSala.setText("Sala:");
lblSala.setBounds(20, 20, 40, 25);

Text txtSala = new Text(shell, SWT.BORDER);
txtSala.setBounds(70, 20, 80, 25);

// -----------------------------
// ÁREA DE RESULTADO
// -----------------------------
Text txtResultado = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
txtResultado.setBounds(20, 220, 600, 220);

// -----------------------------
// BOTÕES
// -----------------------------
Button btnIniciarLimpeza = new Button(shell, SWT.PUSH);
btnIniciarLimpeza.setText("Iniciar Limpeza");
btnIniciarLimpeza.setBounds(20, 70, 150, 30);

Button btnFinalizarLimpeza = new Button(shell, SWT.PUSH);
btnFinalizarLimpeza.setText("Finalizar Limpeza");
btnFinalizarLimpeza.setBounds(200, 70, 150, 30);

Button btnIniciarMan = new Button(shell, SWT.PUSH);
btnIniciarMan.setText("Iniciar Manutenção");
btnIniciarMan.setBounds(20, 120, 150, 30);

Button btnFinalizarMan = new Button(shell, SWT.PUSH);
btnFinalizarMan.setText("Finalizar Manutenção");
btnFinalizarMan.setBounds(200, 120, 180, 30);

Button btnStatus = new Button(shell, SWT.PUSH);
btnStatus.setText("Ver Status da Sala");
btnStatus.setBounds(20, 170, 180, 30);

Button btnHistorico = new Button(shell, SWT.PUSH);
btnHistorico.setText("Ver Histórico");
btnHistorico.setBounds(220, 170, 150, 30);

//-----------------------------
//EVENTOS DOS BOTÕES
//-----------------------------

btnIniciarLimpeza.addListener(SWT.Selection, e -> {
try {
int sala = Integer.parseInt(txtSala.getText());
txtResultado.setText(controller.iniciarLimpeza(sala));
} catch (Exception ex) {
txtResultado.setText("Erro: " + ex.getMessage());
}
});

btnFinalizarLimpeza.addListener(SWT.Selection, e -> {
try {
int sala = Integer.parseInt(txtSala.getText());
txtResultado.setText(controller.finalizarLimpeza(sala));
} catch (Exception ex) {
txtResultado.setText("Erro: " + ex.getMessage());
}
});

btnIniciarMan.addListener(SWT.Selection, e -> {
try {
int sala = Integer.parseInt(txtSala.getText());
txtResultado.setText(controller.iniciarManutencao(sala));
} catch (Exception ex) {
txtResultado.setText("Erro: " + ex.getMessage());
}
});

btnFinalizarMan.addListener(SWT.Selection, e -> {
try {
int sala = Integer.parseInt(txtSala.getText());
txtResultado.setText(controller.finalizarManutencao(sala));
} catch (Exception ex) {
txtResultado.setText("Erro: " + ex.getMessage());
}
});

btnStatus.addListener(SWT.Selection, e -> {
try {
int sala = Integer.parseInt(txtSala.getText());
txtResultado.setText(controller.verStatusSala(sala));
} catch (Exception ex) {
txtResultado.setText("Erro: " + ex.getMessage());
}
});

btnHistorico.addListener(SWT.Selection, e -> {
try {
int sala = Integer.parseInt(txtSala.getText());
txtResultado.setText(controller.verHistorico(sala));
} catch (Exception ex) {
txtResultado.setText("Erro: " + ex.getMessage());
}
});

//-----------------------------
//ABRIR A JANELA
//-----------------------------
shell.open();

while (!shell.isDisposed()) {
if (!display.readAndDispatch())
display.sleep();
}
}

public static void main(String[] args) {
Display display = new Display();
new LimpezaView(display);
display.dispose();
}
}