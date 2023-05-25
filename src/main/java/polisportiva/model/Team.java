package polisportiva.model;
import javax.swing.*;
import polisportiva.db.ConnectionProvider;
import java.awt.*;
import java.sql.*;
import java.util.LinkedList;

public class Team extends JPanel{
    private JComboBox<String> sportSelectionBox = new JComboBox<>();  
    private  Connection connection;
    private JTextField teamName = new JTextField();
    private JTextField teamCode = new JTextField();
    private LinkedList<JCheckBox> coachList= new LinkedList<JCheckBox>();
    private LinkedList<JCheckBox> playersList= new LinkedList<JCheckBox>();
    private JPanel coachPanel = new JPanel(new GridLayout(40, 1));
    Statement statement;
       
    public Team(ConnectionProvider provider) {
        this.connection = provider.getMySQLConnection();
        mainInterface();
    }

    private void mainInterface() {
        this.setLayout(new BorderLayout()); 
        this.sportSelectionBox.addItem("Calcio");
        this.sportSelectionBox.addItem("Basket");
        this.sportSelectionBox.addItem("Pallavolo");
        this.sportSelectionBox.addItem("BaseBall");
        JPanel upperPanel = new JPanel(new GridLayout(2, 3));
        upperPanel.add( sportSelectionBox);
        upperPanel.add(new JLabel("Inserisci nome squadra"));
        upperPanel.add(new JLabel("Inserisci il codice Squadra"));
        upperPanel.add(new JLabel("Seleziona i giocatori"));
        upperPanel.add(teamName);
        upperPanel.add(teamCode);
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.add(new JLabel("Seleziona nome dell'allenatore"));
        
            try {
                this.statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("Select * from allenatore");
            while(rs.next()){
                
                coachList.add(new JCheckBox(rs.getObject(1).toString()));
                for (JCheckBox jCheckBox : coachList) {
                    coachPanel.add(jCheckBox);
                }
             }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        final JScrollPane scrollPaneAllenatori = new JScrollPane(coachPanel);
        rightPanel.add(scrollPaneAllenatori);

        final JPanel playersPanel= new JPanel();
        playersPanel.setLayout(new GridLayout(40, 1));

        try {
            ResultSet rs = statement.executeQuery(
                "Select * from iscritto where CodSquadra is null and CodTessera is not null");
        while(rs.next()){
        
            playersList.add(new JCheckBox(rs.getObject(1).toString()));
            for (JCheckBox jCheckBox : playersList) {
                playersPanel.add(jCheckBox);
            }
         }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JScrollPane scrollPane1 = new JScrollPane(playersPanel);
        this.add(upperPanel, BorderLayout.NORTH);
       this.add(scrollPane1, BorderLayout.CENTER);
       this.add(rightPanel , BorderLayout.EAST);
       this.revalidate();
       this.repaint();
    }
    
    public void callQuery() {
        playersList.stream().filter(x-> x.isSelected()).forEach(giocatore ->{
            try {
                this.statement.executeUpdate("UPDATE iscritto SET CodSquadra = "+"'"
                +teamCode.getText()+"'"+"WHERE iscritto.CF = "
                +"'"+giocatore.getText()+"'");
              
          } catch (SQLException e) {
              e.printStackTrace();
          }
        });
        try {
            this.statement.executeUpdate("INSERT INTO squadra (CodSquadra,Nome, Sport, CF_Allenatore)"+
            "VALUES ('"+teamCode.getText().toUpperCase()+"', '"+teamName.getText()+"', '"
            + sportSelectionBox.getSelectedItem().toString().toUpperCase()
            +"', '"+coachList.stream().filter(x -> x.isSelected()).findFirst().get().getText() +"');");
          
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }
}
