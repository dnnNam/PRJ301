/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package namnd.controller;

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
import namnd.registration.RegistrationDAO;
import namnd.util.MyApplicationConstants;

/**
 *
 * @author Hi
 */
@WebServlet(name="DeleteServlet", urlPatterns={"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {
//    private final String ERROR_PAGE = "error.html";
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
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
        // 1 lấy toàn thông tin người dùng 
        String username = request.getParameter("pk");
        String searchValue = request.getParameter("lastSearchName");
        String url = siteMaps.getProperty(MyApplicationConstants.UpdateFeature.ERROR_PAGE);
        try {
            // 2 controller call method của model 
            // 2.1 new DAO object 
            RegistrationDAO dao = new RegistrationDAO();
            // 2.2 controller call method từ DAO object 
            boolean result = dao.deleteAccount(username);
            // process
            if(result){
                // refesh --> call previous functionc again
                // --> remind -> adđ request parameter base on controller feature before
                url = "DispatchServlet"
                        +"?btAction=Search"
                        +"&txtSearchvalue=" + searchValue;
            }// detele successfully
        }catch(ClassNotFoundException ex){
            log("SQL: " + ex.getMessage());
        }catch(SQLException ex){
            log("Class Not Found: " + ex.getMessage());
        }
        finally{
            // ở đây dùng sendRedirect vì trong request Scope hiện tại có tới 
            // 5 request paramater : mà quan trọng trong đó có 2 request paramater btAction 
            // tên giống nhau lập tức tạo thành mảng không biết thứ là sẽ không biết thực hiện
            // nên dùng sendRedirect để hủy 3 request parameter của delete đi 
            response.sendRedirect(url);
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
