/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package namnd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import namnd.registration.RegistrationCreateError;
import namnd.registration.RegistrationDAO;
import namnd.registration.RegistrationDTO;

/**
 *
 * @author Hi
 */
@WebServlet(name="AddAccountServlet", urlPatterns={"/AddAccountServlet"})
public class AddAccountServlet extends HttpServlet {
    private final String LOGIN_PAGE = "login.html";
    private final String ERROR_PAGE = "createAccount.jsp";
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // 1 get all information user 
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullName = request.getParameter("txtFullName");
        boolean foundErr = false; // check xem có lỗi không 
        RegistrationCreateError errors = new RegistrationCreateError();
        // là 1 java bean để hứng lỗi , khác với DTO không cần mapping các 
        // cột table dưới database
        String url = ERROR_PAGE;
        try{
           // có 4 lỗi người dùng và 1 lỗi từ hệ thống 
           //2 check user errors 
           if(username.trim().length() < 6 || username.trim().length() > 12){
               foundErr = true;
               errors.setUsernameLengthErr("Username is required from 6 to 12 characters");
           }
           if(password.trim().length() < 6 || password.trim().length() > 20){
               foundErr = true;
               errors.setPasswordLengthErr("Password is required from 6 to 20 characters");
           }else if(!confirm.trim().equals(password.trim())){
               // nếu như password không đúng thì không cần phải kiểm tra confirm password nữa
               foundErr = true;
               errors.setConfirmNotMatched("confirm must be match password");
           }
           if(fullName.trim().length() < 2 || fullName.trim().length() > 50){
               foundErr = true;
               errors.setUsernameLengthErr("Username is required from 2 to 50 characters");
           }
           
           // nếu như có lỗi thì lưu lỗi hiển thị ra cho người dùng 
           if(foundErr){
               request.setAttribute("CREATE_ERRORS", errors);
           }else{ // no error 
               // 3 call method của of Model(DAO)
               RegistrationDAO dao = new RegistrationDAO();
               RegistrationDTO dto = new RegistrationDTO(username, password, fullName, false);
               boolean result = dao.createAccount(dto);
               // 4 process 
               if(result){
                   url = ERROR_PAGE;
               }
           }
        }catch(ClassNotFoundException ex){
            
            log("Class Not Found: " + ex.getMessage());
        }catch(SQLException ex){
            String msg = ex.getMessage();
            log("SQL: " + msg);
            if(msg.contains("duplicate")){
                errors.setUsernameIsExisted(username + "is Existed");
                request.setAttribute("CREATE_ERRORS", errors);
            }
            
        }finally{
            
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
