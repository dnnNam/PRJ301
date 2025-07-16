/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package namnd.util;

// kiểu dữ liệu của DBMS 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// varchar, datetime , int , float , bit 
// string ,          ,     , double , boolean
// để chuyển đổi các kiểu dữ liệu này là JDBC Driver 
// là danh sách liệt kê giúp chuyển đổi dữ liệu từ ngôn ngữ lập trình sang DBMS và ngược lại
// drive nó là chìa khóa để vô DBMS 
// nó là object tĩnh vì nó là danh sách liệt kê 
// nên xuất hiện DriverManager là thằng làm nhiệm vụ load jDBC drive lên 
// bộ nhớ và thực hiện chuyển đổi 
// số 1 là Driver Manager , số 2 JDBC Driver , số 3 JDBC API 
// toàn bộ gói package là java.sql
// là sql server là 1 container vì có cấu hình port 1433
// class not found exception trường hợp sau 
// lỗi driver chưa add vào project , kiểm tra đúng tên hay chưa 
// thứ 2 lỗi chuỗi viết sai 
public class DBHelper {
    //-- JDBC API đó là 1 thư viện hay là framework để hỗ trợ kết nối với DBMS 
    
    public static Connection makeConnection()
        throws ClassNotFoundException, SQLException{
        //1. load Driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //2. Create connection string to connect container 
        // do http chung 1 chuẩn 1.1 nên không cần chỉ định tên container 
        //nhưng jdbc phụ thuộc DBMS vì có nhiều loại khác nhau(Oracle, MySQL, MS Server, PostgreSQL)
        String url = "jdbc:sqlserver://"
                 + "localhost:1433;" +
                "databaseName=PRJ301SE1809";
        Connection con = DriverManager.getConnection(url, "sa" , "12345");
        // syntax : jdbc:sqlserver//ip:port;databaseName... 
        // 3. Open connect using Driver Manager 
        return con;
        }
    
}
