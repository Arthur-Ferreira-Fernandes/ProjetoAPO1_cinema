package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Poltrona;

public class SelecionarPoltronaView extends JDialog {

    // [AGREGAÇÃO - List]: A view recebe uma lista de poltronas para exibir, mas não "donas" delas.
    private List<Poltrona> poltronas;
    
    // [ASSOCIAÇÃO]: Referência à View pai (IngressoView) para callback de seleção.
    private IngressoView ingressoView;

    public SelecionarPoltronaView(JFrame parent, List<Poltrona> poltronas, IngressoView ingressoView) {
        super(parent, "Selecionar Poltrona", true);
        this.poltronas = poltronas;
        this.ingressoView = ingressoView;
        montarTela();
    }

    private void montarTela() {
        setSize(500, 380);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new GridLayout(0, 5, 10, 10)); 
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel titulo = new JLabel("Escolha sua poltrona:");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        getContentPane().add(titulo, BorderLayout.NORTH);

        // [ASSOCIAÇÃO]: Itera sobre objetos Poltrona.
        for (Poltrona p : poltronas) {
            JButton btn = new JButton(p.getNumero());

            if (!p.isDisponivel()) {
                btn.setEnabled(false);
                btn.setBackground(Color.RED); 
            } else {
                btn.setBackground(Color.GREEN);
            }

            btn.addActionListener(e -> {
                // [ASSOCIAÇÃO]: Comunica a escolha de volta para o método do objeto IngressoView.
                ingressoView.definirPoltrona(p);
                dispose();
            });

            panel.add(btn);
        }
    }
}