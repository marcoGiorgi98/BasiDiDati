package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class Training extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // crea un formato di data

    private JTextField cod_training_Field = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JFormattedTextField dateField; // crea una textbox formattata per la data di nascita
    
    private JScrollPane scrollPane; // Lo scroll pane per la tabella
    JPanel pannelloPlayers = new JPanel(new GridLayout(1, 1));
    private LinkedList<JCheckBox> playersList = new LinkedList<>()  ; // Lista dei giocatori selezionati

    ConnectionProvider prov = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    Connection conn= prov.getMySQLConnection();
    Statement s;
    ResultSet r;
       

    public Training() {
        scrollPane = new JScrollPane();
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(6, 2)); // usa GridLayout con 7 righe e 2 colonne
        JLabel cod_trainign_Label = new JLabel("Codice Allenamento:"); // crea una label per il nome
        JLabel trainDateLabel = new JLabel("Data Allenamento (2000/01/29):"); // crea una label per il nome
        dateFormat.setLenient(false); // rendi il formato non tollerante alle date non valide
        dateField = new JFormattedTextField(dateFormat); // crea la textbox formattata con il formato di data

        this.add( cod_trainign_Label);
        this.add(cod_training_Field);
        this.add(trainDateLabel); // aggiungi la label del nome al panel nella prima cella
        this.add(dateField); // aggiungi la textbox del nome al panel nella seconda cella


        String sSQL = "SELECT * FROM Iscritto";
        PreparedStatement pS;
        try {
            pS = conn.prepareStatement(sSQL);
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
        setVisible(true); // mostra il frame
    }
    public void callQuery() {
        try {
            Statement state= conn.createStatement();
            s = conn.createStatement();
            String sSQL = "SELECT count(*) FROM allenamento WHERE CodAllenamento = '"+cod_training_Field.getText().toUpperCase()+"'";
            ResultSet rs = state.executeQuery( sSQL);
            rs.next();
            String resultCount = rs.getObject(1).toString();
            if(!resultCount.equals("0")){
                
            }else {
                s.executeUpdate(
                "INSERT INTO allenamento (CodAllenamento, Data)"+
               "VALUES ('"+cod_training_Field.getText().toUpperCase()+"', '"+dateField.getText()+"');");
            }
            
            playersList.stream().filter(x-> x.isSelected()).forEach(giocatore ->{
                try 
                {
                    s.executeUpdate("INSERT INTO fare (CodAllenamento, CF_Iscritto)"+
                    "VALUES ('"+cod_training_Field.getText().toUpperCase()+"', '"+giocatore.getText()+"');");
                } catch (SQLException e) {
                  e.printStackTrace();
              }
            });  
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
