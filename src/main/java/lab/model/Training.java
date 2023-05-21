package lab.model;

import javax.swing.*;
import lab.db.ConnectionProvider;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JComboBox<String> comboBoxSports = new JComboBox<>(); 
       
    public Training(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        this.scrollPane = new JScrollPane();
        this.comboBoxSports.addItem("Calcio"); 
        this.comboBoxSports.addItem("Pallavolo"); 
        this.comboBoxSports.addItem("Basket");
        this.comboBoxSports.addItem("Baseball"); 
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new GridLayout(6, 2)); 
        final JLabel trainDateLabel = new JLabel("Data Allenamento (2000/01/29):"); 
        dateFormat.setLenient(false); 
        dateField = new JFormattedTextField(dateFormat); 
        this.add(comboBoxSports);
        this.add( new JLabel("Codice Allenamento:"));
        this.add(cod_training_Field);
        this.add(trainDateLabel); 
        this.add(dateField); 

        this.scrollPane = new JScrollPane(pannelloPlayers);
        add(scrollPane);
        checkComboBoxInput();
        comboBoxSports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                checkComboBoxInput();
            }
            
        });
       
    }
    public void callQuery() {
        try {
            statement = connection.createStatement();
            String sSQL = "SELECT count(*) FROM allenamento WHERE CodAllenamento = '"
            +cod_training_Field.getText().toUpperCase()+"'";
            ResultSet rs = statement.executeQuery( sSQL);
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
            playersList.stream().filter(x-> x.isSelected()).forEach(giocatore ->{
                try 
                {
                    statement.executeUpdate("INSERT INTO fare (CodAllenamento, CF_Iscritto)"+
                    "VALUES ('"+cod_training_Field.getText().toUpperCase()+"', '"
                    +giocatore.getText()+"');");
                   // statement.close();
                } catch (SQLException e) {
                  e.printStackTrace();
              }
            });  
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void checkComboBoxInput() {
       
        this.remove(scrollPane);
        pannelloPlayers.removeAll();
        this.revalidate();
        String sSQL = "SELECT * from iscritto as I Join squadra as S ON I.CodSquadra = S.CodSquadra"+
        " where S.Sport = '"+ comboBoxSports.getSelectedItem().toString()+"'";
        PreparedStatement pS;
        try {
            pS = connection.prepareStatement(sSQL);
            ResultSet rs = pS.executeQuery( sSQL);
            while(rs.next()){
                    
                playersList.add(new JCheckBox(rs.getObject(1).toString()));
                for (JCheckBox jCheckBox : playersList ) {
                    pannelloPlayers.add(jCheckBox);
                }
             }
             rs.close();
             pS.close();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        playersList.removeAll(playersList);
        this.scrollPane = new JScrollPane(pannelloPlayers);
        pannelloPlayers.revalidate();
        add(scrollPane);
        setVisible(true); 
    }
}
