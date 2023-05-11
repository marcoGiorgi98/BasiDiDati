package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class InsertNewDataPanel extends JFrame {
    private JComboBox<String> comboBox = new JComboBox<>(); // crea una JComboBox
    private JTextField cFField = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JTextField nameField = new JTextField(10); // crea una textbox di 10 caratteri per il nome
    private JTextField surnameField = new JTextField(10); // crea una textbox di 10 caratteri per il cognome
    private JTextField phoneField = new JTextField(10); // crea una textbox di 10 caratteri per il telefono
    private JTextField viaField = new JTextField(20); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField cityField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField numberField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField capField = new JTextField(5); // crea una textbox di 10 caratteri per l'indirizzo
    private JTextField driverField = new JTextField(10); // crea una textbox di 10 caratteri per l'indirizzo
    private JFormattedTextField birthField; // crea una textbox formattata per la data di nascita
    private JButton button = new JButton("Invia"); // crea un bottone con il testo "Invia"
    JLabel DriverLabel = new JLabel("Numero Patente"); // 
    JPanel panel = new JPanel(); // crea un panel
    ConnectionProvider prov = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    Connection conn= prov.getMySQLConnection();
    Statement s;
    ResultSet r;
       

    public InsertNewDataPanel(int width, int height) {
        button.addActionListener(new ActionListener() { // aggiungi un ActionListener al bottone per aprire il nuovo panel
            public void actionPerformed(ActionEvent e) {
                insertNewData();
            }
        });

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                checkComboBoxInput();
            }
            
        });
        mainInterface(width, height);
    }

    private void mainInterface(int width, int height) {
        setTitle("Inserisci nuovi Dati");
       // JPanel panel = new JPanel(); // crea un panel
        panel.setLayout(new GridLayout(12, 2)); // usa GridLayout con 7 righe e 2 colonne
        JLabel cFLabel = new JLabel("Codice Fiscale:"); // crea una label per il nome
        JLabel nameLabel = new JLabel("Nome:"); // crea una label per il nome
        JLabel surnameLabel = new JLabel("Cognome:"); // crea una label per il cognome
        JLabel phoneLabel = new JLabel("Telefono:"); // crea una label per il telefono
        JLabel addressLabel1 = new JLabel("Via:"); // crea una label per l'indirizzo
        JLabel addressLabel2 = new JLabel("Numero:"); // crea una label per l'indirizzo
        JLabel addressLabel4 = new JLabel("Città:"); // crea una label per l'indirizzo
        JLabel addressLabel3 = new JLabel("CAP:"); // crea una label per l'indirizzo
        JLabel birthLabel = new JLabel("Data di nascita (2000/12/27):"); // crea una label per la data di nascita
        JLabel selectionLabel = new JLabel("Figura da inserire"); // 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // crea un formato di data
        dateFormat.setLenient(false); // rendi il formato non tollerante alle date non valide
        birthField = new JFormattedTextField(dateFormat); // crea la textbox formattata con il formato di data
        comboBox.addItem("Iscritto"); // aggiungi la prima opzione alla JComboBox
        comboBox.addItem("Allenatore"); // aggiungi la seconda opzione alla JComboBox
        comboBox.addItem("Autista"); // aggiungi la seconda opzione alla JComboBox
        comboBox.addItem("Preparatore"); // aggiungi la seconda opzione alla JComboBox
        comboBox.addItem("Squadra"); // aggiungi la seconda opzione alla JComboBox
        comboBox.setEditable(false); // rendi la JComboBox non modificabile
        panel.add(selectionLabel);
        panel.add(comboBox); // aggiungi la JComboBox al panel nell'undicesima cella
        panel.add( cFLabel);
        panel.add(cFField);
        panel.add(nameLabel); // aggiungi la label del nome al panel nella prima cella
        panel.add(nameField); // aggiungi la textbox del nome al panel nella seconda cella
        panel.add(surnameLabel); // aggiungi la label del cognome al panel nella terza cella
        panel.add(surnameField); // aggiungi la textbox del cognome al panel nella quarta cella
        panel.add(phoneLabel); // aggiungi la label del telefono al panel nella quinta cella
        panel.add(phoneField); // aggiungi la textbox del telefono al panel nella sesta cella
        panel.add(birthLabel); // aggiungi la label della data di nascita al panel nella nona cella
        panel.add(birthField); // aggiungi la textbox della data di nascita al panel nella decima cella
        panel.add(addressLabel1); // aggiungi la label dell'indirizzo al panel nella settima cella
        panel.add(viaField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        panel.add(addressLabel2); // aggiungi la label dell'indirizzo al panel nella settima cella
        panel.add(numberField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella  
        panel.add(addressLabel3); // aggiungi la label dell'indirizzo al panel nella settima cella
        panel.add(cityField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        panel.add(addressLabel4); // aggiungi la label dell'indirizzo al panel nella settima cella
        panel.add(capField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella

        panel.add(DriverLabel); // aggiungi la label dell'indirizzo al panel nella settima cella
        panel.add(driverField); // aggiungi la textbox dell'indirizzo al panel nell'ottava cella
        driverField.setVisible(false);
        DriverLabel.setVisible(false);
        add(panel, BorderLayout.CENTER); // aggiungi il panel al frame al centro
        add(button, BorderLayout.SOUTH); // aggiungi il bottone al frame a sud
        pack(); // imposta la dimensione del frame in base al suo contenuto
        setVisible(true); // mostra il frame
    }

    public void insertNewData() {
        String selectedTable = comboBox.getSelectedItem().toString();
        switch (selectedTable) {
            case "Autista": addAutista() ;break;
            default: addIscritto();
                break;
        }
        
    }

    private String addIscritto() {
       
        return "INSERT INTO iscritto (CF, Name, Surname, Telephone, Birthday, Address, Number, Cap, City)"+
        "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+
        phoneField.getText()+"', '"+birthField.getText()+"', '"+viaField.getText()+"', '"+numberField.getText()
        +"', '"+capField.getText()+"', '"+cityField.getText()+");";
    }

    private void addAutista() {
        try {
            s = conn.createStatement();
             s.executeUpdate("INSERT INTO autista (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono, CodPatente)"+
            "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+
            birthField.getText()+"', '"+viaField.getText()+"', '"+numberField.getText()
            +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"', '"+driverField.getText()+"');");
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
       
    }

    private void checkComboBoxInput() {
        if(comboBox.getSelectedItem()=="Autista") {
            driverField.setVisible(true);
            DriverLabel.setVisible(true);
           
        }
        else if (comboBox.getSelectedItem() == "Squadra") {
            panel.removeAll();
            panel.setLayout( new BorderLayout());
            JComboBox<String> sportSelectionBox = new JComboBox<>();
            sportSelectionBox.addItem("Calcio");
            sportSelectionBox.addItem("Basket");
            sportSelectionBox.addItem("Pallavolo");
            sportSelectionBox.addItem("BaseBall");
            JTextField nomeSquadra = new JTextField();
            JTextField categoriaSquadra = new JTextField();
            JPanel pannelloSuperiore = new JPanel(new GridLayout(2, 3));
            pannelloSuperiore.add( sportSelectionBox);
            pannelloSuperiore.add(new JLabel("Inserisci nome squadra"));
            pannelloSuperiore.add(new JLabel("Inserisci la Categoria"));
            pannelloSuperiore.add(new JLabel("Seleziona i giocatori"));
            pannelloSuperiore.add(nomeSquadra);
            pannelloSuperiore.add(categoriaSquadra);

            JPanel pannelloDestra = new JPanel(new GridLayout(2, 1));
            pannelloDestra.add(new JLabel("Seleziona nome dell'allenatore"));
            JPanel pannelloAllenatori = new JPanel(new GridLayout(40, 1));
            for (int b=0;b<40;b++) {
                pannelloAllenatori.add(new JCheckBox("awrggfv"));
            }
            JScrollPane scrollPaneAllenatori = new JScrollPane(pannelloAllenatori);
            pannelloDestra.add(scrollPaneAllenatori);

            JPanel pannelloGiocatori= new JPanel();
            pannelloGiocatori.setLayout(new GridLayout(40, 1));
    
            for (int a=0;a<40;a++) {
                pannelloGiocatori.add(new JCheckBox("a.toString("));
            }
            JScrollPane scrollPane1 = new JScrollPane(pannelloGiocatori);
            panel.add(pannelloSuperiore, BorderLayout.NORTH);
            panel.add(scrollPane1, BorderLayout.CENTER);
            panel.add(pannelloDestra , BorderLayout.EAST);
            panel.revalidate();
            panel.repaint();
        }
        else {
            driverField.setVisible(false);
            DriverLabel.setVisible(false);
        }
    }
}
