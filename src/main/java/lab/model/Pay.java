package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Pay extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 

    private JTextField cod_paymentField = new JTextField(10); 
    private JTextField priceField = new JTextField(5); 
    private JFormattedTextField dateField; 

    ConnectionProvider prov = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    Connection conn= prov.getMySQLConnection();
    Statement s;
    ResultSet r;
       

    public Pay() {
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(14, 2)); 
        JLabel codpaymentLabel = new JLabel("Codice Pagamento:"); 
        JLabel priceLabel = new JLabel("Cifra €:"); 
        JLabel dateLabel = new JLabel("Data Erogazione (2000/12/27):"); 
        
        dateFormat.setLenient(false); 
        dateField = new JFormattedTextField(dateFormat); 
        this.add(codpaymentLabel);
        this.add(cod_paymentField);
        this.add(dateLabel);
        this.add(dateField); 
        this.add(priceLabel); 
        this.add(priceField); 
        setVisible(true); 
    }
    public void callQuery() {
        try {
            s = conn.createStatement();
            s.executeUpdate(
                "INSERT INTO Stipendio (CodStipendio, DataErogazione,Cifra)"+
               "VALUES ('"+cod_paymentField.getText().toUpperCase()+"', '"+dateField.getText()
               +"', '"+priceField.getText()+"');");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
