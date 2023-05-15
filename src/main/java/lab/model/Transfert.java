package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

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
    private Statement s;
       
    public Transfert(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(14, 2)); 
        JLabel cod_Transfert_Label = new JLabel("Codice Trasferta:"); 
        JLabel cod_match_Label = new JLabel("Codice Partita:"); 
        JLabel departureTime_Label = new JLabel("orario di partenza:"); 
        JLabel arriveTime_Label = new JLabel("orario di arrivo:"); 
        JLabel departureDateLabel = new JLabel("Data di Partenza (2000/12/27):"); 
        JLabel arriveDateLabel = new JLabel("Data d'arrivo' (2000/12/28):"); 
        JLabel addressLabel1 = new JLabel("Via:"); 
        JLabel addressLabel2 = new JLabel("Numero:"); 
        JLabel addressLabel4 = new JLabel("Città:"); 
        JLabel addressLabel3 = new JLabel("CAP:"); 
        JLabel targaLabel = new JLabel("Targa Mezzo:"); 
        this.dateFormat.setLenient(false); 
        this.dateDeparture = new JFormattedTextField(dateFormat); 
        this.dateArrive = new JFormattedTextField(dateFormat); 
        this.add( cod_Transfert_Label);
        this.add(cod_transfert_Field);
        this.add(cod_match_Label);
        this.add(codMatchField);
        this.add(departureTime_Label);
        this.add(departTimeField); 
        this.add(arriveTime_Label); 
        this.add(arriveTimeField); 
        this.add(departureDateLabel);
        this.add(dateDeparture); 
        this.add(arriveDateLabel); 
        this.add(dateArrive); 
        this.add(addressLabel4); 
        this.add(cityField); 
        this.add(addressLabel1); 
        this.add(viaField); 
        this.add(addressLabel3); 
        this.add(capField); 
        this.add(addressLabel2); 
        this.add(numberField); 
        this.add(targaLabel); 
        this.add(targaField); 
        setVisible(true); 
    }
    public void callQuery() {
        try {
            s = connection.createStatement();
            s.executeUpdate(
                "INSERT INTO Trasferta (CodTrasferta,OraArrivo,OraPartenza,DataPartenza,DataArrivo, Città, Via,Cap, Numero,CodPartita,Targa)"+
               "VALUES ('"+cod_transfert_Field.getText().toUpperCase()+"', '"+arriveTimeField.getText()+"', '"+ departTimeField.getText()
               +"', '"+dateDeparture.getText()
               +"', '"+dateArrive.getText()+"', '"+cityField.getText()
               +"', '"+viaField.getText()+"', '"+capField.getText()
               +"', '"+numberField.getText()+"', '"+codMatchField.getText().toUpperCase()+"', '"+targaField.getText().toUpperCase()+"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
