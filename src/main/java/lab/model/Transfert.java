package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Transfert extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // crea un formato di data

    private JTextField cod_transfert_Field = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JTextField departTimeField = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JTextField arriveTimeField = new JTextField(20); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField cityField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField numberField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField capField = new JTextField(5); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField codMatchField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField targaField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField viaField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo

    private JFormattedTextField dateDeparture; // crea una textbox formattata per la data di nascita
    private JFormattedTextField dateArrive; // crea una textbox formattata per la data di nascita
    JTextField nomeSquadra = new JTextField();
    JTextField codSquadra = new JTextField();
    JPanel pannelloAllenatori = new JPanel(new GridLayout(40, 1));

    ConnectionProvider prov = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    Connection conn= prov.getMySQLConnection();
    Statement s;
    ResultSet r;
       

    public Transfert() {
        mainInterface();
    }

    private void mainInterface() {
        //setSize(1000, 600);
        this.setLayout(new GridLayout(14, 2)); // usa GridLayout con 7 righe e 2 colonne
        JLabel cod_Transfert_Label = new JLabel("Codice Trasferta:"); // crea una label per il nome
        JLabel cod_match_Label = new JLabel("Codice Partita:"); // crea una label per il nome
        JLabel departureTime_Label = new JLabel("orario di partenza:"); // crea una label per il nome
        JLabel arriveTime_Label = new JLabel("orario di arrivo:"); // crea una label per il nome
        JLabel departureDateLabel = new JLabel("Data di Partenza (2000/12/27):"); // crea una label per la data di nascita
        JLabel arriveDateLabel = new JLabel("Data d'arrivo' (2000/12/28):"); // crea una label per la data di nascita
        JLabel addressLabel1 = new JLabel("Via:"); // crea una label per l'indirizzo
        JLabel addressLabel2 = new JLabel("Numero:"); // crea una label per l'indirizzo
        JLabel addressLabel4 = new JLabel("Città:"); // crea una label per l'indirizzo
        JLabel addressLabel3 = new JLabel("CAP:"); // crea una label per l'indirizzo
        JLabel targaLabel = new JLabel("Targa Mezzo:"); // crea una label per l'indirizzo
        
        
        
        dateFormat.setLenient(false); // rendi il formato non tollerante alle date non valide
        dateDeparture = new JFormattedTextField(dateFormat); // crea la textbox formattata con il formato di data
        dateArrive = new JFormattedTextField(dateFormat); // crea la textbox formattata con il formato di data

        this.add( cod_Transfert_Label);
        this.add(cod_transfert_Field);
        this.add(cod_match_Label);
        this.add(codMatchField);
        this.add(departureTime_Label); // aggiungi la label del nome al panel nella prima cella
        this.add(departTimeField); // aggiungi la textbox del nome al panel nella seconda cella
        this.add(arriveTime_Label); // aggiungi la label del nome al panel nella prima cella
        this.add(arriveTimeField); // aggiungi la textbox del nome al panel nella seconda cella
        this.add(departureDateLabel); // aggiungi la label della data di nascita al panel nella nona cella
        this.add(dateDeparture); // aggiungi la textbox della data di nascita al panel nella decima cella
        this.add(arriveDateLabel); // aggiungi la label della data di nascita al panel nella nona cella
        this.add(dateArrive); // aggiungi la textbox della data di nascita al panel nella decima cella
        this.add(addressLabel4); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(cityField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        this.add(addressLabel1); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(viaField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        this.add(addressLabel3); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(capField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        this.add(addressLabel2); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(numberField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella  
       
        this.add(targaLabel); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(targaField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        setVisible(true); // mostra il frame
    }
    public void callQuery() {
        try {
            s = conn.createStatement();
            s.executeUpdate(
                "INSERT INTO Trasferta (CodTrasferta,OraArrivo,OraPartenza,DataPartenza,DataArrivo, Città, Via,Cap, Numero,CodPartita,Targa)"+
               "VALUES ('"+cod_transfert_Field.getText().toUpperCase()+"', '"+arriveTimeField.getText()+"', '"+ departTimeField.getText()
               +"', '"+dateDeparture.getText()
               +"', '"+dateArrive.getText()+"', '"+cityField.getText()
               +"', '"+viaField.getText()+"', '"+capField.getText()
               +"', '"+numberField.getText()+"', '"+codMatchField.getText().toUpperCase()+"', '"+targaField.getText().toUpperCase()+"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
