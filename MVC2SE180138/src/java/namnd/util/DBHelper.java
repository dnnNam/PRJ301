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
    throws ClassNotFoundException , SQLException{
        
        //1 load driver
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
       // 2 Create connection string to connect container 
       String url = "jdbc:sqlserver://"
               + "localhost:1433;"
               + "databaseName=PRJ301SE1809";
       // 3 open connect driver manager
       Connection con = DriverManager.getConnection(url , "sa" , "12345");
       return con;
       
    }
}
