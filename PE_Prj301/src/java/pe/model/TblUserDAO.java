/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.utils.DbUtils;

/**
 *
 * @author Hi
 */
public class TblUserDAO implements Serializable {
    
    public TblUserDTO checkLogin (String username , int password)
    throws ClassNotFoundException , SQLException{
            TblUserDTO result = null;
            Connection con = null;
            PreparedStatement stm = null;
            ResultSet rs = null;
            try {
                // connect
                con = DbUtils.getConnection();
                if(con != null){
                    // query 
                    String sql = "Select fullName, role "
                            + "From tbl_User "
                            + "Where username = ? "
                            + "and password = ? "
                            + "and role = 1";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, username);
                    stm.setInt(2, password);
                    // excute 
                    rs = stm.executeQuery();
                    if(rs.next()){
                        String fullName = rs.getString("fullName");
                        boolean isRole = rs.getBoolean("role");
                        result = new TblUserDTO(username, password, fullName, isRole);
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
