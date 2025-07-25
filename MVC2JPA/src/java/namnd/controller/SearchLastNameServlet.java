/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package namnd.controller;


import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import namnd.registration.Registration;
import namnd.registration.RegistrationBLO;
import namnd.registration.RegistrationDAO;
import namnd.registration.RegistrationDTO;

/**
 *
 * @author Hi
 */
@WebServlet(name="SearchLastNameServlet", urlPatterns={"/SearchLastNameServlet"})
public class SearchLastNameServlet extends HttpServlet {
   private final String SEARCH_PAGE = "search.jsp"; 
   // nếu không nhập thì reload phải là jsp vì nếu html thì lúc 
   // không nhập reload lại mất dòng chữ welcome
   private final String SEARCH_RESULT = "search.jsp";
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //1 get all information của user 
        String searchValue = request.getParameter("txtSearchvalue");
        String url = SEARCH_PAGE;
        try{
            if(!searchValue.trim().isEmpty()){
            //2 controller call method của Model 
            // 2.1 new DAO object 
//                RegistrationDAO dao = new RegistrationDAOv();

                RegistrationBLO blo = new RegistrationBLO();
            
            // 2.2 call method của DAO object 
//            dao.searchLastName(searchValue);
            // 3 Process
               List<Registration> result = blo.searchLastname(searchValue);
                url = SEARCH_RESULT;
                // tìm attribute nếu có thì cập nhập 
                // nếu chưa có tạo mới 
                // setAttribute cũng là new attribute hay setAttribute khi 
                // Attribute chưa có 
                // update ưu tiên vì update không có mới new 
                // chọn request Scope vì chỉ muốn lưu kết quả search tạm thời 
                // để hiển thị ra cho người dùng trong 1 request
                request.setAttribute("SEARCH_RESULT", result);
            }// search Vlue not empty 
//        }catch(ClassNotFoundException ex){
//            log("SQL: " + ex.getMessage());
//        }catch(SQLException ex){
//            log("Class Not Found: " + ex.getMessage());
        }
        finally{
            // ở dùng forward vì muốn lưu kết quả search để sang trang search.jsp 
            // hiển thị , chứ sendRedirect sẽ mất hết
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
