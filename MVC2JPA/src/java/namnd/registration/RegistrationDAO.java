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
// điều số 1 khai bóa biến và gán null 
// phải đóng tất cả các đối tượng bằng mọi cách , khai báo chiều thuận đóng chiều ngược 
// mơi thực hiện xử lý 
// mỗi mệnh đề câu lệnh SQl phải được viết trên 1 dòng 
// trước khi xuống đòng phải chèn 1 khoảng trắng 
// không thôi chúng ta có 1 lỗi syntax From near
// tất cả mọi thứ tên cột tên database copy từ database 
// không thôi sẽ bị lỗi object not found
// có 2 loại statement là Statement và PreparedStatement 
// Statement : đa số không có mệnh đề where 
// PreparedStatement : có mệnh đề where và truyền tham số 
// mỗi dấu chấm hỏi có tên là placeholder 
// insert delete update kết quả nhận được là 1 số nguyên thể hiện sô dòng tương tác dưới database int >= 0
// dùng select  là một resultSet là 1 con trỏ là tới 1 danh sách  tường ứng row từ số 2 tới n -1 
// đầu tiên chứa BOF , EOF , (begin of file , end of file)
// sau khi thực thi con trỏ luôn trỏ xuống dòng đầu tiên , dịch chuyển con trỏ phỉa dùng phương thức next 
// next còn chứa dữ liệu trả ra true không còn chứa dữ liệu trả ra false
// đặc tính của con trỏ là foward only ,nếu không thỏa dữ liệu là BOF và EOF 
// có không có resultSet luôn luôn tạo ra cho dù dưới database thỏa hay không thỏa 
// nếu viết sql biết trả 1 một dòng hay nhiều dòng nếu 1 dùng if , nếu nhiều while
public class RegistrationDAO implements Serializable {

    public RegistrationDTO checkLogin(String username, String password)
            throws SQLException, ClassNotFoundException {
        RegistrationDTO result = null;

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. model connect DB 
            con = DBHelper.makeConnection();
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
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");

                    result = new RegistrationDTO(username, null, fullName, role);
                }
                // next có thì bằng true
            } // connection is an available 
        } finally {
            
            //khai báo theo chiều thuận đóng theo chiều ngược
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
    
    // 
    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }

    public void searchLastName(String searchValue)
            throws SQLException, ClassNotFoundException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. model connect DB 
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. model truy vân dữ liệu từ database
                //2.1 write SQL String

                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname Like ?";

                //2.2 nạp câu lệnh vào bộ nhớ là giốn tô đên SQl (create Statement object) và check cú pháp 
                // dấu chấm hỏi SQl từ trái sang phải , vị trí thiết lập tính từ số 1 
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");

                // statement  b1 load câu lệnh vào bộ nhớ , b2 check cú phấp , B3 execute trả ra kết quả 
                // prepareStatement  lần thứ 2 không cần load và check syntax nữa 
                // về nhà đọc callable statement 
                //2.3 thực thi query 
                rs = stm.executeQuery();
                //3 model get data from ....... then 
                // model sets data to properties of model 
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    RegistrationDTO dto = new RegistrationDTO(username,
                            password, fullName, role);
                    // set 
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    }// account is not available
                    this.accounts.add(dto);
                }// traverse each row in table 
                // next có thì bằng true
            } // connection is an available 
        } finally {
            if (rs != null) {
                rs.close();
            }
            // stm khai báo sau phải đóng trước 
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }

    }

    public boolean deleteAccount(String username)
            throws SQLException, ClassNotFoundException {
        boolean result = false;

        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. model connect DB 
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. model truy vân dữ liệu từ database
                //2.1 write SQL String
                // xóa tất cả các dòng với điều kiện giá trị truyền username chính xác
                String sql = "Delete From Registration "
                        + "Where username = ?";
                //2.2 nạp câu lệnh vào bộ nhớ là giốn tô đên SQl (create Statement object) và check cú pháp 
                // dấu chấm hỏi SQl từ trái sang phải , vị trí thiết lập tính từ số 1 
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                // statement  b1 load câu lệnh vào bộ nhớ , b2 check cú phấp , B3 execute trả ra kết quả 
                // prepareStatement  lần thứ 2 không cần load và check syntax nữa 
                // về nhà đọc callable statement 
                //2.3 thực thi query 
                int effectRows = stm.executeUpdate();
                //3 model get data from ....... then 
                // model sets data to properties of model 
                // check số dòng hiệu lực 
                if (effectRows > 0) {
                    result = true;
                }
                // next có thì bằng true
            } // connection is an available 
        } finally {

            // stm khai báo sau phải đóng trước 
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean updateAccount(String username, String password, String isAdmin)
            throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            // 1 connect with database 
            con = DBHelper.makeConnection();
            if (con != null) {
                // 2.1 create SQL String 
                String sql = "Update Registration "
                        + "Set password=? , isAdmin=? "
                        + "Where username=?";
                //2.2 nạp câu SQl vào object Statement 
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                // cần kiểm tra vì input type checkbox 
                // nếu là admin thì có thuộc tính checked 
                // thì server gửi cặp name và value 
                // nhưng nếu không phải admin thì không có thuộc tính checked 
                // thì server sẽ nhận giá trị là null 
                if (isAdmin != null && isAdmin.equals("ON")) {
                    stm.setString(2, "1");
                } else {
                    stm.setString(2, "0");
                }
                stm.setString(3, username);
                // 2.3 excute Query
                // sau khi update sẽ trả ra số dòng bị ảnh hưởng bởi câu SQL
                int effectRows = stm.executeUpdate();
                // 3 process 
                if (effectRows > 0) {
                    result = true;
                }

            }// connection is an available
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    // field nào không có truyền giá trị mặc định cho nó 
    // thì phải cân nhắc truyền object 
    // không truyền quá nhiều tham số 
    public boolean createAccount(RegistrationDTO dto)
            throws ClassNotFoundException, SQLException {
        boolean result = false;

        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. model connect DB 
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. model truy vân dữ liệu từ database
                //2.1 write SQL String

                String sql = "INSERT INTO Registration("
                        + "username, password, lastname, isAdmin"
                        + ") Values("
                        + "?, ?, ?, ?"
                        + ")";
                //2.2 nạp câu lệnh vào bộ nhớ là giốn tô đên SQl (create Statement object) và check cú pháp 
                // dấu chấm hỏi SQl từ trái sang phải , vị trí thiết lập tính từ số 1 
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getUsername());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getFullName());
                stm.setBoolean(4, dto.isRole());

                // statement  b1 load câu lệnh vào bộ nhớ , b2 check cú phấp , B3 execute trả ra kết quả 
                // prepareStatement  lần thứ 2 không cần load và check syntax nữa 
                // về nhà đọc callable statement 
                //2.3 thực thi query 
                int effectRows = stm.executeUpdate();
                //3 model get data from ....... then 
                // model sets data to properties of model 
                if (effectRows > 0) {
                    result = true;
                }
                // next có thì bằng true
            } // connection is an available 
        } finally {

            // stm khai báo sau phải đóng trước 
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }  
    return result;
    }
}