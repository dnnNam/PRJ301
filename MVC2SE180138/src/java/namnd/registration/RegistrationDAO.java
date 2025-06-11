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
         boolean result = false;
         Connection con = null; 
         PreparedStatement stm = null;
         ResultSet rs = null;
         try {
             // 1 model connect DB
             con = DBHelper.makeConnection();
             // 2.1write SQL String 
             String sql = "Select username "
                  +  "From Registration "
                  + "Where username = ? "
                  + "And password = ?";
             // 2.2 nạp câu lệnh vào bộ nhớ  và check cú pháp 
             stm = con.prepareStatement(sql);
             stm.setString(1, username);
             stm.setString(2, password);
             rs = stm.executeQuery();
             if(rs.next()){
                 result = true;
             }
        } finally {
             
             if(rs != null){
                 rs.close();
             }
             if(stm != null){
                 stm.close();
             }
             if(con != null){
                 con.close();
             }
        }
         return result;
    }
}
