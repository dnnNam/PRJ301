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

/**
 *
 * @author Hi
 */
@WebServlet(name="DispatchServlet", urlPatterns={"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {
    private final String LOGIN_PAGE = "login.html";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String DELETE_CONTROLLER = "DeleteServlet";
    private final String SEARCH_LASTNAME_CONTROLLER = "SearchLastNameServlet";
    private final String UPDATE_CONTROLLER = "UpdateServlet";
    private final String STARTUP_CONTROLLER = "StartUpServlet";
    private final String CREATE_ACCOUNT_CONTROLLER = "AddAccountServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    
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
        // tất cả các button có name là btAction ,
        // check value để thực hiện chức năng tương ứng 
        String button = request.getParameter("btAction");
        String url = LOGIN_PAGE;
        try{
           if(button == null){ // request đầu tiên 
               // lần đầu tiên check cookies đã có tồn tại hay chưa 
               // nếu chưa có thì chắc chắn là lần thứ nhất 
               url = STARTUP_CONTROLLER; 
               
           }else{
               switch (button) {
                   case"Login":
                       url = LOGIN_CONTROLLER;
                       break;
                   case"Search":
                       url = SEARCH_LASTNAME_CONTROLLER;
                       break;
                   case"Delete":
                       url = DELETE_CONTROLLER;
                       break;
                   case"Update":
                       url = UPDATE_CONTROLLER;
                       break;
                   case"CreateAccount":
                       url = CREATE_ACCOUNT_CONTROLLER;
                       break;
                   case"logout":
                       url = LOGOUT_CONTROLLER;
                       break;
                       
                   default:
                       throw new AssertionError();
               }
           } 
           
        }finally{
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
