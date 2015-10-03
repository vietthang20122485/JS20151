/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NghiepVu;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author vietthang
 */
public class Connect {
    private String ClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String url = "jdbc:sqlserver://127.0.0.1:50000;databaseName=demo;user=sa;password=thang123";
    public static Connection connection;
    public Connect() {       
        try {
            Class.forName(ClassName);
            try {
                connection = DriverManager.getConnection(url);
                Welcome wel = new Welcome();
                wel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                wel.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        Connect connect = new Connect();
    }
}
