package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class InsertNewDataPanel extends JFrame {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
    private JComboBox<String> comboBox = new JComboBox<>();
    private JTextField cFField = new JTextField(10); 
    private JTextField nameField = new JTextField(10); 
    private JTextField surnameField = new JTextField(10); 
    private JTextField phoneField = new JTextField(10); 
    private JTextField viaField = new JTextField(20);
    private JTextField cityField = new JTextField(10);
    private JTextField numberField = new JTextField(10); 
    private JTextField capField = new JTextField(5); 
    private JTextField driverField = new JTextField(10); 
    private JTextField codTesseraField = new JTextField(10); 
    private JTextField categoryField = new JTextField(10); 
    private final JLabel tesseraLabel = new JLabel("Codice Tessera"); // 
    private final JLabel categoriaLabel = new JLabel("Categoria"); // 
    private JFormattedTextField subscrictionField = new JFormattedTextField(dateFormat); 
    private JFormattedTextField birthField; 
    private final JButton button = new JButton("Invia"); 
    private final JLabel DriverLabel = new JLabel("Numero Patente"); // 
    private final JLabel subscrictionLabel = new JLabel("Data Iscrizione (2000/12/27):"); 
    private Match match;
    private Transfert transfert;
    private Training training;
    private Tesseramento tesserament;
    private Pay payment;
    private Team team;
    private JPanel panel = new JPanel(); 
    private JPanel upperPanel= new JPanel();
    private ConnectionProvider provider = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    private  Connection connection;
    private Statement statement;
       

    public InsertNewDataPanel(int width, int height) {
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
        mainInterface(width, height);
    }

    private void mainInterface(int width, int height) {
        setTitle("Inserisci nuovi Dati");
        this.connection= provider.getMySQLConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.panel.setLayout(new GridLayout(14, 2)); 
        this.upperPanel.setLayout(new GridLayout(1, 2));
        this.dateFormat.setLenient(false); 
        this.birthField = new JFormattedTextField(dateFormat); 
        this.comboBox.addItem("Allenatore");
        this.comboBox.addItem("Iscritto"); 
        this.comboBox.addItem("Autista"); 
        this.comboBox.addItem("Preparatore"); 
        this.comboBox.addItem("Squadra"); 
        this.comboBox.addItem("Partita"); 
        this.comboBox.addItem("Trasferta");
        this.comboBox.addItem("Allenamento"); 
        this.comboBox.addItem("Tesseramento"); 
        this.comboBox.addItem("Pagamento");
        this.comboBox.setEditable(false); 
        this.upperPanel.add(new JLabel("Figura da inserire"));
        this.upperPanel.add(comboBox); 
        this.panel.add(new JLabel("Codice Fiscale:"));
        this.panel.add(cFField);
        this.panel.add(new JLabel("Nome:")); 
        this.panel.add(nameField); 
        this.panel.add(new JLabel("Cognome:"));
        this.panel.add(surnameField);
        this.panel.add(new JLabel("Telefono:")); 
        this.panel.add(phoneField);
        this.panel.add(new JLabel("Data di nascita (2000/12/27):")); 
        this.panel.add(birthField); 
        this.panel.add(new JLabel("Via:"));
        this.panel.add(viaField); 
        this.panel.add(new JLabel("Numero:"));
        this.panel.add(numberField);   
        this.panel.add(new JLabel("CAP:"));
        this.panel.add(cityField); 
        this.panel.add(new JLabel("Città:"));
        this.panel.add(capField);
        this.panel.add(DriverLabel); 
        this.panel.add(driverField); 
        this.panel.add(tesseraLabel);
        this.panel.add(codTesseraField);
        this.panel.add(categoriaLabel);
        this.panel.add(categoryField);
        this.panel.add(subscrictionLabel);
        this.panel.add(subscrictionField);
        this.subscrictionLabel.setVisible(false);
        this.subscrictionField.setVisible(false);
        this.categoryField.setVisible(false);
        this.categoriaLabel.setVisible(false);
        this.codTesseraField.setVisible(false);
        this.tesseraLabel.setVisible(false);
        this.driverField.setVisible(false);
        this.DriverLabel.setVisible(false);
        add(panel, BorderLayout.CENTER); 
        add(upperPanel, BorderLayout.NORTH); 
        add(button, BorderLayout.SOUTH); 
        pack(); 
        setVisible(true); 
    }

    public void insertNewData() {
        final  String selectedTable = comboBox.getSelectedItem().toString();
        switch (selectedTable) {
            case "Autista": addAutista() ;break;
            case "Iscritto": addIscritto(); ;break;
            case "Squadra": team.callQuery() ;break;
            case "Partita": match.callQuery() ;break;
            case "Trasferta": transfert.callQuery() ;break;
            case "Allenamento": training.callQuery() ;break;
            case "Tesseramento": tesserament.callQuery() ;break;
            case "Pagamento": payment.callQuery() ;break;
            default:  addPreparatoreAllenatore(selectedTable) ;
                break;
        }
        
    }

    private void addIscritto() {
        try {
            this.statement.executeUpdate(
         "INSERT INTO iscritto (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono,Categoria,DataIscrizione,CodTessera)"+
        "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+birthField.getText()
        +"', '"+viaField.getText()+"', '"+numberField.getText()
        +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"', '"+categoryField.getText().toUpperCase()
        +"', '"+subscrictionField.getText()+"', '"+codTesseraField.getText().toUpperCase()+"');");

        } catch (SQLException e) {
        e.printStackTrace();
        }
    }

    private void addAutista() {
        try {
            this.statement.executeUpdate("INSERT INTO autista (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono, CodPatente)"+
            "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+
            birthField.getText()+"', '"+viaField.getText()+"', '"+numberField.getText()
            +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"', '"+driverField.getText()+"');");
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
       
    }

    private void addPreparatoreAllenatore(String person) {
        try {
             this.statement.executeUpdate("INSERT INTO "+person +" (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono)"+
            "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+
            birthField.getText()+"', '"+viaField.getText()+"', '"+numberField.getText()
            +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"');");
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
       
    }

    private void checkComboBoxInput() {
        if(comboBox.getSelectedItem()=="Autista") {
            driverField.setVisible(true);
            DriverLabel.setVisible(true);
           
        }
        else {
            driverField.setVisible(false);
            DriverLabel.setVisible(false);
        }
        if(comboBox.getSelectedItem()=="Iscritto") {
        subscrictionLabel.setVisible(true);
        subscrictionField.setVisible(true);
        categoryField.setVisible(true);
        categoriaLabel.setVisible(true);
        codTesseraField.setVisible(true);
        tesseraLabel.setVisible(true);   
        }
        else {
            subscrictionLabel.setVisible(false);
            subscrictionField.setVisible(false);
            categoryField.setVisible(false);
            categoriaLabel.setVisible(false);
            codTesseraField.setVisible(false);
            tesseraLabel.setVisible(false);   
        }

        switch (comboBox.getSelectedItem().toString()) {
            case "Partita": this.showMatchPanel();break;
            case "Trasferta": this.showTransfertPanel();break;
            case "Allenamento": this.showTrainingPanel();break;
            case "Tesseramento": this.showTesseramentPanel();break;
            case "Pagamento": this.showPaymentPanel();break;
            case "Squadra": this.showTeamPanel();break;
            default:
                break;
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
