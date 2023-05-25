package polisportiva.model;

import javax.swing.*;

import polisportiva.db.ConnectionProvider;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Transfert extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
    private JTextField cod_transfert_Field = new JTextField(10); 
    private JTextField departTimeField = new JTextField(10); 
    private JTextField arriveTimeField = new JTextField(20); 
    private JTextField cityField = new JTextField(10); 
    private JTextField numberField = new JTextField(10); 
    private JTextField capField = new JTextField(5); 
    private JTextField codMatchField = new JTextField(10);
    private JTextField targaField = new JTextField(10);
    private JTextField viaField = new JTextField(10); 
    private JFormattedTextField dateDeparture; 
    private JFormattedTextField dateArrive; 
    private Connection connection;
    private Statement statement;
       
    public Transfert(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(14, 2)); 
        JLabel departureDateLabel = new JLabel("Data di Partenza (2000/12/27):"); 
        JLabel arriveDateLabel = new JLabel("Data d'arrivo' (2000/12/28):"); 
        this.dateFormat.setLenient(false); 
        this.dateDeparture = new JFormattedTextField(dateFormat); 
        this.dateArrive = new JFormattedTextField(dateFormat); 
        this.add( new JLabel("Codice Trasferta:"));
        this.add(cod_transfert_Field);
        this.add(new JLabel("Codice Partita:"));
        this.add(codMatchField);
        this.add(new JLabel("orario di partenza:"));
        this.add(departTimeField); 
        this.add(new JLabel("orario di arrivo:")); 
        this.add(arriveTimeField); 
        this.add(departureDateLabel);
        this.add(dateDeparture); 
        this.add(arriveDateLabel); 
        this.add(dateArrive); 
        this.add(new JLabel("Città:")); 
        this.add(cityField); 
        this.add(new JLabel("Via:")); 
        this.add(viaField); 
        this.add(new JLabel("CAP:")); 
        this.add(capField); 
        this.add(new JLabel("Numero:")); 
        this.add(numberField); 
        this.add(new JLabel("Targa Mezzo:")); 
        this.add(targaField); 
        setVisible(true); 
    }
    public void callQuery() {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(
                "INSERT INTO Trasferta (CodTrasferta,OraArrivo,OraPartenza,DataPartenza,DataArrivo, Città, Via,Cap, Numero,CodPartita,Targa)"+
               "VALUES ('"+cod_transfert_Field.getText().toUpperCase()+"', '"+arriveTimeField.getText()+"', '"+ departTimeField.getText()
               +"', '"+dateDeparture.getText()
               +"', '"+dateArrive.getText()+"', '"+cityField.getText()
               +"', '"+viaField.getText()+"', '"+capField.getText()
               +"', '"+numberField.getText()+"', '"+codMatchField.getText().toUpperCase()+"', '"+targaField.getText().toUpperCase()+"');");
               statement.close();
               connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
