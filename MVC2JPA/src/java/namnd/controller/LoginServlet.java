/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package namnd.controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import namnd.registration.Registration;
import namnd.registration.RegistrationBLO;
import namnd.registration.RegistrationDAO;
import namnd.registration.RegistrationDTO;

/**
 *
 * @author Hi
 */
public class LoginServlet extends HttpServlet {
    // các địa chỉ URL phải thành biến hằng số
    private final String SEARCH_PAGE = "search.jsp";
    private final String INVALID_PAGE = "invalid.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        // get toàn bộ thông tin người dùng(get all user's information)
        
        String url = INVALID_PAGE;
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        try {
            //2. controller call method của model 
            // 2.1 new DAO object 
//            RegistrationDAO dao = new RegistrationDAO();
            RegistrationBLO blo = new RegistrationBLO();
            // 2.2 call method của DAO object 
//            RegistrationDTO result = dao.checkLogin(username, password);
              Registration result = blo.checkLogin(username, password);
            // 3. process 
            if (result != null) {
                // chọn session vì sẽ có nhiều hành động (nhiều yêu cầu) của 1 người dùng
                // muốn giữ thông tin người dùng trong suốt thời gian hoạt động 
                
                HttpSession session = request.getSession();
                // lưu thông tin người dùng để hiển thị dòng welcome
                session.setAttribute("USER_INFO", result); // dynamic
                url = SEARCH_PAGE;
                // lưu cookie 
                //tạo cookie dựa username , password
                Cookie cookie = new Cookie(username, password);
                // set thời gian tồn tại của cookie
                cookie.setMaxAge(60 * 3);
                // add cookie vào response object gửi cho lưu dưới client
                response.addCookie(cookie);
            }// login successfull
            // ở là servlet thế nên phải phải dùng catch bắt lỗi chứ không đươc 
            // throws lỗi 
            // vì các phương thức trong servlet object là (signature method) 
            // không được phép thêm xóa sửa
//        }catch(ClassNotFoundException ex){
//            // bắt class notfound vì connect DB Class.forName() ném ra lỗi này 
//            log("SQL: " + ex.getMessage());
//        }catch(SQLException ex){
//            // lỗi SQLException trong Driver.getConnection() có ném ra lỗi này 
//            log("Class Not Found: " + ex.getMessage());
        } finally {
            // response.sendRedirect(url);
            /// code che đường truyền 
            // ở đây dùng vì mình muốn truyền attribute USER_INFOR sang search.jsp 
            // để hiển thị welcome , fullname , nếu dùng response.sendRedirect 
            // response object sẽ send response về cho browser kích hoạt cơ chế stateless 
            // từ server ngắt kết nối với client và xóa toàn bộ request object và response object 
            // có trong container
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
