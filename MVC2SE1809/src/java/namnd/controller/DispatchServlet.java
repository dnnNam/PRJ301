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
import java.util.Properties;
import namnd.util.MyApplicationConstants;

/**
 *
 * @author Hi
 */
@WebServlet(name="DispatchServlet", urlPatterns={"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {
//    private final String LOGIN_PAGE = "";
//    private final String LOGIN_CONTROLLER = "LoginServlet";
//    private final String SEARCH_LASTNAME_CONTROLLER = "SearchLastNameServlet";
//    private final String DELETE_ACCOUNT_CONTROLLER = "DeleteServlet";
//    private final String CHECK_ACCOUNT_CONTROLLER = "CheckAccountServlet";
//    private final String UPDATE_ACCOUNT_CONTROLLER = "UpdateAccountServlet";
//    private final String CREATE_ACCOUNT_CONTROLLER = "CreateAccountServlet";
//    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
        //1. người ta đã click cái gì 
        String button = request.getParameter("btAction");
        // url pattern không có parameter vì nên button = null
//        String url = LOGIN_PAGE;
        String url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGIN_PAGE);
        response.setContentType("text/html;charset=UTF-8");
        try{
           if(button == null){ // first request
               // nếu check cookies không tồn tại là lần thứ nhất 
               // nếu tồn tại chắc chắn không phải lần thứ nhất 
               url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.CHECK_ACCOUNT_CONTROLLER);
           }else{
               switch (button) {
                   case "Login":
                       url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGIN_CONTROLLER);
                       break;
                   case "Search":
                       url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.SEARCH_LASTNAME_CONTROLLER);
                       break;
                   case "delete":
                       url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.DELETE_CONTROLLER);
                       break;
                   case "Update":
                       url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.UPDATE_CONTROLLER);
                       break;
                   case "Create Account":
                       url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.CREATE_ACCOUNT_CONTROLLER);
                       break;
                   case "logout":
                       url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGOUT_CONTROLLER);
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
