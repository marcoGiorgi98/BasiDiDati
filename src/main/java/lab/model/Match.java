package lab.model;

import javax.swing.*;
import lab.db.ConnectionProvider;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Match extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 

    private JTextField cod_match_Field = new JTextField(10); 
    private JTextField teamCodField = new JTextField(10); 
    private JTextField viaField = new JTextField(20); 
    private JTextField cityField = new JTextField(10); 
    private JTextField numberField = new JTextField(10);
    private JTextField capField = new JTextField(5); 
    private JTextField resultField = new JTextField(10);
    private JTextField cf_PreparatoreField = new JTextField(10);
    private JTextField avversarioField = new JTextField(10); 
    private JFormattedTextField dateField; 
    private Connection connection;
    private Statement statement;
       
    public Match(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(14, 2)); 
        final JLabel cod_match_Label = new JLabel("Codice Partita:");
        final JLabel teamCodLabel = new JLabel("Codice Squadra:"); 
        final JLabel addressLabel1 = new JLabel("Via:"); 
        final JLabel addressLabel2 = new JLabel("Numero:"); 
        final JLabel addressLabel4 = new JLabel("Città:"); 
        final JLabel addressLabel3 = new JLabel("CAP:"); 
        final JLabel avversario = new JLabel("Avversario:"); 
        final JLabel result = new JLabel("Risultato:"); 
        final JLabel CF_preparatore = new JLabel("CF Preparatore:");
        final JLabel dateLabel = new JLabel("Data (2000/12/27):"); 
        
        dateFormat.setLenient(false); 
        dateField = new JFormattedTextField(dateFormat); 

        this.add( cod_match_Label);
        this.add(cod_match_Field);
        this.add(teamCodLabel); 
        this.add(teamCodField); 
        this.add(dateLabel); 
        this.add(dateField); 
        this.add(addressLabel4);
        this.add(cityField); 
        this.add(addressLabel1);
        this.add(viaField); 
        this.add(addressLabel3);
        this.add(capField); 
        this.add(addressLabel2);
        this.add(numberField);  
        this.add(avversario); 
        this.add(avversarioField);
        this.add(result); 
        this.add(resultField);
        this.add(CF_preparatore);
        this.add(cf_PreparatoreField);
        setVisible(true); 
    }

    public void callQuery() {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(
                "INSERT INTO Partita (CodPartita, CodSquadra, Data,  Città, Via,Cap, Numero,Avversario,Risultato,CF_Preparatore)"+
               "VALUES ('"+cod_match_Field.getText().toUpperCase()+"', '"+teamCodField.getText().toUpperCase()
               +"', '"+dateField.getText()
               +"', '"+cityField.getText()
               +"', '"+viaField.getText()+"', '"+capField.getText()
               +"', '"+numberField.getText()+"', '"+avversarioField.getText()
               +"', '"+resultField.getText()+"', '"+cf_PreparatoreField.getText().toUpperCase()+"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
