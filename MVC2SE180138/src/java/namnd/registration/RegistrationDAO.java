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
    public boolean checkLogin(String username, String password)
            throws ClassNotFoundException, SQLException {
        boolean result = false;
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

                String sql = "Select username "
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
                    result = true;
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
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            // 1 connect với DB 
            con = DBHelper.makeConnection();
            if (con != null) {
               //2.1 create SQL String 
               String sql ="Select username, password, lastname, isAdmin "
                       +"From Registration "
                       +"Where lastname Like ?";
               //2.2 create StateMent object 
               stm = con.prepareStatement(sql);
               stm.setString(1, "%" + searchValue + "%");
               // 3 execute 
               rs = stm.executeQuery();
               // process result 
               while(rs.next()){
                   // get data from ResultSet
                   String username = rs.getString("username");
                   String password = rs.getString("password");
                   String fullname = rs.getString("lastname");
                   Boolean role = rs.getBoolean("isAdmin");
                   // set data to DTO properties 
                   RegistrationDTO dto = new RegistrationDTO(username, password, fullname, role);
                   if(this.accounts == null){
                       this.accounts = new ArrayList<RegistrationDTO>();
                       // nhớ new arraylist nha không vá 21 cái hàm á 
                   }// account are unvaiable
                   this.accounts.add(dto);
               }// traversal all Result set 
            }// end connection is available

        } finally {
            if(rs != null){
                rs.close();
            }
            
            if(stm != null){
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}
