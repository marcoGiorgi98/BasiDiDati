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
    private DefaultPanel defaultPanel;
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
