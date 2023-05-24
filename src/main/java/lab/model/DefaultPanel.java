package lab.model;

import javax.swing.*;
import lab.db.ConnectionProvider;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JTextField codTesseraField = new JTextField(10); 
    private JTextField categoryField = new JTextField(10); 
    private final JLabel tesseraLabel = new JLabel("Codice Tessera:"); 
    private JTextField paymentField = new JTextField(5); 
    private final JLabel paymentLabel = new JLabel("Cifra:"); 
    private final JLabel categoriaLabel = new JLabel("Categoria:"); 
    private JFormattedTextField subscrictionField = new JFormattedTextField(dateFormat); 
    private JFormattedTextField birthField; 
   
    private final JLabel DriverLabel = new JLabel("Numero Patente"); 
    private final JLabel subscrictionLabel = new JLabel("Data Iscrizione (2000/12/27):"); 
    private ConnectionProvider provider = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    private  Connection connection;
    private Statement statement;
    
    public DefaultPanel(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        mainInterface();
    }

    private void mainInterface() {
        this.connection= provider.getMySQLConnection();
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
        this.add(tesseraLabel);
        this.add(codTesseraField);
        this.add(categoriaLabel);
        this.add(categoryField);
        this.add(subscrictionLabel);
        this.add(subscrictionField);
        this.add(paymentLabel);
        this.add(paymentField);
        this.paymentField.setVisible(false);
        this.paymentLabel.setVisible(false);
        this.subscrictionLabel.setVisible(false);
        this.subscrictionField.setVisible(false);
        this.categoryField.setVisible(false);
        this.categoriaLabel.setVisible(false);
        this.codTesseraField.setVisible(false);
        this.tesseraLabel.setVisible(false);
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
        if(parameter=="Iscritto") {
        subscrictionLabel.setVisible(true);
        subscrictionField.setVisible(true);
        categoryField.setVisible(true);
        categoriaLabel.setVisible(true);
        codTesseraField.setVisible(true);
        tesseraLabel.setVisible(true);  
        paymentField.setVisible(true);
        paymentLabel.setVisible(true); 
        }
        else {
            subscrictionLabel.setVisible(false);
            subscrictionField.setVisible(false);
            categoryField.setVisible(false);
            categoriaLabel.setVisible(false);
            codTesseraField.setVisible(false);
            tesseraLabel.setVisible(false);   
            paymentField.setVisible(false);
            paymentLabel.setVisible(false); 
        }
    }
      
    public void addIscritto() {
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

    public void addPreparatoreAllenatore(String person) {
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
