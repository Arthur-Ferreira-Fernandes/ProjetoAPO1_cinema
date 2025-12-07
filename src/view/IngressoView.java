package view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.FilmeController;
import controller.IngressoController;
import controller.SessaoController;
import model.Filme;
import model.Poltrona;
import model.Sessao;

public class IngressoView {

    public JFrame frame;

    // [COMPOSIÇÃO]: A View instancia e gerencia o ciclo de vida destes Controllers.
    private IngressoController ingressoController;
    private FilmeController filmeController;
    private SessaoController sessaoController;

    // Componentes de Tela
    private JComboBox<String> comboFilme;
    private JComboBox<String> comboData;
    private JComboBox<String> comboHorario;
    private JLabel lblAssentoSelecionado;
    private JTextField textNomeCliente;
    private JTextField textEmailCliente;
    private JTextField textTelefoneCliente;

    // Estado da Tela
    // [ASSOCIAÇÃO]: Referência simples a um objeto Poltrona selecionado na outra tela.
    private Poltrona assentoSelecionado;
    
    // [AGREGAÇÃO - List]: A View agrega listas de objetos do modelo (Filme, Sessao) para exibição nos combos.
    private List<Filme> filmes = new ArrayList<>();
    private List<Sessao> sessoesDoFilme = new ArrayList<>();

    public IngressoView() {
        // Inicializa Controllers
        ingressoController = new IngressoController();
        filmeController = new FilmeController();
        sessaoController = new SessaoController();
        
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
            frame.dispose();
            MainView menu = new MainView();
            menu.open();
        });
        frame.getContentPane().add(btnVoltarMenu);

        JLabel lblTitulo = new JLabel("Comprar Ingresso");
        lblTitulo.setBounds(405, 10, 200, 20);
        frame.getContentPane().add(lblTitulo);

        // --- FILME ---
        JLabel lblFilme = new JLabel("Selecione o filme:");
        lblFilme.setBounds(10, 106, 146, 17);
        frame.getContentPane().add(lblFilme);

        comboFilme = new JComboBox<>();
        comboFilme.setBounds(173, 98, 197, 25);
        comboFilme.addActionListener(e -> {
            int idx = comboFilme.getSelectedIndex();
            if (idx >= 0 && idx < filmes.size()) {
                Filme f = filmes.get(idx);
                carregarDatas(f.getId()); 
                comboHorario.removeAllItems();
                assentoSelecionado = null;
                atualizarLabelAssento();
            }
        });
        frame.getContentPane().add(comboFilme);

        // --- DATA ---
        JLabel lblData = new JLabel("Selecione a data:");
        lblData.setBounds(10, 174, 146, 17);
        frame.getContentPane().add(lblData);

        comboData = new JComboBox<>();
        comboData.setBounds(173, 166, 197, 25);
        comboData.addActionListener(e -> {
            atualizarComboHorarios();
            assentoSelecionado = null;
            atualizarLabelAssento();
        });
        frame.getContentPane().add(comboData);

        // --- HORÁRIO ---
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

        // --- ASSENTO ---
        JLabel lblAssento = new JLabel("Assento selecionado:");
        lblAssento.setBounds(10, 300, 146, 17);
        frame.getContentPane().add(lblAssento);

        lblAssentoSelecionado = new JLabel("Nenhum");
        lblAssentoSelecionado.setBounds(173, 300, 100, 17);
        frame.getContentPane().add(lblAssentoSelecionado);

        JButton btnSelecionarAssento = new JButton("Selecionar Assento");
        btnSelecionarAssento.setBounds(253, 295, 150, 25);
        btnSelecionarAssento.addActionListener(e -> abrirSelecionarPoltrona());
        frame.getContentPane().add(btnSelecionarAssento);

        // --- CLIENTE ---
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

        JButton btnComprar = new JButton("Comprar Ingresso");
        btnComprar.setBounds(310, 409, 150, 30);
        btnComprar.addActionListener(e -> comprarIngresso());
        frame.getContentPane().add(btnComprar);
    }

    private void carregarFilmes() {
        filmes = filmeController.listarTodos();
        comboFilme.removeAllItems();
        for (Filme f : filmes) {
            comboFilme.addItem(f.getNome()); 
        }
    }

    private void carregarDatas(int filmeId) {
        sessoesDoFilme = sessaoController.listarPorFilme(filmeId);
        comboData.removeAllItems();
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Sessao s : sessoesDoFilme) {
            String dataFormatada = s.getInicio().toLocalDate().format(formatoData);
            boolean existe = false;
            for (int i = 0; i < comboData.getItemCount(); i++) {
                if (comboData.getItemAt(i).equals(dataFormatada)) {
                    existe = true; break;
                }
            }
            if (!existe) comboData.addItem(dataFormatada);
        }
    }

    private void atualizarComboHorarios() {
        String dataSelecionada = (String) comboData.getSelectedItem();
        comboHorario.removeAllItems();
        if (dataSelecionada == null) return;

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        for (Sessao s : sessoesDoFilme) {
            String dataSessao = s.getInicio().toLocalDate().format(formatoData);
            if (dataSessao.equals(dataSelecionada)) {
                comboHorario.addItem(s.getInicio().toLocalTime().format(formatoHora));
            }
        }
    }

    private Sessao getSessaoSelecionada() {
        String horario = (String) comboHorario.getSelectedItem();
        String data = (String) comboData.getSelectedItem();
        if (horario == null || data == null) return null;

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        for (Sessao s : sessoesDoFilme) {
            String h = s.getInicio().toLocalTime().format(formatoHora);
            String d = s.getInicio().toLocalDate().format(formatoData);
            if (h.equals(horario) && d.equals(data)) {
                return s;
            }
        }
        return null;
    }

    private void abrirSelecionarPoltrona() {
        Sessao sessao = getSessaoSelecionada();
        if (sessao == null) {
            JOptionPane.showMessageDialog(frame, "Selecione data e horário primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // [AGREGAÇÃO - List]: Retorna lista de poltronas.
        List<Poltrona> poltronas = ingressoController.listarPoltronasPorSala(sessao.getSalaId());
        
        // [ASSOCIAÇÃO]: Passa 'this' para a view filha (SelecionarPoltronaView), criando uma ligação.
        SelecionarPoltronaView selecionar = new SelecionarPoltronaView(frame, poltronas, this);
        selecionar.setVisible(true);
    }

    public void definirPoltrona(Poltrona p) {
        this.assentoSelecionado = p;
        atualizarLabelAssento();
    }

    private void atualizarLabelAssento() {
        lblAssentoSelecionado.setText(assentoSelecionado == null ? "Nenhum" : assentoSelecionado.getNumero());
    }

    private void comprarIngresso() {
        Sessao sessao = getSessaoSelecionada();
        
        if (sessao == null || assentoSelecionado == null || textNomeCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Preencha todos os dados (Sessão, Assento e Cliente)!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idIngresso = ingressoController.comprarIngresso(
            textNomeCliente.getText(),
            textEmailCliente.getText(),
            textTelefoneCliente.getText(),
            sessao.getId(),
            assentoSelecionado
        );

        if (idIngresso != -1) {
            JOptionPane.showMessageDialog(frame, "Ingresso comprado! ID: " + idIngresso, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            // Limpa seleção
            assentoSelecionado = null;
            atualizarLabelAssento();
        } else {
            JOptionPane.showMessageDialog(frame, "Erro ao realizar a compra.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}