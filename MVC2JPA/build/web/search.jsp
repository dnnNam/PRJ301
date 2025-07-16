<%-- 
    Document   : search
    Created on : Jun 13, 2025, 8:21:18 AM
    Author     : Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--<%@page import="java.util.List" %> 
<%@page import="namnd.registration.RegistrationDTO" %>--%>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <font color="red">
        Welcome, ${sessionScope.USER_INFO.lastname}
        </font>
        <div style="display: flex ; align-items: center ; gap: 20px">
            <h1>Search Page</h1>
            <form action="DispatchServlet">
                <input type="submit" value="logout" name="btAction" />
            </form>
        </div>

        <form action="DispatchServlet" >
            Search Value<input type="text" name="txtSearchvalue" 
                               value="${param.txtSearchvalue}" /> <br/>     
            <input type="submit" value="Search"  name="btAction"/>
        </form> <br/> 
        <%-- khai báo biến searchValue , gán giá trị request parameter txtSearchValue
        --%>
        <c:set var="searchValue" value="${param.txtSearchvalue}" />

        <%-- kiểm tra searchValue có tồn tại hay không --%>
        <c:if test="${not empty searchValue}">
            <%-- lấy danh sách kết quả search thông qua attribute của request scope --%>
            <c:set var="result" value="${requestScope.SEARCH_RESULT}" />
            <%-- lấy lỗi password khi update thông qua attribute của request scope --%>
            <c:set var="error" value="${requestScope.CREATE_ERROR}"/>

             <%-- kiểm tra danh sách có tồn tại hay không --%>
             <%-- nếu có thì hiển thị ra table cho người dùng  --%>
            <c:if test="${not empty result}" >
                <table border="1">
                    <thead>
                         <%-- khai báo các cột --%>
                        <tr>
                            <th>No.</th>
                            <th>username</th>
                            <th>password</th>
                            <th>Full Name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>

                        </tr>
                    </thead>
                    <tbody>
                        <%-- forEach duyệt lần lượt từng phần từ , đưa giá trị vào  vùng nhớ 
                        trung gian check thực hiện và tiếp tục phần từ tiếp theo thế nên lúc xài 
                        phải sử biến trung gian , varStatus là con trỏ nên phải chấm count vì nếu 
                        không có count thì sẽ in ra địa chỉ--%>
                        
                        <c:forEach var="dto" items="${result}" varStatus="counter" >
                        <%-- chức năng update phải nằm trong foreach vì chức năng update mỗi user là 1 form (nhiều form)
                        vì nếu form nằm ngoài foreach thì tất cả user 1 form lúc đó các request parameter trùng tên 
                        lập tức lấy name làm tên mảng và đưa các value vào trong đó là mảng này không biết thứ tự 
                        dẫn tới update sai--%>    
                        <form action="DispatchServlet" method="POST">
                            <tr>
                                <td>
                                    <%-- số thứ tự không update --%>
                                    ${counter.count}
                                </td>
                                <td>
                                    <%-- username không upadte nhưng  user name (primary key)cần phải truyền về phía server 
                                    để update chính xác user đó , nếu không priamry thì sẽ update hàng loạt 
                                    để gửi cần control đặc biệt phải nằm trong form , user để type hidden 
                                    vì không muốn cho người dùng thay đổi --%>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername"
                                           value="${dto.username}"/>
                                </td>

                                <td>
                                    <%-- update password thế nên để type là text --%>
                                    <input type="text" name="txtPassword" value="${dto.password}" /> (6 - 20 chars) </br>
                                    <%-- kiểm tra có đúng user đó nhập sai password không --%>
                                    <c:if test="${requestScope.PK_USER eq dto.username && not empty error}">
                                        <font color="red">
                                            ${error.passwordLengthErr}
                                        </font>
                                    </c:if>
                                </td>
                                <td>
                                    ${dto.lastname}
                                </td>
                                <td>
                                    <%-- update role type checkBox , nếu kiểm tra role là dto.role ra true 
                                    thì sẽ thêm thuộc tính checked là ô đã tick --%>
                                    <input type="checkbox" name="chkAdmin" value="ON"  
                                           <c:if test="${dto.isAdmin}">
                                               checked="checked"
                                           </c:if>
                                           />

                                </td>
                                <td>
                                   <%-- 
                                   tạo đường url deleteLink hướng servlet điều phối 
                                    chức năng delete cần truyền 3 request parameter
                                     tất cả đều tới servlet điều phối 
                                    btAction để servlet điều phối thực hiện servlet chức năng 
                                    pk giúp xóa đúng  user dòng cần xóa 
                                    searchValue vì nếu xóa thành công mình cần refresh lại trang 
                                    là mình urlRewriting để gọi chức năng trước đó là search 
                                    chỉ có 1 ô nhập liệu nên mình bổ sung 1 request parameter là seachValue
                                   cuối tạo link delete--%>
                                    <c:url var="deleteLink" value="DispatchServlet">
                                        <c:param name="btAction" value="delete" />
                                        <c:param name="pk" value="${dto.username}"/>
                                        <c:param name="lastSearchName" 
                                                 value="${param.txtSearchvalue}"/>
                                    </c:url>
                                    <a href="${deleteLink}">Delete</a>
                                </td>
                                <td>
                                    <%-- update refresh lại trang thế nên cần bổ sung control của vào form 
                                    urlRewriting để gọi chức năng trước đó là search 
                                    chỉ có 1 ô nhập liệu nên mình bổ sung 1 request parameter là seachValue--%> 
                                    <input type="hidden" name="txtSearchvalue" 
                                           value="${searchValue}" />
                                    <%-- tạo nút lệnh hướng về điều phối để thức servlet chức năng tương ứng  --%>
                                    <input type="submit" value="Update" name="btAction" />
                                </td>
                            </tr>

                        </form>



                    </c:forEach>
                </tbody>
            </table>
        </c:if>

         <%-- kiểm tra danh sách có tồn tại hay không --%>  
          <%-- không tồn tại thì thông báo cho người dùng không có--%>  
        <c:if test="${empty result}">
            <h2>
                <font color="red" >
                No record is matched !!!
                </font>
            </h2>

        </c:if>

    </c:if>



    <%--
    <% 
        // service() --> _jspService là biến thể của service 
        String searchValue = request.getParameter("txtSearchvalue");
        if(searchValue != null){
              List<RegistrationDTO> result = 
                    (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");
                    if(result != null){
                     %>
                     
                     <table border="1">
                         <thead>
                             <tr>
                                 <th>No.</th>
                                 <th>Username</th>
                                 <th>Password</th>
                                 <th>Full name</th>
                                 <th>Role</th>
                             </tr>
                         </thead>
                         <tbody>
                             <%
                                int count = 0;
                                for(RegistrationDTO dto : result){
                                    %>
                                <tr>
                                 <td>
                                     <%= ++count %>
                                  </td>
                                  <td>
                                      <%= dto.getUsername() %>
                                      
                                  </td>
                                  <td>
                                       <%= dto.getPassword() %>
                                  </td>
                                  <td>
                                       <%= dto.getFullName() %>
                                  </td>
                                  <td>
                                      <%= dto.isRole() %>
                                  </td>
                               </tr>
                             <%
                                }// traverse dto is result
                             %>
                         </tbody>
                     </table>

                         <%
                        }else{
                        %>
                         <h2>
                             <font color ="red"> 
                                No Record is matched !!!
                             </font>
                          </h2>   
                        <%
                        }
            }// vì khi truy cập url ? parameter , nhưng url không có parameter (GET) 
        
        %>
    --%>
</body>
</html>
