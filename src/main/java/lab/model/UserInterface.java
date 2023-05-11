package lab.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface extends JFrame{
    private JButton insertDataBtn = new JButton("Inserisci nuovi dati");
    private JButton viewDataBtn = new JButton("Visualizza dati");
    private JButton closeButton = new JButton("Chiudi programma");

    public UserInterface() {
        mainInterface();
    }

    private void mainInterface() {
        setTitle("Polisportiva Bianchi&Rossi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel centerPanel = new JPanel(new FlowLayout()); // usa FlowLayout invece di GridLayout

        centerPanel.add(insertDataBtn);
        centerPanel.add(viewDataBtn);
        centerPanel.add(closeButton);
        closeButton.addActionListener(new ActionListener() { // usa una classe anonima invece di una classe separata
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        insertDataBtn.addActionListener(new ActionListener() { // aggiungi un ActionListener al bottone per aprire il nuovo panel
            public void actionPerformed(ActionEvent e) {
                InsertNewDataPanel dataPanel = new InsertNewDataPanel(getWidth(),getHeight());
            }
        });

        viewDataBtn.addActionListener(new ActionListener() { // aggiungi un ActionListener al bottone per aprire il nuovo panel
            public void actionPerformed(ActionEvent e) {
               ViewDataPanel ViewdataPanel = new ViewDataPanel();
            }
        });
        add(centerPanel, BorderLayout.CENTER);
        setSize(1000, 600);
        setVisible(true);
    }

    
}

