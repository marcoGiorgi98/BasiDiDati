package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Tesseramento extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // crea un formato di data

    private JTextField cod_tessermentoField = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JTextField categoryField = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JTextField priceField = new JTextField(5); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField cField= new JTextField(16); // crea una textbox di 10 caratteri per l'indirizzo
    private JFormattedTextField dateField; // crea una textbox formattata per la data di nascita

    ConnectionProvider prov = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    Connection conn= prov.getMySQLConnection();
    Statement s;
    ResultSet r;
       

    public Tesseramento() {
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(14, 2)); // usa GridLayout con 7 righe e 2 colonne
        JLabel codTesseramentoLabel = new JLabel("Codice Tessera:"); // crea una label per il nome
        JLabel categoryLabel = new JLabel("Categoria:"); // crea una label per il nome
        JLabel priceLabel = new JLabel("Costo €:"); // crea una label per l'indirizzo
        JLabel CFLabel = new JLabel("CF Iscritto:"); // crea una label per l'indirizzo
        JLabel dateLabel = new JLabel("Data (2000/12/27):"); // crea una label per la data di nascita
        
        dateFormat.setLenient(false); // rendi il formato non tollerante alle date non valide
        dateField = new JFormattedTextField(dateFormat); // crea la textbox formattata con il formato di data
        this.add(codTesseramentoLabel);
        this.add(cod_tessermentoField);
        this.add(categoryLabel); // aggiungi la label del nome al panel nella prima cella
        this.add(categoryField); // aggiungi la textbox del nome al panel nella seconda cella
        this.add(dateLabel); // aggiungi la label della data di nascita al panel nella nona cella
        this.add(dateField); // aggiungi la textbox della data di nascita al panel nella decima cella
        this.add(priceLabel); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(priceField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        this.add(CFLabel); // aggiungi la label dell'indirizzo al panel nella settima cella
        this.add(cField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        setVisible(true); // mostra il frame
    }
    public void callQuery() {
        try {
            s = conn.createStatement();
            s.executeUpdate(
                "INSERT INTO Tesseramento (CodTesseramento, Data, Categoria,Prezzo,CF)"+
               "VALUES ('"+cod_tessermentoField.getText().toUpperCase()+"', '"+dateField.getText()
               +"', '"+categoryField.getText().toUpperCase()
               +"', '"+priceField.getText()
               +"', '"+cField.getText()+"');");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
