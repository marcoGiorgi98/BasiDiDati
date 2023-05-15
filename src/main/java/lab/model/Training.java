package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class Training extends JPanel{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
    private JTextField cod_training_Field = new JTextField(10); 
    private JFormattedTextField dateField; 
    private JScrollPane scrollPane;
    private JPanel pannelloPlayers = new JPanel(new GridLayout(1, 1));
    private LinkedList<JCheckBox> playersList = new LinkedList<>()  ; 
    private Connection connection ;
    private Statement statement;
       
    public Training(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        this.scrollPane = new JScrollPane();
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(6, 2)); 
        final JLabel cod_trainign_Label = new JLabel("Codice Allenamento:"); 
        final JLabel trainDateLabel = new JLabel("Data Allenamento (2000/01/29):"); 
        dateFormat.setLenient(false); 
        dateField = new JFormattedTextField(dateFormat); 
        this.add( cod_trainign_Label);
        this.add(cod_training_Field);
        this.add(trainDateLabel); 
        this.add(dateField); 

        String sSQL = "SELECT * FROM Iscritto";
        PreparedStatement pS;
        try {
            pS = connection.prepareStatement(sSQL);
            ResultSet rs = pS.executeQuery( sSQL);
            while(rs.next()){
                    
                playersList .add(new JCheckBox(rs.getObject(1).toString()));
                for (JCheckBox jCheckBox : playersList ) {
                    pannelloPlayers.add(jCheckBox);
                }
             }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        this.scrollPane = new JScrollPane(pannelloPlayers);
        add(scrollPane);
        setVisible(true); 
    }
    public void callQuery() {
        try {
            Statement state= connection.createStatement();
            statement = connection.createStatement();
            String sSQL = "SELECT count(*) FROM allenamento WHERE CodAllenamento = '"
            +cod_training_Field.getText().toUpperCase()+"'";
            ResultSet rs = state.executeQuery( sSQL);
            rs.next();
            String resultCount = rs.getObject(1).toString();
            if(!resultCount.equals("0")){
                
            }else {
                statement.executeUpdate(
                "INSERT INTO allenamento (CodAllenamento, Data)"+
               "VALUES ('"+cod_training_Field.getText().toUpperCase()+"', '"+
               dateField.getText()+"');");
            }
            rs.close();
            state.close();
            playersList.stream().filter(x-> x.isSelected()).forEach(giocatore ->{
                try 
                {
                    statement.executeUpdate("INSERT INTO fare (CodAllenamento, CF_Iscritto)"+
                    "VALUES ('"+cod_training_Field.getText().toUpperCase()+"', '"
                    +giocatore.getText()+"');");
                    statement.close();
                } catch (SQLException e) {
                  e.printStackTrace();
              }
            });  
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
