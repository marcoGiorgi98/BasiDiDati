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
    JTextField nomeSquadra = new JTextField();
    JTextField codSquadra = new JTextField();
    JPanel pannelloAllenatori = new JPanel(new GridLayout(40, 1));

    ConnectionProvider prov = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    Connection conn= prov.getMySQLConnection();
    Statement s;
    ResultSet r;
       

    public Match() {
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(14, 2)); 
        JLabel cod_match_Label = new JLabel("Codice Partita:");
        JLabel teamCodLabel = new JLabel("Codice Squadra:"); 
        JLabel addressLabel1 = new JLabel("Via:"); 
        JLabel addressLabel2 = new JLabel("Numero:"); 
        JLabel addressLabel4 = new JLabel("Città:"); 
        JLabel addressLabel3 = new JLabel("CAP:"); 
        JLabel avversario = new JLabel("Avversario:"); 
        JLabel result = new JLabel("Risultato:"); 
        JLabel CF_preparatore = new JLabel("CF Preparatore:");
        JLabel dateLabel = new JLabel("Data (2000/12/27):"); 
        
        
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
            s = conn.createStatement();
            s.executeUpdate(
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
