package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainView {

    // [COMPOSIÇÃO]: A classe 'tem um' JFrame que é criado na inicialização.
    private JFrame frmMenuPrincipal;

    // [MÉTODO ESTÁTICO]: Ponto de entrada da aplicação (Entry Point).
    // Pertence à classe, não à instância.
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // [TRATAMENTO DE ERROS E EXCEÇÕES]: Protege a inicialização da thread gráfica
                try {
                    MainView window = new MainView();
                    window.frmMenuPrincipal.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainView() {
        initialize();
    }

    private void initialize() {
        frmMenuPrincipal = new JFrame();
        frmMenuPrincipal.setTitle("Menu Principal");
        frmMenuPrincipal.setBounds(100, 100, 334, 265);
        frmMenuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMenuPrincipal.getContentPane().setLayout(null);
        
        JButton btnComprarIngresso = new JButton("Comprar Ingresso");
        btnComprarIngresso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // [ASSOCIAÇÃO]: MainView cria e usa IngressoView (Navegação).
                IngressoView IngressoView = new IngressoView();
                IngressoView.frame.setVisible(true);
                
                // Fecha o menu principal para liberar recursos visuais
                frmMenuPrincipal.dispose();
            }
        });
        btnComprarIngresso.setBounds(80, 57, 158, 23);
        frmMenuPrincipal.getContentPane().add(btnComprarIngresso);
        
        JButton btnBuscarReserva = new JButton("Buscar Ingresso");
        btnBuscarReserva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // [ASSOCIAÇÃO]: Navegação para BuscarReservaView.
                BuscarReservaView buscarReservaView = new BuscarReservaView();
                buscarReservaView.getFrame().setVisible(true);
                frmMenuPrincipal.dispose();
            }
        });
        btnBuscarReserva.setBounds(80, 91, 158, 23);
        frmMenuPrincipal.getContentPane().add(btnBuscarReserva);
        
        JButton btnFazerManutencao = new JButton("Fazer Manutenção");
        btnFazerManutencao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 // [ASSOCIAÇÃO]: Navegação para LimpezaEManutencaoView.
                 LimpezaEManutencaoView limpezaViewS = new LimpezaEManutencaoView();
                 limpezaViewS.frame.setVisible(true);
                 frmMenuPrincipal.dispose();
            }
        });
        btnFazerManutencao.setBounds(80, 125, 158, 23);
        frmMenuPrincipal.getContentPane().add(btnFazerManutencao);
    }
    
    public void open() {
        frmMenuPrincipal.setVisible(true);
    }
}