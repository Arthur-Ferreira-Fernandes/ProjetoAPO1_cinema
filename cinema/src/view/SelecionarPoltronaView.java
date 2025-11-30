package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import model.Poltrona;

public class SelecionarPoltronaView extends JDialog {

    private List<Poltrona> poltronas;
    private IngressoView ingressoView;

    public SelecionarPoltronaView(JFrame parent, List<Poltrona> poltronas, IngressoView ingressoView) {
        super(parent, "Selecionar Poltrona", true); // modal
        this.poltronas = poltronas;
        this.ingressoView = ingressoView;

        montarTela();
    }

    private void montarTela() {
        setSize(500, 380);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new GridLayout(0, 5, 10, 10)); // 5 colunas, espaçamento horizontal e vertical 10
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel titulo = new JLabel("Escolha sua poltrona:");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        getContentPane().add(titulo, BorderLayout.NORTH);

        for (Poltrona p : poltronas) {
            JButton btn = new JButton(p.getNumero());

            if (!p.isDisponivel()) {
                btn.setEnabled(false); // poltrona ocupada
            }

            btn.addActionListener(e -> {
                ingressoView.definirPoltrona(p); // devolve a poltrona selecionada
                dispose(); // fecha a janela
            });

            panel.add(btn);
        }
    }
}
