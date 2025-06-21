/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package namnd.controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import namnd.registration.RegistrationDAO;
import namnd.registration.RegistrationDTO;

/**
 *
 * @author Hi
 */
@WebServlet(name="SearchLastNameServlet", urlPatterns={"/SearchLastNameServlet"})
public class SearchLastNameServlet extends HttpServlet {
   private final String SEARCH_PAGE = "search.jsp";
   private final String SEARCH_RESULT = "search.jsp";
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
        // 1 get all information của user 
        String searchValue = request.getParameter("txtSearchvalue");
        String url = SEARCH_PAGE;
        try {
           if(!searchValue.trim().isEmpty()){
             // 2 controller call method của model 
             //2.1 new DAO object 
               RegistrationDAO dao = new RegistrationDAO();
             // 2.2 call method 
             dao.searchLastName(searchValue);
             // 3 process result 
               List<RegistrationDTO> result = dao.getAccount();
               url = SEARCH_RESULT;
               // in order to send data to servletShow 
               // use attribute  don't use parameter 
               // because handle in here server but paramter client -> server 
               // through request , 1 reason data in DTO type List -> String 
               // use attribute can modify , parameter (read only)
               request.setAttribute("SEARCH_RESULT", result);
               // setAttribute , if this attribute already exists , update it 
               // if doest not exist then create it 
               // setAttribute include new Attribute hay setAttribute 
           }// user typed invalid value => go to SEARCH PAGE again
           
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        finally{
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
