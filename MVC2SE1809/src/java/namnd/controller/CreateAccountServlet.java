/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package namnd.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Properties;
import namnd.registration.RegistrationCreateError;
import namnd.registration.RegistrationDAO;
import namnd.registration.RegistrationDTO;
import namnd.util.MyApplicationConstants;

/**
 *
 * @author Hi
 */
@WebServlet(name="CreateAccountServlet", urlPatterns={"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
        // 1 get all user information 
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        // tạo nơi chứa lỗi 
        RegistrationCreateError errors = new RegistrationCreateError();
        // RegistrationCreateError javaBean mapping với các cột 
        //
        boolean foundErr = false;
        String url = siteMaps.getProperty(MyApplicationConstants.CreateFeature.CREATE_ACCOUNT_PAGE);
        try{
            //1.1 verify all user's error
            if(username.trim().length() < Integer.parseInt(siteMaps.getProperty(MyApplicationConstants.CreateFeature.MIN_LENGTH_USERNAME)) ||
                    username.trim().length() > Integer.parseInt(siteMaps.getProperty(MyApplicationConstants.CreateFeature.MAX_LENGTH_USERNAME))){
                foundErr = true;
                errors.setUsernameLengthErr
                    (siteMaps.getProperty(MyApplicationConstants.CreateFeature.USERNAME_LEN_ERR));
            }
            if(password.trim().length() < Integer.parseInt(siteMaps.getProperty(MyApplicationConstants.CreateFeature.MIN_LENGTH_PASSWORD))
                    || password.trim().length() >  Integer.parseInt(siteMaps.getProperty(MyApplicationConstants.CreateFeature.MAX_LENGTH_PASSWORD))){
                foundErr = true;
               errors.setPasswordLengthErr
                (siteMaps.getProperty(MyApplicationConstants.CreateFeature.PASSWORD_LEN_ERR));
            }else if (!confirm.trim().equals(password.trim())) {
                // nếu như password không đúng thì không cần phải kiểm tra 
                // confirm password
                foundErr = true;
                errors.setConfirmNotMatched(siteMaps.getProperty(MyApplicationConstants.CreateFeature.CONFIRM_PASSWORD_ERR));
            }
            
            if (fullname.trim().length() < 
                    Integer.parseInt(siteMaps.getProperty(MyApplicationConstants.CreateFeature.MIN_LENGTH_FULLNAME))|| fullname.trim().length() > 
                    Integer.parseInt(siteMaps.getProperty(MyApplicationConstants.CreateFeature.MAX_LENGTH_FULLNAME))) {
                foundErr = true;
                errors.setFullNameLengthErr(siteMaps.getProperty(MyApplicationConstants.CreateFeature.FULLNAME_LENGTH_ERR));
            }
            if(foundErr){
                // ở đây chon request Scope vì nó chỉ lưu tạm thời để show ra 
                // kết quả cho người dùng, thường thì show ra chỉ đọc chứ không cần 
                // thêm xóa sửa ta nên chọn request parameter , nhưng ta đang dứng 
                // phía server nên chọn attribute 
               
                request.setAttribute("CREATE_ERRORS", errors);
            }else{
                // controller sẽ call method của model 
                // 2.1 new DAO Object 
                RegistrationDAO dao = new RegistrationDAO();
                // 2.2 call methods từ DAO object 
                RegistrationDTO dto = new RegistrationDTO(username, password, fullname, false);
                boolean result = dao.createAccount(dto);
                // 3 process
                if(result){
                    url = MyApplicationConstants.CreateFeature.LOGIN_PAGE;
                }// acccount is created
           }// no error
        }catch(ClassNotFoundException ex){
            
            log("Class Not Found: " + ex.getMessage());
        }catch(SQLException ex){
            // dưới này là sẽ bắt 1 lỗi hệ thống, sau khi hệ thống thực thi  
            // trường hợp này là bắt lỗi trùng account 
            // sau dao.createAcccount xảy ra lỗi 
            // catch sẽ bắt lại kiểm trong log có từ duplicate không 
            // nếu có thì lưu lỗi vào trong attribute truyền sang jsp hiển thị 
            // lỗi cho người dùng 
            String msg = ex.getMessage();
            log("SQL" + msg);
            if(msg.contains("duplicate")){
                errors.setUsernameIsExisted(username + MyApplicationConstants.CreateFeature.USERNAME_EXISTED_ERR);
                request.setAttribute("CREATE_ERRORS", errors);
            }
   
        }finally{
            // dùng requestDispatcher foward attribute và request parameter 
            // sang create Account để hiển thị lỗi và hiển thị lại kết quả đã 
            // nhập sai 
            // có lỗi và show lỗi người ta nhìn sai gì mới sửa 
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
