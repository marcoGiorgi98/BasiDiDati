package lab.model;
import javax.swing.*;
import lab.db.ConnectionProvider;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class Team extends JPanel{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
    private JComboBox<String> sportSelectionBox = new JComboBox<>();  
    private  Connection connection;
    private JTextField nomeSquadra = new JTextField();
    private JTextField codSquadra = new JTextField();
    private LinkedList<JCheckBox> coachList= new LinkedList<JCheckBox>();
    private LinkedList<JCheckBox> playersList= new LinkedList<JCheckBox>();
    private JPanel pannelloAllenatori = new JPanel(new GridLayout(40, 1));
    Statement statement;
    ResultSet r;
       
    public Team(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new BorderLayout()); 
        sportSelectionBox.addItem("Calcio");
        sportSelectionBox.addItem("Basket");
        sportSelectionBox.addItem("Pallavolo");
        sportSelectionBox.addItem("BaseBall");
        JPanel pannelloSuperiore = new JPanel(new GridLayout(2, 3));
        pannelloSuperiore.add( sportSelectionBox);
        pannelloSuperiore.add(new JLabel("Inserisci nome squadra"));
        pannelloSuperiore.add(new JLabel("Inserisci il codice Squadra"));
        pannelloSuperiore.add(new JLabel("Seleziona i giocatori"));
        pannelloSuperiore.add(nomeSquadra);
        pannelloSuperiore.add(codSquadra);

        JPanel pannelloDestra = new JPanel(new GridLayout(2, 1));
        pannelloDestra.add(new JLabel("Seleziona nome dell'allenatore"));
        
            try {
                this.statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("Select * from allenatore");
            while(rs.next()){
                
                coachList.add(new JCheckBox(rs.getObject(1).toString()));
                for (JCheckBox jCheckBox : coachList) {
                    pannelloAllenatori.add(jCheckBox);
                }
             }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        JScrollPane scrollPaneAllenatori = new JScrollPane(pannelloAllenatori);
        pannelloDestra.add(scrollPaneAllenatori);

        JPanel pannelloGiocatori= new JPanel();
        pannelloGiocatori.setLayout(new GridLayout(40, 1));

        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("Select * from iscritto");
        while(rs.next()){
        
            playersList.add(new JCheckBox(rs.getObject(1).toString()));
            for (JCheckBox jCheckBox : playersList) {
                pannelloGiocatori.add(jCheckBox);
            }
         }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JScrollPane scrollPane1 = new JScrollPane(pannelloGiocatori);
        this.add(pannelloSuperiore, BorderLayout.NORTH);
       this.add(scrollPane1, BorderLayout.CENTER);
       this.add(pannelloDestra , BorderLayout.EAST);
       this.revalidate();
       this.repaint();
    }
    
    public void callQuery() {
        playersList.stream().filter(x-> x.isSelected()).forEach(giocatore ->{
            try {
                this.statement.executeUpdate("UPDATE iscritto SET CodSquadra = "+"'"
                +codSquadra.getText()+"'"+"WHERE iscritto.CF = "
                +"'"+giocatore.getText()+"'");
              
          } catch (SQLException e) {
              e.printStackTrace();
          }
        });
        try {
            this.statement.executeUpdate("INSERT INTO squadra (CodSquadra,Nome, Sport, CF_Allenatore)"+
            "VALUES ('"+codSquadra.getText()+"', '"+nomeSquadra.getText()+"', '"+ sportSelectionBox.getSelectedItem().toString()
            +"', '"+coachList.stream().filter(x -> x.isSelected()).findFirst().get().getText() +"');");
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }
}
