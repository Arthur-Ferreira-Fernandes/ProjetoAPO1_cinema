package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainView {

	private JFrame frmMenuPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frmMenuPrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMenuPrincipal = new JFrame();
		frmMenuPrincipal.setTitle("Menu Principal");
		frmMenuPrincipal.setBounds(100, 100, 334, 265);
		frmMenuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMenuPrincipal.getContentPane().setLayout(null);
		
		JButton btnComprarIngresso = new JButton("Comprar Ingresso");
		btnComprarIngresso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        IngressoView IngressoView = new IngressoView();
		        IngressoView.frame.setVisible(true);
		        frmMenuPrincipal.dispose();
		    }
		});
		btnComprarIngresso.setBounds(80, 57, 158, 23);
		frmMenuPrincipal.getContentPane().add(btnComprarIngresso);
		
		JButton btnBuscarReserva = new JButton("Buscar Ingresso");
		btnBuscarReserva.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
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
		    	 LimpezaView limpezaViewS = new LimpezaView();
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
