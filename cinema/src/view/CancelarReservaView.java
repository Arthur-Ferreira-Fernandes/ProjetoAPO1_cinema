package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import banco.DBConnection;
import controller.ServicoController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CancelarReservaView {

    private JFrame frmCancelarReserva;
    // [ATRIBUTO]: ID recebido da tela anterior.
    private int reservaId;
    private JLabel lblCancelarReserva;
    JFrame frame;

    public JFrame getFrame() {
        return frmCancelarReserva;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CancelarReservaView window = new CancelarReservaView(123);
                window.frmCancelarReserva.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // [MÉTODO COM SOBRECARGA]: Construtor customizado que recebe o ID.
    public CancelarReservaView(int reservaId) {
        this.reservaId = reservaId;
        initialize();
    }

    private void initialize() {
        frmCancelarReserva = new JFrame();
        frmCancelarReserva.setTitle("Cancelar Reserva");
        frmCancelarReserva.setBounds(100, 100, 450, 300);
        frmCancelarReserva.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmCancelarReserva.getContentPane().setLayout(null);
        
        JButton btnVoltarMenu = new JButton("Voltar ao Menu");
        btnVoltarMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainView menu = new MainView();
                menu.open(); 
                frmCancelarReserva.dispose();
            }
        });
        btnVoltarMenu.setBounds(147, 160, 140, 30);
        frmCancelarReserva.getContentPane().add(btnVoltarMenu);
        
        lblCancelarReserva = new JLabel("Cancelar reserva: " + reservaId);
        lblCancelarReserva.setBounds(107, 65, 250, 20);
        frmCancelarReserva.getContentPane().add(lblCancelarReserva);

        JButton btnCancelarReserva = new JButton("Cancelar");
        btnCancelarReserva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Diálogo de Confirmação
                int confirm = JOptionPane.showConfirmDialog(frmCancelarReserva,
                        "Deseja realmente cancelar a reserva " + reservaId + "?",
                        "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                try {
                    // [ASSOCIAÇÃO]: Instancia Controller para executar cancelamento
                    DBConnection db = new DBConnection();
                    ServicoController sc = new ServicoController(db.getConnection());
                    
                    // Chama regra de negócio
                    String msg = sc.cancelarReserva(reservaId);
                    
                    JOptionPane.showMessageDialog(frmCancelarReserva, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    frmCancelarReserva.dispose(); 
                } catch (Exception ex) {
                    // [TRATAMENTO DE ERROS]
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmCancelarReserva,
                            "Erro ao cancelar: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancelarReserva.setBounds(147, 115, 140, 30);
        frmCancelarReserva.getContentPane().add(btnCancelarReserva);
    }
}