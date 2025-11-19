package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import banco.DBConnection;
import banco.FilmeBanco;
import banco.PoltronaBanco;
import banco.SessaoBanco;
import controller.IngressoController;
import model.Filme;
import model.Ingresso;
import model.Sessao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.widgets.Text;

public class ingressoView {

	protected Shell shell;
	private Combo comboFilme;
	private Combo comboData;
	private Combo comboHorario;
	private Combo comboAssento;
	
	private List<Sessao> sessoesDoFilme = new ArrayList<>();
	private Text textNomeCliente;
	private Text textEmailCliente;
	private Text textTelefoneCliente;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ingressoView window = new ingressoView();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		carregarFilmes();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(945, 544);
		shell.setText("SWT Application");
		
		Label lblCompraIngresso = new Label(shell, SWT.NONE);
		lblCompraIngresso.setBounds(405, 10, 135, 17);
		lblCompraIngresso.setText("Comprar Ingresso");
		
		Label lblSelecionaFilme = new Label(shell, SWT.NONE);
		lblSelecionaFilme.setBounds(10, 106, 146, 17);
		lblSelecionaFilme.setText("Selecione o filme");
		comboFilme = new Combo(shell, SWT.READ_ONLY);
        comboFilme.setBounds(173, 98, 197, 23);
        comboFilme.addListener(SWT.Selection, e -> {
            String nome = comboFilme.getText();
            Filme f = (Filme) comboFilme.getData(nome);

            if (f != null) {
                carregarDatas(f.getIdFilme());
                comboHorario.removeAll();
                comboAssento.removeAll();
            }
        });
		
		Label lblSelecionaData = new Label(shell, SWT.NONE);
		lblSelecionaData.setBounds(10, 174, 146, 17);
		lblSelecionaData.setText("Selecione a data");
		
		Label lblSelecionaHorario = new Label(shell, SWT.NONE);
		lblSelecionaHorario.setBounds(10, 242, 146, 17);
		lblSelecionaHorario.setText("Selecione o horario");
		
		Label lblSelecionaAssento = new Label(shell, SWT.NONE);
		lblSelecionaAssento.setBounds(10, 300, 146, 17);
		lblSelecionaAssento.setText("Selecione o assento");
		
		Combo comboFIlme = new Combo(shell, SWT.NONE);
		comboFIlme.setItems(new String[] {"\"Item 1\"", "\"Item 2\""});
		comboFIlme.setBounds(173, 98, 197, 23);
		
		comboData = new Combo(shell, SWT.NONE);
		comboData.setBounds(173, 166, 197, 34);
		comboData.addListener(SWT.Selection, e -> {
		    String dataSelecionada = comboData.getText();
		    comboHorario.removeAll();
		    comboAssento.removeAll();

		    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

		    for (Sessao s : sessoesDoFilme) {
		        String dataSessao = s.getInicio().toLocalDate().format(formatoData);
		        if (dataSessao.equals(dataSelecionada)) {
		            String horaFormatada = s.getInicio().toLocalTime().format(formatoHora);
		            comboHorario.add(horaFormatada);
		            comboHorario.setData(horaFormatada, s);
		        }
		    }
		});
		
		comboHorario = new Combo(shell, SWT.NONE);
		comboHorario.setBounds(173, 234, 197, 34);
		comboHorario.addListener(SWT.Selection, e -> {
		    String hora = comboHorario.getText();
		    Sessao sessao = (Sessao) comboHorario.getData(hora);

		    if (sessao != null) {
		        carregarAssentos(sessao.getSalaId());
		    }
		});
		
		comboAssento = new Combo(shell, SWT.NONE);
		comboAssento.setBounds(173, 292, 197, 34);
		
		Button btnCompraIngresso = new Button(shell, SWT.NONE);
		btnCompraIngresso.setBounds(405, 403, 135, 34);
		btnCompraIngresso.setText("Comprar Ingresso");
		
		Label lblNomeCliente = new Label(shell, SWT.NONE);
		lblNomeCliente.setBounds(442, 106, 70, 17);
		lblNomeCliente.setText("Nome:");
		
		Label lblEmailCliente = new Label(shell, SWT.NONE);
		lblEmailCliente.setBounds(442, 174, 70, 17);
		lblEmailCliente.setText("Email:");
		
		Label lblTelefoneCliente = new Label(shell, SWT.NONE);
		lblTelefoneCliente.setBounds(442, 242, 70, 17);
		lblTelefoneCliente.setText("Telefone:");
		
		textNomeCliente = new Text(shell, SWT.BORDER);
		textNomeCliente.setBounds(590, 98, 269, 34);
		
		textEmailCliente = new Text(shell, SWT.BORDER);
		textEmailCliente.setBounds(590, 168, 269, 30);
		
		textTelefoneCliente = new Text(shell, SWT.BORDER);
		textTelefoneCliente.setBounds(590, 234, 269, 34);
		btnCompraIngresso.addListener(SWT.Selection, e -> {
		    String nomeCliente = textNomeCliente.getText().trim();
		    String emailCliente = textEmailCliente.getText().trim();
		    String telefoneCliente = textTelefoneCliente.getText().trim();

		    String assentoSelecionado = comboAssento.getText();
		    Sessao sessaoSelecionada = (Sessao) comboHorario.getData(comboHorario.getText());

		    if (nomeCliente.isEmpty() || emailCliente.isEmpty() || telefoneCliente.isEmpty()) {
		        MessageBox alerta = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
		        alerta.setText("Erro");
		        alerta.setMessage("Preencha todos os dados do cliente!");
		        alerta.open();
		        return;
		    }

		    if (sessaoSelecionada == null) {
		        MessageBox alerta = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
		        alerta.setText("Erro");
		        alerta.setMessage("Selecione a data e horário do filme!");
		        alerta.open();
		        return;
		    }

		    if (assentoSelecionado.isEmpty()) {
		        MessageBox alerta = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
		        alerta.setText("Erro");
		        alerta.setMessage("Selecione o assento!");
		        alerta.open();
		        return;
		    }

		    int clienteId = inserirOuPegarCliente(nomeCliente, emailCliente, telefoneCliente);
		    if (clienteId == -1) {
		        MessageBox alerta = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		        alerta.setText("Erro");
		        alerta.setMessage("Erro ao cadastrar cliente!");
		        alerta.open();
		        return;
		    }

		    Ingresso ingresso = new Ingresso(
		        "Comprado",
		        LocalDateTime.now(),
		        clienteId,
		        sessaoSelecionada.getId(),
		        getPoltronaId(sessaoSelecionada.getSalaId(), assentoSelecionado)
		    );

		    IngressoController controller = new IngressoController();
		    boolean sucesso = controller.comprarIngresso(ingresso);

		    if (sucesso) {
		        MessageBox alerta = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		        alerta.setText("Sucesso");
		        alerta.setMessage("Ingresso comprado com sucesso!");
		        alerta.open();
		        carregarAssentos(sessaoSelecionada.getSalaId()); // atualiza combo de assentos
		    } else {
		        MessageBox alerta = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		        alerta.setText("Erro");
		        alerta.setMessage("Erro ao comprar ingresso.");
		        alerta.open();
		    }
		});

	}
	
	private int inserirOuPegarCliente(String nome, String email, String telefone) {
	    int clienteId = -1;

	    try {
	        DBConnection db = new DBConnection();
	        java.sql.Connection conn = db.getConnection();

	        
	        String sqlSelect = "SELECT ClienteId FROM Cliente WHERE Email = ?";
	        java.sql.PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
	        psSelect.setString(1, email);
	        java.sql.ResultSet rs = psSelect.executeQuery();

	        if (rs.next()) {
	            clienteId = rs.getInt("ClienteId");
	        } else {
	            
	            String sqlInsert = "INSERT INTO Cliente (NomeCliente, Email, Telefone) VALUES (?, ?, ?)";
	            java.sql.PreparedStatement psInsert = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS);
	            psInsert.setString(1, nome);
	            psInsert.setString(2, email);
	            psInsert.setString(3, telefone);
	            psInsert.executeUpdate();

	            java.sql.ResultSet rsInsert = psInsert.getGeneratedKeys();
	            if (rsInsert.next()) {
	                clienteId = rsInsert.getInt(1);
	            }

	            rsInsert.close();
	            psInsert.close();
	        }

	        rs.close();
	        psSelect.close();
	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return clienteId;
	}

	
	private int getPoltronaId(int salaId, String poltronaNumero) {
	    PoltronaBanco dao = new PoltronaBanco();
	    return dao.getPoltronaId(salaId, poltronaNumero);
	}
	
	private void carregarDatas(int filmeId) {
	    SessaoBanco dao = new SessaoBanco();
	    sessoesDoFilme = dao.listarPorFilme(filmeId);

	    comboData.removeAll();

	    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	    for (Sessao s : sessoesDoFilme) {
	        String dataFormatada = s.getInicio().toLocalDate().format(formatoData);

	        if (comboData.indexOf(dataFormatada) == -1) {
	            comboData.add(dataFormatada);
	        }
	    }
	}


	
	private void carregarAssentos(int salaId) {
	    PoltronaBanco dao = new PoltronaBanco();
	    List<String> poltronas = dao.listarPoltronasDisponiveis(salaId);

	    comboAssento.removeAll();

	    for (String p : poltronas) {
	        comboAssento.add(p);
	    }
	}

	private void carregarFilmes() {
	    FilmeBanco dao = new FilmeBanco();
	    List<Filme> filmes = dao.listarFilmes();

	    comboFilme.removeAll();

	    for (Filme f : filmes) {
	        comboFilme.add(f.getNome());
	        comboFilme.setData(f.getNome(), f);
	    }
	}
}
