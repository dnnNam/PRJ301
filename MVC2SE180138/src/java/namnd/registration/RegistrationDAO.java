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
        // create variable boolean to help finished function 
        boolean result = false;
        // connect BD by helperDB 
        Connection  con = null; 
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
             // step 13 and 14 12  step 12 finished 
             // 1 model connect DB 
             // 1.1declare variable  = null
             // 1.2 close all object 
             // 1.3 execute 
             con = DBHelper.makeConnection();
             if(con != null){
                 // 2 model truy van du lieu tu DB 
                 // 2.1 tao cau lenh SQL 
                 // moi menh de cuacau lenh SQl phai viet tren 1 dong 
                 // truoc khi xuong dong phai chen them 1 khoang trang neu khong 
                 // cos loi syntaxFromNear
                 // tat ca cac ten cot phai copy tu DB 
                 // neu khong co loi OBJECT NOT FOUND 
                 String sql = "Select username "
                   + "From Registration "
                   + "Where username = ? "   
                   + "And password = ?";
                 // 2.2 to den cau lenh la qua trinh nap cau try van vao create statement Object 
                 // check syntax va excute 
                 stm = con.prepareStatement(sql);
                 stm.setString(1, username);
                 stm.setString(2, password);
                 // 2.3 execture query 
                 rs = stm.executeQuery();
                 if(rs.next()){
                     result =true;
                 }
                 
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
