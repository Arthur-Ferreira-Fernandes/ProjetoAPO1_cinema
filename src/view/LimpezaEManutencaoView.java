package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import controller.LimpezaController;
import controller.ManutencaoController;
import controller.SalaController; // [NOVO IMPORT]

public class LimpezaEManutencaoView {

    private JFrame frame;
    private JTextField txtSala;
    private JTextArea txtResultado;
    
    // [COMPOSIÇÃO]: Controllers são partes integrantes desta View (instanciados no construtor).
    private LimpezaController limpezaController;
    private ManutencaoController manutencaoController;
    private SalaController salaController; // [NOVO CONTROLADOR]

    // [MÉTODOS ESTÁTICOS]: Método main.
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LimpezaEManutencaoView window = new LimpezaEManutencaoView();
                window.frame.setVisible(true);
            } catch (Exception e) {
                // [TRATAMENTO DE ERROS E EXCEÇÕES]: Captura genérica de erros de inicialização.
                e.printStackTrace();
            }
        });
    }

    public LimpezaEManutencaoView() {
        this.limpezaController = new LimpezaController();
        this.manutencaoController = new ManutencaoController();
        this.salaController = new SalaController(); // [INICIALIZAÇÃO]
        initialize();
    }

    public JFrame getFrame() {
        return frame;
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Gestão de Limpeza e Manutenção");
        frame.setBounds(100, 100, 612, 517);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JButton btnVoltarMenu = new JButton("Voltar ao Menu");
        btnVoltarMenu.setBounds(226, 162, 163, 23);
        btnVoltarMenu.addActionListener(e -> {
            frame.dispose();
            MainView menu = new MainView();
            menu.open();
        });
        frame.getContentPane().add(btnVoltarMenu);

        JLabel lblSala = new JLabel("Sala:");
        lblSala.setBounds(188, 29, 46, 14);
        frame.getContentPane().add(lblSala);

        txtSala = new JTextField();
        txtSala.setBounds(255, 26, 86, 20);
        frame.getContentPane().add(txtSala);
        txtSala.setColumns(10);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(txtResultado);
        scrollPane.setBounds(37, 213, 542, 235);
        frame.getContentPane().add(scrollPane);

        // --- BOTÕES ---
        JButton btnIniciarLimpeza = new JButton("Iniciar Limpeza");
        btnIniciarLimpeza.setBounds(37, 89, 163, 23);
        btnIniciarLimpeza.addActionListener(e -> executarAcao(1));
        frame.getContentPane().add(btnIniciarLimpeza);

        JButton btnFinalizarLimpeza = new JButton("Finalizar Limpeza");
        btnFinalizarLimpeza.setBounds(37, 123, 163, 23);
        btnFinalizarLimpeza.addActionListener(e -> executarAcao(2));
        frame.getContentPane().add(btnFinalizarLimpeza);

        JButton btnIniciarMan = new JButton("Iniciar Manutenção");
        btnIniciarMan.setBounds(226, 89, 163, 23);
        btnIniciarMan.addActionListener(e -> executarAcao(3));
        frame.getContentPane().add(btnIniciarMan);

        JButton btnFinalizarMan = new JButton("Finalizar Manutenção");
        btnFinalizarMan.setBounds(226, 123, 163, 23);
        btnFinalizarMan.addActionListener(e -> executarAcao(4));
        frame.getContentPane().add(btnFinalizarMan);

        JButton btnHistorico = new JButton("Ver Histórico Completo");
        btnHistorico.setBounds(416, 89, 163, 23);
        btnHistorico.addActionListener(e -> executarAcao(5));
        frame.getContentPane().add(btnHistorico);

        // [MUDANÇA]: Botão alterado para verificar status geral da sala
        JButton btnStatus = new JButton("Verificar Disponibilidade");
        btnStatus.setBounds(416, 123, 163, 23);
        btnStatus.addActionListener(e -> executarAcao(6));
        frame.getContentPane().add(btnStatus);
    }

    private void executarAcao(int tipoAcao) {
        String inputSala = txtSala.getText().trim();
        if (inputSala.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, digite o número da sala.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // [TRATAMENTO DE ERROS E EXCEÇÕES]: Bloco try-catch para tratar input numérico inválido e erros gerais.
        try {
            int salaId = Integer.parseInt(inputSala);
            String resultado = "";

            // [CÓDIGO DE ACESSO AO BANCO DE DADOS (Indireto)]: Todas as ações do switch chamam Controllers que acessam o banco.
            switch (tipoAcao) {
                case 1: 
                    resultado = limpezaController.iniciarLimpeza(salaId);
                    break;
                case 2: 
                    resultado = limpezaController.finalizarLimpeza(salaId);
                    break;
                case 3: 
                    resultado = manutencaoController.iniciarManutencao(salaId);
                    break;
                case 4: 
                    resultado = manutencaoController.finalizarManutencao(salaId);
                    break;
                case 5: 
                    String histManut = manutencaoController.verHistorico(salaId);
                    String histLimp = limpezaController.verHistorico(salaId);
                    resultado = "============================================\n" +
                                "           HISTÓRICO DE MANUTENÇÃO          \n" +
                                "============================================\n" +
                                histManut + "\n\n" +
                                "============================================\n" +
                                "            HISTÓRICO DE LIMPEZA            \n" +
                                "============================================\n" +
                                histLimp;
                    break;
                    
                // [MUDANÇA]: Chama o SalaController para ver disponibilidade
                case 6: 
                    resultado = "--- Verificação de Sala ---\n" + salaController.verificarStatusSala(salaId);
                    break;
            }

            txtResultado.setText(resultado);
            txtResultado.setCaretPosition(0);

        } catch (NumberFormatException ex) {
            // [TRATAMENTO DE ERROS]: Especifico para conversão de String para int.
            JOptionPane.showMessageDialog(frame, "O número da sala deve ser um valor inteiro válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // [TRATAMENTO DE ERROS]: Genérico para qualquer outra falha (ex: falha de conexão SQL propagada).
            txtResultado.setText("Erro inesperado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}