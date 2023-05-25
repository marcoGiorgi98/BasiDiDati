package lab.model;
import javax.swing.*;
import lab.db.ConnectionProvider;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Tesseramento extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 

    private JTextField cod_tessermentoField = new JTextField(10); 
    private JTextField categoryField = new JTextField(10); 
    private JTextField priceField = new JTextField(5); 
    private JTextField cField= new JTextField(16); 
    private JFormattedTextField dateField; 
    private  Connection connection;
    Statement statement;
    ResultSet r;
       
    public Tesseramento(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(14, 2));  
        JLabel dateLabel = new JLabel("Data Iscrizione (2000/12/27):"); 
        dateFormat.setLenient(false); 
        dateField = new JFormattedTextField(dateFormat); 
        this.add(new JLabel("Codice Tessera:"));
        this.add(cod_tessermentoField);
        this.add(new JLabel("Categoria:")); 
        this.add(categoryField); 
        this.add(dateLabel);
        this.add(dateField); 
        this.add(new JLabel("Costo €:")); 
        this.add(priceField); 
        this.add(new JLabel("CF Iscritto:")); 
        this.add(cField); 
        setVisible(true); 
      
    }
    public void callQuery() {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("Update iscritto SET CodTessera = "+"'"
            +cod_tessermentoField.getText().toUpperCase()+"' ,DataIscrizione= '"
            +dateField.getText()+"', Categoria= '"+ categoryField.getText().toUpperCase()+"' " +
            "Where CF = '"+cField.getText().toUpperCase()+"'");
            

            statement.executeUpdate(
                "INSERT INTO Tesseramento (Data, Categoria,Prezzo,CF)"+
               "VALUES ('"+dateField.getText()
               +"', '"+categoryField.getText().toUpperCase()
               +"', '"+priceField.getText()
               +"', '"+cField.getText()+"');");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
