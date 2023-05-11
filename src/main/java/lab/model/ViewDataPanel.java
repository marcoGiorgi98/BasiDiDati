package lab.model;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import lab.db.ConnectionProvider;


import java.awt.*;
public class ViewDataPanel  extends JFrame {

    // Variabili per la connessione al database
    private static final String DB_URL = "polisportiva";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Lakanoch98!";

    // Variabili per l'interfaccia grafica
    private JButton executeButton; // Il bottone per eseguire la query
    private JTable resultTable; // La tabella per mostrare i risultati
    private JScrollPane scrollPane; // Lo scroll pane per la tabella
    JPanel panel = new JPanel(); // crea un panel

    private JComboBox<String> comboBox = new JComboBox<>(); // crea una JComboBox

    public ViewDataPanel() {
       // Creo la finestra principal 
       setSize(1000, 600);
       setTitle("Visualizza Dati");
       panel.setLayout(new GridLayout(2, 1)); // usa GridLayout con 7 righe e 2 colonne

       JLabel textLabel = new JLabel("Visualizza:"); // crea una label per il cognome
       comboBox.addItem("Iscritto"); // aggiungi la prima opzione alla JComboBox
       comboBox.addItem("Allenatore"); // aggiungi la seconda opzione alla JComboBox
       comboBox.addItem("Autista"); // aggiungi la seconda opzione alla JComboBox
       comboBox.addItem("Preparatore"); // aggiungi la seconda opzione alla JComboBox
       comboBox.addItem("Squadra"); // aggiungi la seconda opzione alla JComboBox
       comboBox.addItem("Partita"); // aggiungi la seconda opzione alla JComboBox

       // Creo il bottone per eseguire la query
       executeButton = new JButton("Execute");
       executeButton.addActionListener(e -> executeQuery()); // Aggiungo un listener che chiama il metodo executeQuery quando il bottone viene premuto

       // Creo la tabella per i risultati (inizialmente vuota)
       resultTable = new JTable();
       //resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

       // Creo lo scroll pane per la tabella
       scrollPane = new JScrollPane(resultTable);

       // Aggiungo i componenti alla finestra
       panel.add(textLabel);
       panel.add(comboBox);

       // Imposto la dimensione e la visibilità della finestra
       add(panel, BorderLayout.NORTH); // aggiungi il panel al frame al centro
       add(scrollPane, BorderLayout.CENTER); // aggiungi il panel al frame al centro
       add( executeButton, BorderLayout.SOUTH); // aggiungi il bottone al frame a sud
       setVisible(true);
    }

    private void executeQuery() {
        try {
            ConnectionProvider prov = new ConnectionProvider(DB_USER , DB_PASSWORD, DB_URL);
            Connection conn= prov.getMySQLConnection();
        
            String sSQL = "SELECT * FROM ?";
            PreparedStatement pS = conn.prepareStatement(sSQL);
            pS.setString(1,comboBox.getSelectedItem().toString().toLowerCase());
           
         
            ResultSet rs = pS.executeQuery( sSQL.replace("?",comboBox.getSelectedItem().toString().toLowerCase()));
            
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
            add(scrollPane, BorderLayout.CENTER); // aggiungi il panel al frame al centro
           // panel.repaint();
            

            
        } catch (SQLException ex) {
            // In caso di eccezione, mostro un messaggio di errore
            JOptionPane.showMessageDialog(this, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

