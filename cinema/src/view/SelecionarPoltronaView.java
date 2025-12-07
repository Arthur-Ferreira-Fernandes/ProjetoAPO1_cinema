package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import model.Poltrona;

// [HERANÇA]: Herda de JDialog para funcionar como janela modal.
public class SelecionarPoltronaView extends JDialog {

    // [AGREGAÇÃO - LIST]: Recebe lista de poltronas da sala.
    private List<Poltrona> poltronas;
    
    // [ASSOCIAÇÃO]: Referência à View pai para devolver o dado.
    private IngressoView ingressoView;

    public SelecionarPoltronaView(JFrame parent, List<Poltrona> poltronas, IngressoView ingressoView) {
        super(parent, "Selecionar Poltrona", true); // Configura modal
        this.poltronas = poltronas;
        this.ingressoView = ingressoView;

        montarTela();
    }

    private void montarTela() {
        setSize(500, 380);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new GridLayout(0, 5, 10, 10)); // Layout de Grade
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel titulo = new JLabel("Escolha sua poltrona:");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        getContentPane().add(titulo, BorderLayout.NORTH);

        for (Poltrona p : poltronas) {
            JButton btn = new JButton(p.getNumero());

            // Verifica estado do modelo para alterar a View
            if (!p.isDisponivel()) {
                btn.setEnabled(false); // poltrona ocupada
            }

            btn.addActionListener(e -> {
                // [ASSOCIAÇÃO]: Devolve a poltrona selecionada via método da View pai
                ingressoView.definirPoltrona(p); 
                dispose(); // fecha a janela
            });

            panel.add(btn);
        }
    }
}