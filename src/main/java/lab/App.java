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
             r = s.executeQuery("Select * from iscritto");
             while(r.next()){
                int numCol = r.getMetaData().getColumnCount();
                for ( int i = 1 ; i <= numCol ; i++ )
                { // I numeri di colonna iniziano da 1.
                    System.out.println( "COL" + i + "=" +r.getObject(i));
                } 
             }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        UserInterface user =new UserInterface();
        

        
    

    }

}
