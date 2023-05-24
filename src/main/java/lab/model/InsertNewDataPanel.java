package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class InsertNewDataPanel extends JFrame {
    private JComboBox<String> comboBox = new JComboBox<>();
    private final JButton button = new JButton("Aggiungi Dati"); 
    private Match match;
    private DefaultPanel defaultPanel;
    private Transfert transfert;
    private Training training;
    private Tesseramento tesserament;
    private Pay payment;
    private Team team;
    private JPanel panel = new JPanel(); 
    private JPanel upperPanel= new JPanel();
    private ConnectionProvider provider = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
       
    public InsertNewDataPanel() {
        this.setSize(400, 700);
        mainInterface();
        button.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                insertNewData();
            }
        });

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                checkComboBoxInput();
            }
            
        });
    }

    private void mainInterface() {
        setTitle("Inserisci nuovi Dati");
        this.panel.setLayout(new GridLayout(14, 2)); 
        this.upperPanel.setLayout(new GridLayout(1, 2));
        this.comboBox.addItem("Partita"); 
        this.comboBox.addItem("Allenatore");
        this.comboBox.addItem("Iscritto"); 
        this.comboBox.addItem("Autista"); 
        this.comboBox.addItem("Preparatore"); 
        this.comboBox.addItem("Squadra"); 
        this.comboBox.addItem("Trasferta");
        this.comboBox.addItem("Allenamento"); 
        this.comboBox.addItem("Tesseramento"); 
        this.comboBox.addItem("Pagamento");
        this.comboBox.setEditable(false); 
        this.upperPanel.add(new JLabel("Figura da inserire"));
        this.upperPanel.add(comboBox); 
        
        add(panel, BorderLayout.CENTER); 
        add(upperPanel, BorderLayout.NORTH); 
        add(button, BorderLayout.SOUTH); 
        //pack(); 
        setVisible(true); 
    }

    public void insertNewData() {
        final  String selectedTable = comboBox.getSelectedItem().toString();
        switch (selectedTable) {
            case "Autista": defaultPanel.addAutista() ;break;
            case "Iscritto": defaultPanel.addIscritto(); ;break;
            case "Squadra": team.callQuery() ;break;
            case "Partita": match.callQuery() ;break;
            case "Trasferta": transfert.callQuery() ;break;
            case "Allenamento": training.callQuery() ;break;
            case "Tesseramento": tesserament.callQuery() ;break;
            case "Pagamento": payment.callQuery() ;break;
            default:  defaultPanel.addPreparatoreAllenatore(selectedTable) ;
                break;
        }
        
    }

    private void checkComboBoxInput() {
        switch (comboBox.getSelectedItem().toString()) {
            case "Partita": this.showMatchPanel();break;
            case "Trasferta": this.showTransfertPanel();break;
            case "Allenamento": this.showTrainingPanel();break;
            case "Tesseramento": this.showTesseramentPanel();break;
            case "Pagamento": this.showPaymentPanel();break;
            case "Squadra": this.showTeamPanel();break;
            default:this.showDefaultPanel() ;break;
                
        }
    }
   
    private void updatePanel() {
        revalidate();
        repaint();
    }

    private void showMatchPanel() {
        var centralPanel = this.getContentPane().getComponentAt(this.getContentPane().getWidth()/2,this.getContentPane().getHeight()/2);
        this.remove(centralPanel);
        match = new Match(this.provider);
        add(match, BorderLayout.CENTER); 
        this.updatePanel();
    }
    private void showDefaultPanel() {
        var centralPanel = this.getContentPane().getComponentAt(this.getContentPane().getWidth()/2,this.getContentPane().getHeight()/2);
        this.remove(centralPanel);
        defaultPanel = new DefaultPanel(this.provider);
        defaultPanel.modifyLabelsVisibility(comboBox.getSelectedItem().toString());
        add(defaultPanel, BorderLayout.CENTER); 
        this.updatePanel();
    }
    private void showTrainingPanel() {
        var centralPanel = this.getContentPane().getComponentAt(this.getContentPane().getWidth()/2,this.getContentPane().getHeight()/2);
        this.remove(centralPanel);
        training = new Training(this.provider);
        add( training, BorderLayout.CENTER); 
        this.updatePanel();
    }
    private void showTesseramentPanel() {
        var centralPanel = this.getContentPane().getComponentAt(this.getContentPane().getWidth()/2,this.getContentPane().getHeight()/2);
        this.remove(centralPanel);
        tesserament = new Tesseramento(this.provider);
        add(tesserament, BorderLayout.CENTER); 
        this.updatePanel();
    }
    private void showTransfertPanel() {
        var centralPanel = this.getContentPane().getComponentAt(this.getContentPane().getWidth()/2,this.getContentPane().getHeight()/2);
        this.remove(centralPanel);
        transfert = new Transfert(this.provider);
        add(transfert, BorderLayout.CENTER); 
        this.updatePanel();
    }
    private void showPaymentPanel() {
      
        var centralPanel = this.getContentPane().getComponentAt(this.getContentPane().getWidth()/2,this.getContentPane().getHeight()/2);
        this.remove(centralPanel);
        payment = new Pay(this.provider);
        add( payment, BorderLayout.CENTER);
        this.updatePanel();
    }
    private void showTeamPanel() {
        var centralPanel = this.getContentPane().getComponentAt(this.getContentPane().getWidth()/2,this.getContentPane().getHeight()/2);
        this.remove(centralPanel);
        team = new Team(this.provider);
        add(team, BorderLayout.CENTER); 
        this.updatePanel();
    }
}
