package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Training extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // crea un formato di data

    private JTextField cod_training_Field = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JFormattedTextField dateField; // crea una textbox formattata per la data di nascita
    
    JPanel pannelloAllenatori = new JPanel(new GridLayout(40, 1));

    ConnectionProvider prov = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    Connection conn= prov.getMySQLConnection();
    Statement s;
    ResultSet r;
       

    public Training() {
        mainInterface();
    }

    private void mainInterface() {
        //setSize(1000, 600);
        this.setLayout(new GridLayout(14, 2)); // usa GridLayout con 7 righe e 2 colonne
        JLabel cod_trainign_Label = new JLabel("Codice Allenamento:"); // crea una label per il nome
        JLabel trainDateLabel = new JLabel("Data Allenamento (2000/01/29):"); // crea una label per il nome
        dateFormat.setLenient(false); // rendi il formato non tollerante alle date non valide
        dateField = new JFormattedTextField(dateFormat); // crea la textbox formattata con il formato di data

        this.add( cod_trainign_Label);
        this.add(cod_training_Field);
        this.add(trainDateLabel); // aggiungi la label del nome al panel nella prima cella
        this.add(dateField); // aggiungi la textbox del nome al panel nella seconda cella
        
        setVisible(true); // mostra il frame
    }
    public void callQuery() {
        try {
            s = conn.createStatement();
            s.executeUpdate(
                "INSERT INTO allenamento (CodAllenamento, Data)"+
               "VALUES ('"+cod_training_Field.getText().toUpperCase()+"', '"+dateField.getText()+"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
