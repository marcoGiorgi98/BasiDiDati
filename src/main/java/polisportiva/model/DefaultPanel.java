package polisportiva.model;

import javax.swing.*;
import polisportiva.db.ConnectionProvider;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class DefaultPanel extends JPanel{
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
    private JTextField cFField = new JTextField(10); 
    private JTextField nameField = new JTextField(10); 
    private JTextField surnameField = new JTextField(10); 
    private JTextField phoneField = new JTextField(10); 
    private JTextField viaField = new JTextField(20);
    private JTextField cityField = new JTextField(10);
    private JTextField numberField = new JTextField(10); 
    private JTextField capField = new JTextField(5); 
    private JTextField driverField = new JTextField(10); 


 
    private JFormattedTextField birthField; 
    private final JLabel DriverLabel = new JLabel("Numero Patente"); 
    
    private  Connection connection;
    private Statement statement;
    
    public DefaultPanel(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        mainInterface();
    }

    private void mainInterface() {
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.setLayout(new GridLayout(14, 2)); 
        this.dateFormat.setLenient(false); 
        this.birthField = new JFormattedTextField(dateFormat);  
        this.add(new JLabel("Codice Fiscale:"));
        this.add(cFField);
        this.add(new JLabel("Nome:")); 
        this.add(nameField); 
        this.add(new JLabel("Cognome:"));
        this.add(surnameField);
        this.add(new JLabel("Telefono:")); 
        this.add(phoneField);
        this.add(new JLabel("Data di nascita (2000/12/27):")); 
        this.add(birthField); 
        this.add(new JLabel("Via:"));
        this.add(viaField); 
        this.add(new JLabel("Numero:"));
        this.add(numberField);   
        this.add(new JLabel("CAP:"));
        this.add(cityField); 
        this.add(new JLabel("Città:"));
        this.add(capField);
        this.add(DriverLabel); 
        this.add(driverField); 
        
        
        this.driverField.setVisible(false);
        this.DriverLabel.setVisible(false);
        setVisible(true); 
    }

    public void modifyLabelsVisibility(String parameter) {
        if(parameter=="Autista") {
            driverField.setVisible(true);
            DriverLabel.setVisible(true);
        }
        else {
            driverField.setVisible(false);
            DriverLabel.setVisible(false);
        }
        
    }
      
    public void addIscritto() {
        try {
            this.statement.executeUpdate(
         "INSERT INTO iscritto (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono)"+
        "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+birthField.getText()
        +"', '"+viaField.getText()+"', '"+numberField.getText()
        +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"');");

        } catch (SQLException e) {   
        e.printStackTrace();
        }
    }

    public void addAutista() {
        try {
            this.statement.executeUpdate("INSERT INTO autista (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono, CodPatente)"+
            "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+
            birthField.getText()+"', '"+viaField.getText()+"', '"+numberField.getText()
            +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"', '"+driverField.getText()+"');");
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
       
    }

    public void addPreparatoreAllenatore(final String person) {
        try {
             this.statement.executeUpdate("INSERT INTO "+person +" (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono)"+
            "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+
            birthField.getText()+"', '"+viaField.getText()+"', '"+numberField.getText()
            +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"');");
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }
}
