package polisportiva.model;

import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import polisportiva.db.ConnectionProvider;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ViewDataPanel  extends JFrame {

    private static final String DB_URL = "polisportiva";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Lakanoch98!";
    private JButton executeButton; 
    private JTable resultTable; 
    private JScrollPane scrollPane; 
    private JFormattedTextField dateField; 
    private JPanel panel = new JPanel(); 
    private ConnectionProvider prov;
    private JComboBox<String> comboBox = new JComboBox<>();
    private JComboBox<String> comboBoxSports = new JComboBox<>(); 

    public ViewDataPanel() {
       setSize(1000, 600);
       setTitle("Visualizza Dati");
       prov = new ConnectionProvider(DB_USER , DB_PASSWORD, DB_URL);
       this.panel.setLayout(new GridLayout(2, 3)); 
       final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM"); 
       this.dateField = new JFormattedTextField(dateFormat); 

       final JLabel textLabel = new JLabel("Visualizza:"); 
       this.comboBox.addItem("Iscritto"); 
       this.comboBox.addItem("Allenatore");
       this.comboBox.addItem("Autista"); 
       this.comboBox.addItem("Preparatore");
       this.comboBox.addItem("Squadra"); 
       this.comboBox.addItem("Partita"); 
       this.comboBox.addItem("Trasferta"); 
       this.comboBox.addItem("Allenamento");
       this.comboBox.addItem("Presenze Allenamenti"); 
       this.comboBox.addItem("Pagamenti"); 

        this.comboBoxSports.addItem("Calcio"); 
        this.comboBoxSports.addItem("Pallavolo"); 
        this.comboBoxSports.addItem("Basket");
        this.comboBoxSports.addItem("Baseball"); 

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                checkComboBoxInput();
            }
            
        });
       executeButton = new JButton("Mostra");
       executeButton.addActionListener(e ->{
        switch (comboBox.getSelectedItem().toString().toLowerCase()) {
            case "partita":executeQueryMatch();break;
            case "pagamenti":executeQueryPay();break;
            case "presenze allenamenti": this.executeQueryTrainingPlayer();break;
            default: executeQuery();break;
        }
       
       } ); 
       this.resultTable = new JTable();
       this.scrollPane = new JScrollPane(resultTable);
       this.panel.add(new JLabel(""));
       this.panel.add(textLabel);
       this.panel.add(comboBox);
       this.panel.add(comboBoxSports);
       this.panel.add(new JLabel("Data (yyyy/mm):"));
       this.panel.add(dateField);
       this.comboBoxSports.setVisible(false);
       add(panel, BorderLayout.NORTH); 
       add(scrollPane, BorderLayout.CENTER); 
       add( executeButton, BorderLayout.SOUTH); 
       setVisible(true);
    }

    private void executeQuery() {
        PreparedStatement pS;
        ResultSet rs;

        try {
            Connection conn= prov.getMySQLConnection();
        
            String sSQL = "SELECT * FROM ?";
            if(comboBox.getSelectedItem().toString().toLowerCase().equals("iscritto")) {
                sSQL = "SELECT CF , I.Nome , Cognome , S.Nome as Squadra ,S.Sport"+
                " from iscritto I JOIN squadra S ON I.CodSquadra = S.CodSquadra";
                pS = conn.prepareStatement(sSQL);
                rs = pS.executeQuery();
            }
           else if(comboBox.getSelectedItem().toString().toLowerCase().equals("squadra")) {
                sSQL = "SELECT CodSquadra ,S.Nome, S.Sport,S.CF_Allenatore,A.Nome,A.Cognome"+
               " from Squadra as S Join allenatore as A ON S.CF_Allenatore = A.CF" ;
                pS = conn.prepareStatement(sSQL);
                rs = pS.executeQuery();
              
            }
            else {
                pS = conn.prepareStatement(sSQL);
                rs = pS.executeQuery(sSQL.replace("?",comboBox.getSelectedItem().toString().toLowerCase()));
            }
            
            this.showResult(rs);
            conn.close();
            pS.close();
               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executeQueryMatch() {
        try {
            Connection conn= prov.getMySQLConnection();
            String sSQL = "SELECT P.CodPartita,P.CodSquadra,P.Data,P.Città,P.Via"
            +",P.Cap,P.Numero,P.Avversario,P.CF_Preparatore,S.CF_Allenatore,P.Risultato"+
            " FROM partita P JOIN squadra S ON P.CodSquadra = S.CodSquadra WHERE S.Sport = ?";
            PreparedStatement pS = conn.prepareStatement(sSQL);
            pS.setString(1,comboBoxSports.getSelectedItem().toString().toLowerCase());
            ResultSet rs = pS.executeQuery();
           this.showResult(rs);
           conn.close();
           pS.close();
               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void showResult( ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        DefaultTableModel tableModel = new DefaultTableModel();
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(metaData.getColumnName(i));
        }
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            tableModel.addRow(rowData);
        }
        panel.revalidate();
        this.resultTable = new JTable(tableModel);
        scrollPane = new JScrollPane(resultTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        add(scrollPane, BorderLayout.CENTER);
    }

    private void executeQueryPay() {
        try {
            Connection conn= prov.getMySQLConnection();
            if(dateField.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Inserire una data", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String sSQL = "SELECT * FROM Stipendio"+
            " WHERE MONTH(DataErogazione) = "+dateField.getText().split("/")[1]
            +" AND YEAR(DataErogazione) = "+dateField.getText().split("/")[0];
            PreparedStatement pS = conn.prepareStatement(sSQL);
            ResultSet rs = pS.executeQuery();
           this.showResult(rs);
           conn.close();
           pS.close();
               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executeQueryTrainingPlayer() {
        Statement pS;
        ResultSet rs;
        Connection conn= prov.getMySQLConnection();
        try {
            pS = conn.createStatement();
            rs = pS.executeQuery(
                "Select * from fare");
            this.showResult(rs);
            conn.close();
            pS.close();
               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
      
    }

    private void checkComboBoxInput() {
        if (comboBox.getSelectedItem().toString().toLowerCase().equals("partita")) {
            comboBoxSports.setVisible(true);
        }
        else {
            comboBoxSports.setVisible(false);
        } 
    }
}

