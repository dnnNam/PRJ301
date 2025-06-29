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
import java.util.ArrayList;
import java.util.List;
import namnd.util.DBHelper;

/**
 *
 * @author Hi
 */
public class RegistrationDAO implements Serializable {
    // first step implement serializable because all object inside server convert 
    // format bit stream , bytestream 

    // method check account have been existed 
    public RegistrationDTO checkLogin(String username, String password)
            throws ClassNotFoundException, SQLException {
        RegistrationDTO result = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            // 1 model connect DB
            con = DBHelper.makeConnection();
            // 2.1write SQL String 
            if (con != null) {
                //2. model truy vân dữ liệu từ database
                //2.1 write SQL String

                String sql = "Select lastname , isAdmin "
                        + "From Registration "
                        + "Where username = ? "
                        + "And password = ?";
                //2.2 nạp câu lệnh vào bộ nhớ là giốn tô đên SQl (create Statement object) và check cú pháp 
                // dấu chấm hỏi SQl từ trái sang phải , vị trí thiết lập tính từ số 1 
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                // statement  b1 load câu lệnh vào bộ nhớ , b2 check cú phấp , B3 execute trả ra kết quả 
                // prepareStatement  lần thứ 2 không cần load và check syntax nữa 
                // về nhà đọc callable statement 
                //2.3 thực thi query 
                rs = stm.executeQuery();
                //3 model get data from ....... then 
                // model sets data to properties of model 
                if (rs.next()) {
                    String fullname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    result = new RegistrationDTO(username, null, fullname, role);
                    
                }
                // next có thì bằng true
            }// connection is an avaiable
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    // DTO do DAO tạo 
    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccount() {
        return accounts;
    }

    // searchLastName 
    public void searchLastName(String searchValue)
            throws SQLException, ClassNotFoundException {
        // tạo tên biê 
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            // 1 model connect DB 
            con = DBHelper.makeConnection();
            // 2 model truy vấn dữ liệu dưới database
            // 2.1 write SQl String 
            if (con != null) {
                String sql = "Select username , password , lastname , isAdmin "
                        + "From Registration "
                        + "Where lastname Like ?";
                //2.2 nạp câu lệnh vào bộ nhớ 
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                // 2.3 execute query 
                rs = stm.executeQuery();
                // 3 model set data to properties model 
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    RegistrationDTO dto = new RegistrationDTO(username, password, fullname, role);
                    // set 
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    }
                    this.accounts.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }

    }
    
    
    // deleteAccount 
    
    public boolean deleteAccounts (String username)
    throws ClassNotFoundException , SQLException{
        boolean result = false;
        
        Connection con = null;
        PreparedStatement stm = null;        
        try {
            // 1 model connect với database 
            con = DBHelper.makeConnection();
            if(con != null){
                // 2 model query from database 
                // 2.1 write SQL String 
                String sql = "Delete From Registration " + 
                        "Where username = ?";
                // 2.2 nạp vào object Statement 
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                // 2.3 thực thi
                // vì thực delete 1 dòng dưới table sẽ trả ra số nguyên > 0
                int effectRows = stm.executeUpdate();
                if(effectRows > 0){
                    result = true;
                }
            }// connection is an available
        } finally {
            if(stm != null){
                stm.close();
            }
            
            if(con != null){
                con.close();
            }
        }
        return result;    
    }
    
    
    // update 
    public boolean updateAccounts (String username , String password , String isAdmin)
    throws ClassNotFoundException , SQLException{
        boolean result = false;
        
        Connection con = null;
        PreparedStatement stm = null;        
        try {
            // 1 model connect với database 
            con = DBHelper.makeConnection();
            if(con != null){
                // 2 model query from database 
                // 2.1 write SQL String 
                String sql = "Update Registration " + 
                        "Set password = ? , isAdmin = ? "+
                        "Where username = ?";
                // 2.2 nạp vào object Statement 
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                // kiểm tra checkBox 
                if(isAdmin != null && isAdmin.equals("ON")){
                    stm.setString(2, "1");
                }else{
                    stm.setString(2, "0");
                }
                stm.setString(3, username);
                // 2.3 thực thi
                // vì thực delete 1 dòng dưới table sẽ trả ra số nguyên > 0
                int effectRows = stm.executeUpdate();
                if(effectRows > 0){
                    result = true;
                }
            }// connection is an available
        } finally {
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
