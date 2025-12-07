package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import banco.DBConnection;
import banco.FilmeBanco;
import banco.PoltronaBanco;
import banco.SessaoBanco;
import controller.IngressoController;
import model.Filme;
import model.Ingresso;
import model.Poltrona;
import model.Sessao;

public class IngressoView {

    // [COMPOSIÇÃO]: Instância de janela.
    public JFrame frame;

    private JComboBox<String> comboFilme;
    private JComboBox<String> comboData;
    private JComboBox<String> comboHorario;

    private JLabel lblAssentoSelecionado;
    private JTextField textNomeCliente;
    private JTextField textEmailCliente;
    private JTextField textTelefoneCliente;

    // [ASSOCIAÇÃO]: Referência fraca ao objeto Poltrona selecionado.
    private Poltrona assentoSelecionado;

    // [AGREGAÇÃO - LIST]: A View agrega listas de dados carregadas do banco.
    // Os dados (Filmes/Sessões) existem independentemente desta tela.
    private List<Filme> filmes = new ArrayList<>();
    private List<Sessao> sessoesDoFilme = new ArrayList<>();

    public IngressoView() {
        initialize();
        carregarFilmes();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Comprar Ingresso");
        frame.setBounds(100, 100, 945, 544);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JButton btnVoltarMenu = new JButton("Voltar ao Menu");
        btnVoltarMenu.setBounds(470, 409, 150, 30);
        btnVoltarMenu.addActionListener(e -> {
            frame.dispose(); // fecha a janela atual
            MainView menu = new MainView();
            menu.open(); // abre o menu principal
        });
        frame.getContentPane().add(btnVoltarMenu);

        JLabel lblTitulo = new JLabel("Comprar Ingresso");
        lblTitulo.setBounds(405, 10, 200, 20);
        frame.getContentPane().add(lblTitulo);

        // Combo Filme
        JLabel lblFilme = new JLabel("Selecione o filme:");
        lblFilme.setBounds(10, 106, 146, 17);
        frame.getContentPane().add(lblFilme);

        comboFilme = new JComboBox<>();
        comboFilme.setBounds(173, 98, 197, 25);
        comboFilme.addActionListener(e -> {
            int idx = comboFilme.getSelectedIndex();
            if (idx >= 0 && idx < filmes.size()) {
                Filme f = filmes.get(idx);
                // Carrega dados dependentes (Agregação de Sessões)
                carregarDatas(f.getIdFilme());
                comboHorario.removeAllItems();
                assentoSelecionado = null;
                atualizarLabelAssento();
            }
        });
        frame.getContentPane().add(comboFilme);

        // Combo Data
        JLabel lblData = new JLabel("Selecione a data:");
        lblData.setBounds(10, 174, 146, 17);
        frame.getContentPane().add(lblData);

        comboData = new JComboBox<>();
        comboData.setBounds(173, 166, 197, 25);
        comboData.addActionListener(e -> {
            String dataSelecionada = (String) comboData.getSelectedItem();
            comboHorario.removeAllItems();

            if (dataSelecionada != null) {
                DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

                for (Sessao s : sessoesDoFilme) {
                    String dataSessao = s.getInicio().toLocalDate().format(formatoData);
                    if (dataSessao.equals(dataSelecionada)) {
                        String horaFormatada = s.getInicio().toLocalTime().format(formatoHora);
                        comboHorario.addItem(horaFormatada);
                    }
                }
                assentoSelecionado = null;
                atualizarLabelAssento();
            }
        });
        frame.getContentPane().add(comboData);

        // Combo Horario
        JLabel lblHorario = new JLabel("Selecione o horário:");
        lblHorario.setBounds(10, 242, 146, 17);
        frame.getContentPane().add(lblHorario);

        comboHorario = new JComboBox<>();
        comboHorario.setBounds(173, 234, 197, 25);
        comboHorario.addActionListener(e -> {
            assentoSelecionado = null;
            atualizarLabelAssento();
        });
        frame.getContentPane().add(comboHorario);

        // Label Assento
        JLabel lblAssento = new JLabel("Assento selecionado:");
        lblAssento.setBounds(10, 300, 146, 17);
        frame.getContentPane().add(lblAssento);

        lblAssentoSelecionado = new JLabel("Nenhum");
        lblAssentoSelecionado.setBounds(173, 300, 100, 17);
        frame.getContentPane().add(lblAssentoSelecionado);

        // Botão Selecionar Assento
        JButton btnSelecionarAssento = new JButton("Selecionar Assento");
        btnSelecionarAssento.setBounds(253, 295, 150, 25);
        btnSelecionarAssento.addActionListener(e -> abrirSelecionarPoltrona());
        frame.getContentPane().add(btnSelecionarAssento);

        // Campos do Cliente
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(442, 106, 70, 17);
        frame.getContentPane().add(lblNome);

        textNomeCliente = new JTextField();
        textNomeCliente.setBounds(520, 98, 269, 25);
        frame.getContentPane().add(textNomeCliente);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(442, 174, 70, 17);
        frame.getContentPane().add(lblEmail);

        textEmailCliente = new JTextField();
        textEmailCliente.setBounds(520, 166, 269, 25);
        frame.getContentPane().add(textEmailCliente);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(442, 242, 70, 17);
        frame.getContentPane().add(lblTelefone);

        textTelefoneCliente = new JTextField();
        textTelefoneCliente.setBounds(520, 234, 269, 25);
        frame.getContentPane().add(textTelefoneCliente);

        // Botão Comprar Ingresso
        JButton btnComprar = new JButton("Comprar Ingresso");
        btnComprar.setBounds(310, 409, 150, 30);
        btnComprar.addActionListener(e -> comprarIngresso());
        frame.getContentPane().add(btnComprar);
    }

    private void abrirSelecionarPoltrona() {
        String horario = (String) comboHorario.getSelectedItem();
        // [TRATAMENTO DE ERROS]: Validação de entrada.
        if (horario == null) {
            JOptionPane.showMessageDialog(frame, "Selecione data e horário primeiro!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Sessao sessaoSelecionada = null;
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        for (Sessao s : sessoesDoFilme) {
            String horaFormatada = s.getInicio().toLocalTime().format(formatoHora);
            if (horaFormatada.equals(horario)) {
                sessaoSelecionada = s;
                break;
            }
        }

        if (sessaoSelecionada != null) {
            int salaId = sessaoSelecionada.getSalaId();
            // [ASSOCIAÇÃO]: Usa PoltronaBanco para buscar dados.
            PoltronaBanco pb = new PoltronaBanco();
            
            // [AGREGAÇÃO]: Carrega lista de poltronas do banco.
            List<Poltrona> poltronas = pb.listarPoltronas(salaId);

            // [ASSOCIAÇÃO]: Cria janela filha passando 'this' para callback.
            SelecionarPoltronaView selecionarPoltrona = new SelecionarPoltronaView(frame, poltronas, this);
            selecionarPoltrona.setVisible(true);
        }
    }

    public void definirPoltrona(Poltrona p) {
        if (p != null) {
            assentoSelecionado = p;
            atualizarLabelAssento();
        }
    }

    private void atualizarLabelAssento() {
        if (assentoSelecionado == null) {
            lblAssentoSelecionado.setText("Nenhum");
        } else {
            lblAssentoSelecionado.setText(assentoSelecionado.getNumero());
        }
    }

    private void comprarIngresso() {
        String nome = textNomeCliente.getText().trim();
        String email = textEmailCliente.getText().trim();
        String telefone = textTelefoneCliente.getText().trim();
        String horario = (String) comboHorario.getSelectedItem();

        // Lógica de busca de sessão
        Sessao sessaoSelecionada = null;
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        for (Sessao s : sessoesDoFilme) {
            String horaFormatada = s.getInicio().toLocalTime().format(formatoHora);
            if (horaFormatada.equals(horario)) {
                sessaoSelecionada = s;
                break;
            }
        }

        // [TRATAMENTO DE ERROS]: Validações de Interface
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Preencha todos os dados do cliente!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (sessaoSelecionada == null) {
            JOptionPane.showMessageDialog(frame, "Selecione a data e horário do filme!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (assentoSelecionado == null) {
            JOptionPane.showMessageDialog(frame, "Selecione o assento!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // [CÓDIGO DE ACESSO AO BANCO]: Chama método privado com JDBC direto
        int clienteId = inserirOuPegarCliente(nome, email, telefone);
        if (clienteId == -1) {
            JOptionPane.showMessageDialog(frame, "Erro ao cadastrar cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criação de Entidade (Model)
        Ingresso ingresso = new Ingresso(
            "CONFIRMADA",
            LocalDateTime.now(),
            clienteId,
            sessaoSelecionada.getId(),
            getPoltronaId(sessaoSelecionada.getSalaId(), assentoSelecionado.getNumero())
        );

        // [ASSOCIAÇÃO]: Delega a persistência para o Controller
        IngressoController controller = new IngressoController();
        int ingressoId = controller.comprarIngressoRetornandoId(ingresso);

        if (ingressoId != -1) {
            JOptionPane.showMessageDialog(frame, 
                "Ingresso comprado com sucesso!\nCódigo do ingresso: " + ingressoId,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );
            assentoSelecionado = null;
            atualizarLabelAssento();
        } else {
            JOptionPane.showMessageDialog(frame, "Erro ao comprar ingresso.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // [CÓDIGO DE ACESSO AO BANCO DE DADOS]: JDBC Direto (Mistura de camadas)
    // Insere ou recupera um cliente
    private int inserirOuPegarCliente(String nome, String email, String telefone) {
        int clienteId = -1;

        try {
            DBConnection db = new DBConnection();
            java.sql.Connection conn = db.getConnection();

            // Verifica se cliente já existe
            String sqlSelect = "SELECT ClienteId FROM Cliente WHERE Email = ?";
            java.sql.PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setString(1, email);
            java.sql.ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                clienteId = rs.getInt("ClienteId");
            } else {
                // Se não existe, insere e pega o ID gerado
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
            // [TRATAMENTO DE ERROS]: Loga exceção SQL
            e.printStackTrace();
        }

        return clienteId;
    }

    private int getPoltronaId(int salaId, String poltronaNumero) {
        PoltronaBanco dao = new PoltronaBanco();
        return dao.getPoltronaId(salaId, poltronaNumero);
    }

    private void carregarFilmes() {
        // [ASSOCIAÇÃO]: Usa o DAO para preencher a agregação local
        FilmeBanco dao = new FilmeBanco();
        filmes = dao.listarFilmes();
        comboFilme.removeAllItems();
        for (Filme f : filmes) {
            comboFilme.addItem(f.getNome());
        }
    }

    private void carregarDatas(int filmeId) {
        SessaoBanco dao = new SessaoBanco();
        sessoesDoFilme = dao.listarPorFilme(filmeId);
        comboData.removeAllItems();

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Sessao s : sessoesDoFilme) {
            String dataFormatada = s.getInicio().toLocalDate().format(formatoData);
            boolean existe = false;
            for (int i = 0; i < comboData.getItemCount(); i++) {
                if (comboData.getItemAt(i).equals(dataFormatada)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                comboData.addItem(dataFormatada);
            }
        }
    }
}