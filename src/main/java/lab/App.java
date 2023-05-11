package lab;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lab.db.ConnectionProvider;
import lab.model.UserInterface;

public class App {

    public static void main(String[] args) throws SQLException {
        
        ConnectionProvider prov = new ConnectionProvider("root", "Lakanoch98!", "polisportiva");
        Connection conn= prov.getMySQLConnection();
        Statement s;
        ResultSet r;
        try {
             s = conn.createStatement();
            // s.executeUpdate("UPDATE iscritto SET CodSquadra = NULL");
             
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        UserInterface user =new UserInterface();
        

        
    

    }

}
