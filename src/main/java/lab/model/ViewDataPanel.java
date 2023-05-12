package lab.model;

import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import lab.db.ConnectionProvider;


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
    JPanel panel = new JPanel(); 

    private JComboBox<String> comboBox = new JComboBox<>();
    private JComboBox<String> comboBoxSports = new JComboBox<>(); 

    public ViewDataPanel() {
       setSize(1000, 600);
       setTitle("Visualizza Dati");
       panel.setLayout(new GridLayout(2, 3)); 
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM"); 
       dateField = new JFormattedTextField(dateFormat); 

       JLabel textLabel = new JLabel("Visualizza:"); 
       comboBox.addItem("Iscritto"); 
       comboBox.addItem("Allenatore");
       comboBox.addItem("Autista"); 
       comboBox.addItem("Preparatore");
       comboBox.addItem("Squadra"); 
       comboBox.addItem("Partita"); 
       comboBox.addItem("Trasferta"); 
       comboBox.addItem("Allenamento");
       comboBox.addItem("Fare"); 
       comboBox.addItem("Pagamenti"); 

        comboBoxSports.addItem("Calcio"); 
        comboBoxSports.addItem("Pallavolo"); 
        comboBoxSports.addItem("Basket");
        comboBoxSports.addItem("Baseball"); 

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                checkComboBoxInput();
            }
            
        });
       executeButton = new JButton("Execute");
       executeButton.addActionListener(e ->{
        switch (comboBox.getSelectedItem().toString().toLowerCase()) {
            case "partita":executeQueryMatch();break;
            case "pagamenti":executeQueryPay();break;
                
        
            default: executeQuery();break;
        }
       
       } ); 
       resultTable = new JTable();
       scrollPane = new JScrollPane(resultTable);

       panel.add(new JLabel(""));
       panel.add(textLabel);
       panel.add(comboBox);
       panel.add(comboBoxSports);
       panel.add(new JLabel("Data :"));
       panel.add(dateField);
       comboBoxSports.setVisible(false);
       add(panel, BorderLayout.NORTH); 
       add(scrollPane, BorderLayout.CENTER); 
       add( executeButton, BorderLayout.SOUTH); 
       setVisible(true);
    }

    private void executeQuery() {
        PreparedStatement pS;
        ResultSet rs;

        try {
            ConnectionProvider prov = new ConnectionProvider(DB_USER , DB_PASSWORD, DB_URL);
            Connection conn= prov.getMySQLConnection();
        
            String sSQL = "SELECT * FROM ?";
            if(comboBox.getSelectedItem().toString().toLowerCase().equals("iscritto")) {
                sSQL = "SELECT CF , I.Nome , Cognome , S.Nome as Squadra ,S.Sport"+
                " from iscritto I JOIN squadra S ON I.CodSquadra = S.CodSquadra";
                pS = conn.prepareStatement(sSQL);
                rs = pS.executeQuery();
            }
           else if(comboBox.getSelectedItem().toString().toLowerCase().equals("squadra")) {
                sSQL = "SELECT CF , I.Nome , Cognome , S.Nome as Squadra ,S.CodSquadra, S.Sport"+
               " from iscritto I JOIN squadra S ON I.CodSquadra = S.CodSquadra" ;
                pS = conn.prepareStatement(sSQL);
                rs = pS.executeQuery();
              
            }
            else {
                pS = conn.prepareStatement(sSQL);
                rs = pS.executeQuery(sSQL.replace("?",comboBox.getSelectedItem().toString().toLowerCase()));
            }
            
            this.showResult(rs);
               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executeQueryMatch() {
        try {
            ConnectionProvider prov = new ConnectionProvider(DB_USER , DB_PASSWORD, DB_URL);
            Connection conn= prov.getMySQLConnection();
            String sSQL = "SELECT P.CodPartita,P.CodSquadra,P.Data,P.Città,P.Via"
            +",P.Cap,P.Numero,P.Avversario,P.CF_Preparatore,S.CF_Allenatore,P.Risultato"+
            " FROM partita P JOIN squadra S ON P.CodSquadra = S.CodSquadra WHERE S.Sport = ?";
            PreparedStatement pS = conn.prepareStatement(sSQL);
            pS.setString(1,comboBoxSports.getSelectedItem().toString().toLowerCase());
            ResultSet rs = pS.executeQuery();
           this.showResult(rs);
               
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
            ConnectionProvider prov = new ConnectionProvider(DB_USER , DB_PASSWORD, DB_URL);
            Connection conn= prov.getMySQLConnection();
            String sSQL = "SELECT * FROM Stipendio"+
            " WHERE MONTH(DataErogazione) = "+dateField.getText().split("/")[1]
            +" AND YEAR(DataErogazione) = "+dateField.getText().split("/")[0];
            PreparedStatement pS = conn.prepareStatement(sSQL);
            ResultSet rs = pS.executeQuery();
           this.showResult(rs);
               
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

