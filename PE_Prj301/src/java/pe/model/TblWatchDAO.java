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
import java.util.ArrayList;
import java.util.List;
import pe.utils.DbUtils;

/**
 *
 * @author Hi
 */
public class TblWatchDAO implements Serializable {
     
    private List<TblWatchDTO> accounts;
    public  List<TblWatchDTO> getAccounts (){
        return accounts;
    }
    
    public void searchQuantity(int minValue , int maxValue)
    throws ClassNotFoundException , SQLException{
      
            Connection con = null;
            PreparedStatement stm = null;
            ResultSet rs = null;
            try {
                // connect
                con = DbUtils.getConnection();
                if(con != null){
                    // query 
                    String sql = "Select id , name , brandName , price , size , quantity , description "
                            + "From tbl_Watch "
                            + "Where quantity >= ? "
                            + "and quantity <= ?";
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, minValue);
                    stm.setInt(2, maxValue);
                    // excute 
                    rs = stm.executeQuery();
                    while(rs.next()){
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String brandName = rs.getString("brandName");
                        float price = rs.getFloat("price");
                        int size = rs.getInt("size");
                        int quantity = rs.getInt("quantity");
                        String description = rs.getString("description");
                        TblWatchDTO result = new TblWatchDTO(id, name, brandName, price, size, quantity, description);
                        if(accounts == null){
                            this.accounts = new ArrayList<>();
                        }
                        this.accounts.add(result);
                    }
                }  
            
            }finally {
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
     }      
    
    
    public boolean updateWatch(String description , float price , int quantity , int pk)
    throws ClassNotFoundException , SQLException{
            Connection con = null;
            PreparedStatement stm = null;
            boolean result = false;
            try {
                // connect
                con = DbUtils.getConnection();
                if(con != null){
                    // query 
                    String sql = "Update tbl_Watch "
                            + "set description = ? , price = ? , quantity = ? "
                            + "where id =?";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, description);
                    stm.setFloat(2, price);
                    stm.setInt(3, quantity);
                    stm.setInt(4, pk);
                    // excute 
                    int effectRows = stm.executeUpdate();
                    
                    if(effectRows > 0){
                        result = true;
                    }
                        
                }  
            
            }finally {
                
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
