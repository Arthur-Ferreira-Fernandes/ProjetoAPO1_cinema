package view;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import banco.DBConnection;
import cinema.ServicoController;

public class LimpezaView {

    JFrame frame;
    private JTextField txtSala;
    private JTextField txtResultado;
    private ServicoController controller;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LimpezaView window = new LimpezaView();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public LimpezaView() {
        try {
            DBConnection db = new DBConnection();
            controller = new ServicoController(db.getConnection());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco: " + e.getMessage());
            return;
        }

        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 612, 517);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JButton btnVoltarMenu = new JButton("Voltar ao Menu");
        btnVoltarMenu.setBounds(226, 162, 163, 23);
        btnVoltarMenu.addActionListener(e -> {
            frame.dispose(); // fecha a janela atual
            MainView menu = new MainView();
            menu.open(); // abre o menu principal
        });
        frame.getContentPane().add(btnVoltarMenu);

        JLabel lblSala = new JLabel("Sala:");
        lblSala.setBounds(188, 29, 46, 14);
        frame.getContentPane().add(lblSala);

        txtSala = new JTextField();
        txtSala.setBounds(255, 26, 86, 20);
        frame.getContentPane().add(txtSala);
        txtSala.setColumns(10);

        txtResultado = new JTextField();
        txtResultado.setBounds(37, 213, 542, 235);
        frame.getContentPane().add(txtResultado);
        txtResultado.setColumns(10);

        // =============================
        // BOTÕES
        // =============================
        JButton btnIniciarLimpeza = new JButton("Iniciar Limpeza");
        btnIniciarLimpeza.setBounds(37, 89, 163, 23);
        frame.getContentPane().add(btnIniciarLimpeza);

        JButton btnFinalizarLimpeza = new JButton("Finalizar Limpeza");
        btnFinalizarLimpeza.setBounds(37, 123, 163, 23);
        frame.getContentPane().add(btnFinalizarLimpeza);

        JButton btnIniciarMan = new JButton("Iniciar Manutenção");
        btnIniciarMan.setBounds(226, 89, 163, 23);
        frame.getContentPane().add(btnIniciarMan);

        JButton btnFinalizarMan = new JButton("Finalizar Manutenção");
        btnFinalizarMan.setBounds(226, 123, 163, 23);
        frame.getContentPane().add(btnFinalizarMan);

        JButton btnHistorico = new JButton("Ver Histórico");
        btnHistorico.setBounds(416, 89, 163, 23);
        frame.getContentPane().add(btnHistorico);

        JButton btnStatus = new JButton("Ver Status da Sala");
        btnStatus.setBounds(416, 123, 163, 23);
        frame.getContentPane().add(btnStatus);

        // =============================
        // LISTENERS
        // =============================
        btnIniciarLimpeza.addActionListener(e -> {
            try {
                int sala = Integer.parseInt(txtSala.getText());
                txtResultado.setText(controller.iniciarLimpeza(sala));
            } catch (Exception ex) {
                txtResultado.setText("Erro: " + ex.getMessage());
            }
        });

        btnFinalizarLimpeza.addActionListener(e -> {
            try {
                int sala = Integer.parseInt(txtSala.getText());
                txtResultado.setText(controller.finalizarLimpeza(sala));
            } catch (Exception ex) {
                txtResultado.setText("Erro: " + ex.getMessage());
            }
        });

        btnIniciarMan.addActionListener(e -> {
            try {
                int sala = Integer.parseInt(txtSala.getText());
                txtResultado.setText(controller.iniciarManutencao(sala));
            } catch (Exception ex) {
                txtResultado.setText("Erro: " + ex.getMessage());
            }
        });

        btnFinalizarMan.addActionListener(e -> {
            try {
                int sala = Integer.parseInt(txtSala.getText());
                txtResultado.setText(controller.finalizarManutencao(sala));
            } catch (Exception ex) {
                txtResultado.setText("Erro: " + ex.getMessage());
            }
        });

        btnStatus.addActionListener(e -> {
            try {
                int sala = Integer.parseInt(txtSala.getText());
                txtResultado.setText(controller.verStatusSala(sala));
            } catch (Exception ex) {
                txtResultado.setText("Erro: " + ex.getMessage());
            }
        });

        btnHistorico.addActionListener(e -> {
            try {
                int sala = Integer.parseInt(txtSala.getText());
                txtResultado.setText(controller.verHistorico(sala));
            } catch (Exception ex) {
                txtResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }

	public void open() {
		// TODO Auto-generated method stub
		
	}

}
