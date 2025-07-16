    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package namnd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import namnd.registration.RegistrationDAO;
import namnd.registration.RegistrationDTO;

/**
 *
 * @author Hi
 */
@WebServlet(name = "CheckAccountServlet", urlPatterns = {"/CheckAccountServlet"})
public class CheckAccountServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String SEARCH_PAGE = "search.jsp";

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
        // 1 get Cookies 
        // lấy danh sách cookies
        Cookie[] cookies = request.getCookies(); // đọc
        String url = LOGIN_PAGE;
        try {
            //2 check existed cookies 
            // 2.1 get username và pasword 
            if (cookies != null) {
                //  mỗi cookie 4 KB mỗi domain lưu 20 cookie 
                // nếu có add lố 21 cookie thì hệ điều hành sẽ dùng thuật toán (LRU)
                // least Recently Used sẽ tìm được cookie ít sử dụng gần nhất xóa 
                // và thêm cookie mới dô
                // lấy cookies gần nhất 
                Cookie recentCookies = cookies[cookies.length - 1];
                String username = recentCookies.getName();
                String password = recentCookies.getValue();
                // 2.2 controller call method của model 
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO result = dao.checkLogin(username, password);
                // 2.3 process 
                if (result != null) {
                    // đăng nhập thành chuyển sang search
                    url = SEARCH_PAGE;
                    // lưu session để hiển thị welcome
                    HttpSession session = request.getSession();
                    session.setAttribute("USER_INFO", result);
                }
            }
        } catch (SQLException ex) {
            log("SQL" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            log("Class not found" + ex.getMessage());
        } finally {
            // logout chuyển sang trang login thôi chứ không cần lưu thông tin 
            // hiển thị lên login nên dùng sendRedirect
            response.sendRedirect(url);
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
