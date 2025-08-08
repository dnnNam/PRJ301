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
import java.util.List;
import java.util.Properties;
import namnd.registration.RegistrationCreateError;
import namnd.registration.RegistrationDAO;
import namnd.registration.RegistrationDTO;
import namnd.util.MyApplicationConstants;

/**
 *
 * @author Hi
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {

  

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties)context.getAttribute("SITEMAPS");
        // getUserInfor
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String isAdmin = request.getParameter("chkAdmin");
        String searchValue = request.getParameter("txtSearchvalue");
        String url = siteMaps.getProperty(MyApplicationConstants.UpdateFeature.ERROR_PAGE);
        boolean foundErr = false;
        RegistrationCreateError errors = new RegistrationCreateError();
        try {
            // phải kiểm tra password để khớp với chức năng create Account 
            if (password.trim().length() < 6 || password.trim().length() > 20) {
                foundErr = true;
                errors.setPasswordLengthErr("password is required from 6 to 20 characters");
            }
            //2. controller call method of model 
            //2.1 new dao object
            RegistrationDAO dao = new RegistrationDAO();

            if (foundErr) {
                url = siteMaps.getProperty(MyApplicationConstants.UpdateFeature.SEARCH_PAGE); // qua search.jsp để hiển thị lỗi
                // gọi lại phương thức search 
                //lấy danh sách kết quả search
                dao.searchLastName(searchValue);
                List<RegistrationDTO> listSearch = dao.getAccounts();

                // tìm xem user nào bị nhập sai password 
                // set lại password sai cho user đó
                if (listSearch != null) {
                    for (RegistrationDTO dto : listSearch) {
                        if (dto.getUsername().equals(username)) {
                            dto.setPassword(password);
                        }
                    }
                }
                // lưu lại danh sách mà có user nhập sai passowrd 
                // để hiển thị kết quả nhập sai để cho user biết sai để sửa 
                request.setAttribute("SEARCH_RESULT", listSearch);

                // lưu lại primary key của user đó để thông báo đúng user đó 
                request.setAttribute("PK_USER", username);
                // lưu lỗi để hiển thị 
                request.setAttribute("CREATE_ERROR", errors);
                // bắt buộc xài forward chứ không phải sendirect vì nếu sendReDirect
                // response cơ chế stateless ngắt kết nối với client , xóa reponse object 
                // và request object, xài forward để lưu  các attribute để hiển thị
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
                // sau phải return luôn vì sau dòng rd.forward(request, response) trong servlet
                // code vẫn chạy dẫn đến lỗi IllegalStateException , vì response đã commit
                return;
            }// nếu có lỗi
            //2.2 call method of dao object
            boolean result = dao.updateAccount(username, password, isAdmin);
            //3. process
            if (result) {
                //refresh --> call previous functions again
                //--> remind --> add request parameters based on how many input controls
                url = "DispatchServlet"
                        + "?btAction=Search"
                        + "&txtSearchvalue=" + searchValue;
                // dùng sendRedirect request Scope có 7 request Parameter vì 
                //muốn hủy bỏ 5 request parameter của Update
                // vì có nút lệnh btAction tên giống nhau biến thành mảng không biết thứ tự 
                // sẽ không biết thực hiện chức năng nào
                response.sendRedirect(url);
                // sau phải return luôn vì sau dòng response.sendRedirect(url);
                // code vẫn chạy dẫn đến lỗi IllegalStateException , vì response đã commit
                return;
            }
        } catch (ClassNotFoundException ex) {
            log("Class Not Found: " + ex.getMessage());
        } catch (SQLException ex) {
            log("SQL: " + ex.getMessage());
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
