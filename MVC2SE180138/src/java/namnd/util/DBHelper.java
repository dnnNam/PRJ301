/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package namnd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Hi
 */


public class DBHelper {
    
    public static Connection makeConnection ()
    throws ClassNotFoundException, SQLException
    {
       // load driver 
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
       //2 create connection string ro connect SQL 
       String url = "jdbc:sqlserver://"
               +"localhost:1433;" +
               "databaseName=PRJ301SE1809";
       Connection con = DriverManager.getConnection(url, "sa" , "12345");
       //3 Open Connect using Driver Manager
       return con;
    }
}
