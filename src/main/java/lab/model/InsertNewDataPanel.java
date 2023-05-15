package lab.model;

import javax.swing.*;

import lab.db.ConnectionProvider;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class InsertNewDataPanel extends JFrame {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 

    private JComboBox<String> comboBox = new JComboBox<>();
    private JTextField cFField = new JTextField(10); 
    private JTextField nameField = new JTextField(10); 
    private JTextField surnameField = new JTextField(10); 
    private JTextField phoneField = new JTextField(10); 
    private JTextField viaField = new JTextField(20);
    private JTextField cityField = new JTextField(10);
    private JTextField numberField = new JTextField(10); 
    private JTextField capField = new JTextField(5); 
    private JTextField driverField = new JTextField(10); 
    private JTextField codTesseraField = new JTextField(10); 
    private JTextField categoryField = new JTextField(10); 

    private final JLabel tesseraLabel = new JLabel("Codice Tessera"); // 
    private final JLabel categoriaLabel = new JLabel("Categoria"); // 
    private JFormattedTextField subscrictionField = new JFormattedTextField(dateFormat); 
    private JFormattedTextField birthField; 
    private JButton button = new JButton("Invia"); 
    private final JLabel DriverLabel = new JLabel("Numero Patente"); // 
    private final JLabel subscrictionLabel = new JLabel("Data Iscrizione (2000/12/27):"); 
    private JComboBox<String> sportSelectionBox = new JComboBox<>();  
    private JTextField nomeSquadra = new JTextField();
    private JTextField codSquadra = new JTextField();
    private JPanel pannelloAllenatori = new JPanel(new GridLayout(40, 1));
    private LinkedList<JCheckBox> allenatoriList= new LinkedList<JCheckBox>();
    private LinkedList<JCheckBox> giocatoriList= new LinkedList<JCheckBox>();

    private Match match;
    private Transfert transfert;
    private Training training;
    private Tesseramento tesserament;
    private Pay payment;
    private JPanel panel = new JPanel(); 
    private JPanel upperPanel= new JPanel();
    private ConnectionProvider provider = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
    private  Connection connection;
    private Statement statement;
    private ResultSet r;
       

    public InsertNewDataPanel(int width, int height) {
        button.addActionListener(new ActionListener() { 
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
        this.connection= provider.getMySQLConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.panel.setLayout(new GridLayout(14, 2)); 
        this.upperPanel.setLayout(new GridLayout(1, 2));
        final JLabel cFLabel = new JLabel("Codice Fiscale:"); 
        final JLabel nameLabel = new JLabel("Nome:"); 
        final JLabel surnameLabel = new JLabel("Cognome:"); 
        final JLabel phoneLabel = new JLabel("Telefono:"); 
        final JLabel addressLabel1 = new JLabel("Via:"); 
        final JLabel addressLabel2 = new JLabel("Numero:"); 
        final JLabel addressLabel4 = new JLabel("Città:"); 
        final JLabel addressLabel3 = new JLabel("CAP:"); 
        final JLabel birthLabel = new JLabel("Data di nascita (2000/12/27):"); 
        final JLabel selectionLabel = new JLabel("Figura da inserire"); 
        this.dateFormat.setLenient(false); 
        this.birthField = new JFormattedTextField(dateFormat); 
        this.comboBox.addItem("Allenatore");
        this.comboBox.addItem("Iscritto"); 
        this.comboBox.addItem("Autista"); 
        this.comboBox.addItem("Preparatore"); 
        this.comboBox.addItem("Squadra"); 
        this.comboBox.addItem("Partita"); 
        this.comboBox.addItem("Trasferta");
        this.comboBox.addItem("Allenamento"); 
        this.comboBox.addItem("Tesseramento"); 
        this.comboBox.addItem("Pagamento");
        this.comboBox.setEditable(false); 
        this.upperPanel.add(selectionLabel);
        this.upperPanel.add(comboBox); 
        this.panel.add(cFLabel);
        this.panel.add(cFField);
        this.panel.add(nameLabel); 
        this.panel.add(nameField); 
        this.panel.add(surnameLabel);
        this.panel.add(surnameField);
        this.panel.add(phoneLabel); 
        this.panel.add(phoneField);
        this.panel.add(birthLabel); 
        this.panel.add(birthField); 
        this.panel.add(addressLabel1);
        this.panel.add(viaField); 
        this.panel.add(addressLabel2);
        this.panel.add(numberField);   
        this.panel.add(addressLabel3);
        this.panel.add(cityField); 
        this.panel.add(addressLabel4);
        this.panel.add(capField);
        this.panel.add(DriverLabel); 
        this.panel.add(driverField); 
        this.panel.add(tesseraLabel);
        this.panel.add(codTesseraField);
        this.panel.add(categoriaLabel);
        this.panel.add(categoryField);
        this.panel.add(subscrictionLabel);
        this.panel.add(subscrictionField);
        this.subscrictionLabel.setVisible(false);
        this.subscrictionField.setVisible(false);
        this.categoryField.setVisible(false);
        this.categoriaLabel.setVisible(false);
        this.codTesseraField.setVisible(false);
        this.tesseraLabel.setVisible(false);
        this.driverField.setVisible(false);
        this.DriverLabel.setVisible(false);
        add(panel, BorderLayout.CENTER); 
        add(upperPanel, BorderLayout.NORTH); 
        add(button, BorderLayout.SOUTH); 
        pack(); 
        setVisible(true); 
    }

    public void insertNewData() {
        final  String selectedTable = comboBox.getSelectedItem().toString();
        switch (selectedTable) {
            case "Autista": addAutista() ;break;
            case "Iscritto": addIscritto(); ;break;
            case "Squadra": addSquadra() ;break;
            case "Partita": match.callQuery() ;break;
            case "Trasferta": transfert.callQuery() ;break;
            case "Allenamento": training.callQuery() ;break;
            case "Tesseramento": tesserament.callQuery() ;break;
            case "Pagamento": payment.callQuery() ;break;
            default:  addPreparatoreAllenatore(selectedTable) ;
                break;
        }
        
    }

    private void addIscritto() {
        try {
            this.statement.executeUpdate(
         "INSERT INTO iscritto (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono,Categoria,DataIscrizione,CodTessera)"+
        "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+birthField.getText()
        +"', '"+viaField.getText()+"', '"+numberField.getText()
        +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"', '"+categoryField.getText().toUpperCase()
        +"', '"+subscrictionField.getText()+"', '"+codTesseraField.getText().toUpperCase()+"');");

        } catch (SQLException e) {
        e.printStackTrace();
        }
    }

    private void addAutista() {
        try {
            this.statement.executeUpdate("INSERT INTO autista (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono, CodPatente)"+
            "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+
            birthField.getText()+"', '"+viaField.getText()+"', '"+numberField.getText()
            +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"', '"+driverField.getText()+"');");
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
       
    }

    private void addPreparatoreAllenatore(String person) {
        try {
             this.statement.executeUpdate("INSERT INTO "+person +" (CF, Nome, Cognome, DataNascita, Via, Numero, Cap, Città,Telefono)"+
            "VALUES ('"+cFField.getText().toUpperCase()+"', '"+nameField.getText()+"', '"+surnameField.getText()+"', '"+
            birthField.getText()+"', '"+viaField.getText()+"', '"+numberField.getText()
            +"', '"+capField.getText()+"', '"+cityField.getText()+"', '"+phoneField.getText()+"');");
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
       
    }

    private void addSquadra() {
        giocatoriList.stream().filter(x-> x.isSelected()).forEach(giocatore ->{
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
            +"', '"+allenatoriList.stream().filter(x -> x.isSelected()).findFirst().get().getText() +"');");
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }

    private void checkComboBoxInput() {
        if(comboBox.getSelectedItem()=="Autista") {
            driverField.setVisible(true);
            DriverLabel.setVisible(true);
           
        }
        else {
            driverField.setVisible(false);
            DriverLabel.setVisible(false);
        }
        if(comboBox.getSelectedItem()=="Iscritto") {
        subscrictionLabel.setVisible(true);
        subscrictionField.setVisible(true);
        categoryField.setVisible(true);
        categoriaLabel.setVisible(true);
        codTesseraField.setVisible(true);
        tesseraLabel.setVisible(true);   
        }
        else {
            subscrictionLabel.setVisible(false);
            subscrictionField.setVisible(false);
            categoryField.setVisible(false);
            categoriaLabel.setVisible(false);
            codTesseraField.setVisible(false);
            tesseraLabel.setVisible(false);   
        }
        if(comboBox.getSelectedItem()=="Partita") {
            this.remove(panel);
            match = new Match(this.provider);
            add(match, BorderLayout.CENTER); // aggiungi il panel al frame al centro
            revalidate();
            repaint();
        }
        if(comboBox.getSelectedItem()=="Trasferta") {
            this.remove(panel);
            transfert = new Transfert(this.provider);
            add(transfert, BorderLayout.CENTER); // aggiungi il panel al frame al centro
            revalidate();
            repaint();
        }
        if(comboBox.getSelectedItem()=="Allenamento") {
            this.remove(panel);
            training = new Training(this.provider);
            add( training, BorderLayout.CENTER); // aggiungi il panel al frame al centro
            revalidate();
            repaint();
        }
        if(comboBox.getSelectedItem()=="Tesseramento") {
            this.remove(panel);
            tesserament = new Tesseramento(this.provider);
            add( tesserament, BorderLayout.CENTER); // aggiungi il panel al frame al centro
            revalidate();
            repaint();
        }
        if(comboBox.getSelectedItem()=="Pagamento") {
            this.remove(panel);
            payment = new Pay(this.provider);
            add( payment, BorderLayout.CENTER); // aggiungi il panel al frame al centro
            revalidate();
            repaint();
        }
        if (comboBox.getSelectedItem() == "Squadra") {
            panel.removeAll();
            panel.setLayout( new BorderLayout());
           
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
            
            Connection conn= provider.getMySQLConnection();
                try {
                    Statement s = conn.createStatement();
                    ResultSet rs = s.executeQuery("Select * from allenatore");
                while(rs.next()){
                    
                    allenatoriList.add(new JCheckBox(rs.getObject(1).toString()));
                    for (JCheckBox jCheckBox : allenatoriList) {
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
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery("Select * from iscritto");
            while(rs.next()){
            
                giocatoriList.add(new JCheckBox(rs.getObject(1).toString()));
                for (JCheckBox jCheckBox : giocatoriList) {
                    pannelloGiocatori.add(jCheckBox);
                }
             }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JScrollPane scrollPane1 = new JScrollPane(pannelloGiocatori);
            panel.add(pannelloSuperiore, BorderLayout.NORTH);
            panel.add(scrollPane1, BorderLayout.CENTER);
            panel.add(pannelloDestra , BorderLayout.EAST);
            panel.revalidate();
            panel.repaint();
        }
        
    }
}
