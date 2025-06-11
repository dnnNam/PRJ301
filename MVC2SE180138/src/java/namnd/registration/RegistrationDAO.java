/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package namnd.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import namnd.util.DBHelper;

/**
 *
 * @author Hi
 */
public class RegistrationDAO implements Serializable{
    // first step implement serializable because all object inside server convert 
    // format bit stream , bytestream 
    
    // method check account have been existed 
    public boolean checkLogin(String username , String password)
    throws ClassNotFoundException , SQLException{
      
    }
}
