package lab.model;

import javax.swing.*;
import lab.db.ConnectionProvider;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Match extends JPanel{
    final private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
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
        final JLabel dateLabel = new JLabel("Data (2000/12/27):"); 
        this.dateFormat.setLenient(false); 
        this.dateField = new JFormattedTextField(dateFormat); 

        this.add( new JLabel("Codice Partita:"));
        this.add(cod_match_Field);
        this.add(new JLabel("Codice Squadra:")); 
        this.add(teamCodField); 
        this.add(dateLabel); 
        this.add(dateField); 
        this.add(new JLabel("Città:"));
        this.add(cityField); 
        this.add(new JLabel("Via:"));
        this.add(viaField); 
        this.add(new JLabel("CAP:"));
        this.add(capField); 
        this.add( new JLabel("Numero:"));
        this.add(numberField);  
        this.add(new JLabel("Avversario:")); 
        this.add(avversarioField);
        this.add(new JLabel("Risultato:")); 
        this.add(resultField);
        this.add(new JLabel("CF Preparatore:"));
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
