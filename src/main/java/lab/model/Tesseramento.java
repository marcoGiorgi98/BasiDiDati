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
    Statement s;
    ResultSet r;
       
    public Tesseramento(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(14, 2)); 
        JLabel codTesseramentoLabel = new JLabel("Codice Tessera:"); 
        JLabel categoryLabel = new JLabel("Categoria:"); 
        JLabel priceLabel = new JLabel("Costo €:"); 
        JLabel CFLabel = new JLabel("CF Iscritto:"); 
        JLabel dateLabel = new JLabel("Data (2000/12/27):"); 
        
        dateFormat.setLenient(false); 
        dateField = new JFormattedTextField(dateFormat); 
        this.add(codTesseramentoLabel);
        this.add(cod_tessermentoField);
        this.add(categoryLabel); 
        this.add(categoryField); 
        this.add(dateLabel);
        this.add(dateField); 
        this.add(priceLabel); 
        this.add(priceField); 
        this.add(CFLabel); 
        this.add(cField); 
        setVisible(true); 
    }
    public void callQuery() {
        try {
            s = connection.createStatement();
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
