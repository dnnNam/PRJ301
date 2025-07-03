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
import namnd.registration.RegistrationCreateError;
import namnd.registration.RegistrationDAO;
import namnd.registration.RegistrationDTO;

/**
 *
 * @author Hi
 */
@WebServlet(name="UpdateServlet", urlPatterns={"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {
    private final String ERROR_PAGE = "error.html";
    private final String SEARCH_PAGE = "search.jsp";
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
        // getUserInfor
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String isAdmin = request.getParameter("chkAdmin");
        String searchValue = request.getParameter("lastSearchValue");
        String url = ERROR_PAGE;
        boolean foundErr = false;
        RegistrationCreateError errors = new RegistrationCreateError();
        try  {
            // phải kiểm tra password để khớp với chức năng create Account 
            if(password.trim().length() < 6 || password.trim().length() > 20){
                foundErr = true;
                errors.setPasswordLengthErr("password is required from 6 to 20 characters");
            }

            // 2.1 call DAO object 
            RegistrationDAO dao = new RegistrationDAO();
            // 2.2 controller call method DAO object
            
            if(foundErr){
                url = SEARCH_PAGE;
                dao.searchLastName(searchValue);
                List<RegistrationDTO> listSearch = dao.getAccount();
                
                for (RegistrationDTO dto: listSearch) {
                    if(dto.getUsername().equals(username)){
                        dto.setPassword(password);
                    }
                }
                request.setAttribute("SEARCH_RESULT", listSearch);
                // update lại SEARCH RESULT để hiển thị lại kết quả nhập sai 
                // lưu username để biết rõ dòng nào bị lỗi update chính xác 
                // dòng đó 
                request.setAttribute("ERROR_USER", username);
                // lưu lỗi để hiển thị 
                request.setAttribute("CREATE_ERROR", errors);
                request.setAttribute("SEARCH_VALUE", searchValue);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
                return;
            }
            boolean result = dao.updateAccounts(username, password, isAdmin);
            if(result){
                // refresh 
                // remind bổ sung các request parameter vào URL 
                url = "DispatchServlet?"
                       + "btAction=Search" 
                       + "&txtSearchvalue=" + searchValue; 
                response.sendRedirect(url);
                return;
            }
        }catch(ClassNotFoundException ex){
            log("SQL: " + ex.getMessage());
        }catch(SQLException ex){
            log("Class Not Found: " + ex.getMessage());
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
