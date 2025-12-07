package view;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import banco.DBConnection;
import controller.ServicoController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class BuscarReservaView {

    private JFrame frmBuscarReserva;
    private JTextField textFieldCodReserva;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BuscarReservaView window = new BuscarReservaView();
                    window.frmBuscarReserva.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BuscarReservaView() {
        initialize();
    }

    private void initialize() {
        frmBuscarReserva = new JFrame();
        frmBuscarReserva.setTitle("Buscar Reserva");
        frmBuscarReserva.setBounds(100, 100, 450, 300);
        frmBuscarReserva.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmBuscarReserva.getContentPane().setLayout(null);
        
        JButton btnVoltarMenu = new JButton("Voltar ao Menu");
        btnVoltarMenu.setBounds(212, 126, 120, 30);
        btnVoltarMenu.addActionListener(e -> {
            frmBuscarReserva.dispose(); 
            MainView menu = new MainView();
            menu.open(); 
        });
        frmBuscarReserva.getContentPane().add(btnVoltarMenu);

        JLabel lblCodReserva = new JLabel("Código da Reserva:");
        lblCodReserva.setBounds(68, 72, 130, 20);
        frmBuscarReserva.getContentPane().add(lblCodReserva);

        textFieldCodReserva = new JTextField();
        textFieldCodReserva.setBounds(212, 70, 120, 25);
        frmBuscarReserva.getContentPane().add(textFieldCodReserva);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // [TRATAMENTO DE ERROS]: Valida se o texto é um número inteiro.
                    int reservaId = Integer.parseInt(textFieldCodReserva.getText().trim());
                    
                    // [ASSOCIAÇÃO]: Instancia e usa ServicoController.
                    DBConnection db = new DBConnection();
                    ServicoController sc = new ServicoController(db.getConnection());
                    boolean existe = sc.existeReserva(reservaId); 
                    
                    if (!existe) {
                        JOptionPane.showMessageDialog(frmBuscarReserva,
                                "Reserva não encontrada!",
                                "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // [ASSOCIAÇÃO]: Navega para CancelarReservaView passando o ID.
                    CancelarReservaView cancelar = new CancelarReservaView(reservaId);
                    cancelar.getFrame().setVisible(true);
                    frmBuscarReserva.dispose();

                } catch (NumberFormatException ex) {
                    // [TRATAMENTO DE ERROS]: Captura erro de conversão de string para int.
                    JOptionPane.showMessageDialog(frmBuscarReserva, "Número inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmBuscarReserva, "Erro ao buscar reserva: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnBuscar.setBounds(83, 126, 120, 30);
        frmBuscarReserva.getContentPane().add(btnBuscar);
    }
    
    public JFrame getFrame() {
        return frmBuscarReserva;
    }
}