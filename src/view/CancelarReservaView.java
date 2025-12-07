package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

// [MUDANÇA]: Agora usa IngressoController
import controller.IngressoController;

public class CancelarReservaView {

    private JFrame frmCancelarReserva;
    private int reservaId;
    
    // [COMPOSIÇÃO]: Controller gerenciado por esta View.
    private IngressoController controller;

    public CancelarReservaView(int reservaId) {
        this.reservaId = reservaId;
        this.controller = new IngressoController();
        initialize();
    }

    public JFrame getFrame() {
        return frmCancelarReserva;
    }

    private void initialize() {
        frmCancelarReserva = new JFrame();
        frmCancelarReserva.setTitle("Cancelar Reserva");
        frmCancelarReserva.setBounds(100, 100, 450, 300);
        frmCancelarReserva.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmCancelarReserva.getContentPane().setLayout(null);
        
        JLabel lblInfo = new JLabel("Cancelar reserva: " + reservaId);
        lblInfo.setBounds(107, 65, 250, 20);
        frmCancelarReserva.getContentPane().add(lblInfo);

        JButton btnVoltarMenu = new JButton("Voltar ao Menu");
        btnVoltarMenu.setBounds(147, 160, 140, 30);
        btnVoltarMenu.addActionListener(e -> {
            MainView menu = new MainView();
            menu.open(); 
            frmCancelarReserva.dispose();
        });
        frmCancelarReserva.getContentPane().add(btnVoltarMenu);

        JButton btnCancelarReserva = new JButton("Cancelar");
        btnCancelarReserva.setBounds(147, 115, 140, 30);
        btnCancelarReserva.addActionListener(e -> realizarCancelamento());
        frmCancelarReserva.getContentPane().add(btnCancelarReserva);
    }
    
    private void realizarCancelamento() {
        int confirm = JOptionPane.showConfirmDialog(frmCancelarReserva,
                "Deseja realmente cancelar a reserva " + reservaId + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = controller.cancelarReserva(reservaId);
            
            JOptionPane.showMessageDialog(frmCancelarReserva, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
            
            // Volta ao menu após cancelar
            frmCancelarReserva.dispose();
            new MainView().open();
        }
    }
}