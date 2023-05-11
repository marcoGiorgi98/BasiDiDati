package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Match extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // crea un formato di data

    private JComboBox<String> comboBox = new JComboBox<>(); // crea una JComboBox
    private JTextField cod_match_Field = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JTextField teamCodField = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JTextField phoneField = new JTextField(10); // crea una textbox di 10 caratteri per il telefono
    private JTextField viaField = new JTextField(20); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField cityField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField numberField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField capField = new JTextField(5); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField ResultField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField cf_PreparatoreField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField avversarioField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo

    private JFormattedTextField dateField; // crea una textbox formattata per la data di nascita
    private JButton button = new JButton("Invia"); // crea un bottone con il testo "Invia"
    JLabel DriverLabel = new JLabel("Numero Patente"); // 
    JLabel subscrictionLabel = new JLabel("Data Iscrizione (2000/12/27):"); // 

    JComboBox<String> sportSelectionBox = new JComboBox<>();   //sport da scegliere per una squadra
    JTextField nomeSquadra = new JTextField();
    JTextField codSquadra = new JTextField();
    JPanel pannelloAllenatori = new JPanel(new GridLayout(40, 1));

    ConnectionProvider prov = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    Connection conn= prov.getMySQLConnection();
    Statement s;
    ResultSet r;
       

    public Match() {
        button.addActionListener(new ActionListener() { // aggiungi un ActionListener al bottone per aprire il nuovo panel
            public void actionPerformed(ActionEvent e) {
                //insertNewData();
            }
        });

       
        mainInterface();
    }

    private void mainInterface() {
        //setSize(1000, 600);
        this.setLayout(new GridLayout(14, 2)); // usa GridLayout con 7 righe e 2 colonne
        JLabel cod_match_Label = new JLabel("Codice Partita:"); // crea una label per il nome
        JLabel teamCodLabel = new JLabel("Codice Squadra:"); // crea una label per il nome
        JLabel selectionLabel = new JLabel("Figura da inserire"); // 
        JLabel phoneLabel = new JLabel("Telefono:"); // crea una label per il telefono
        JLabel addressLabel1 = new JLabel("Via:"); // crea una label per l'indirizzo
        JLabel addressLabel2 = new JLabel("Numero:"); // crea una label per l'indirizzo
        JLabel addressLabel4 = new JLabel("Città:"); // crea una label per l'indirizzo
        JLabel addressLabel3 = new JLabel("CAP:"); // crea una label per l'indirizzo
        JLabel avversario = new JLabel("Avversario:"); // crea una label per l'indirizzo
        JLabel result = new JLabel("Risultato:"); // crea una label per l'indirizzo
        JLabel CF_preparatore = new JLabel("CF Preparatore:"); // crea una label per l'indirizzo
        JLabel dateLabel = new JLabel("Data (2000/12/27):"); // crea una label per la data di nascita
        
        
        dateFormat.setLenient(false); // rendi il formato non tollerante alle date non valide
        dateField = new JFormattedTextField(dateFormat); // crea la textbox formattata con il formato di data
        comboBox.addItem("Allenatore"); // aggiungi la seconda opzione alla JComboBox
        comboBox.addItem("Iscritto"); // aggiungi la prima opzione alla JComboBox
        comboBox.addItem("Autista"); // aggiungi la seconda opzione alla JComboBox
        comboBox.addItem("Preparatore"); // aggiungi la seconda opzione alla JComboBox
        comboBox.addItem("Squadra"); // aggiungi la seconda opzione alla JComboBox
        comboBox.addItem("Partita"); // aggiungi la seconda opzione alla JComboBox
        comboBox.setEditable(false); // rendi la JComboBox non modificabile
        this.add(selectionLabel);
        this.add(comboBox); // aggiungi la JComboBox al panel nell'undicesima cella
        this.add( cod_match_Label);
        this.add(cod_match_Field);
        this.add(teamCodLabel); // aggiungi la label del nome al panel nella prima cella
        this.add(teamCodField); // aggiungi la textbox del nome al panel nella seconda cella
        this.add(dateLabel); // aggiungi la label della data di nascita al panel nella nona cella
        this.add(dateField); // aggiungi la textbox della data di nascita al panel nella decima cella
        this.add(addressLabel4); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(cityField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        this.add(addressLabel1); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(viaField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        this.add(addressLabel3); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(capField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        this.add(addressLabel2); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(numberField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella  
        this.add(phoneLabel); // aggiungi la label del telefono al panel nella quinta cella
        this.add(phoneField); // aggiungi la textbox del telefono al panel nella sesta cella
        this.add(avversario); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(avversarioField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        this.add(result); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(ResultField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        this.add(CF_preparatore); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(cf_PreparatoreField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
      
        //add(this, BorderLayout.CENTER); // aggiungi il panel al frame al centro
        //pack(); // imposta la dimensione del frame in base al suo contenuto
        setVisible(true); // mostra il frame
    }
}
