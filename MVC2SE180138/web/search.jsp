<%-- 
    Document   : search
    Created on : Jun 14, 2025, 5:43:09 PM
    Author     : Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--<%@page import="java.util.List" %>
<%@page import="namnd.registration.RegistrationDTO" %> --%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <font style="red">
        Welcome, ${sessionScope.USER_INFOR.fullName}
        </font>
        <h1>Search Page</h1>
        <form action="DispatchServlet" method="GET">
            Search Value<input type="text" name="txtSearchvalue" 
                               value="${param.txtSearchvalue}" /> <br/>     
            <input type="submit" value="Search"  name="btAction"/>
        </form> <br/>
        <c:set var="searchValue" value="${param.txtSearchvalue}" />
        
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
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
                        <c:forEach var="dto" items="${result}" varStatus="counter" >
                        <form name="btAction" action="DispatchServlet" method="POST">
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername" 
                                           value="${dto.username}" />
                                </td>
                                <td>
                                    <input type="text" name="txtPassword" value="${dto.password}" />
                                </td>
                                <td>
                                    ${dto.fullName}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkAdmin" value="ON" 
                                           <c:if test="${dto.role}">
                                               checked="checked"
                                           </c:if>
                                </td>
                                <td>
                                    <c:url var="deleteLink" value="DispatchServlet">
                                        <c:param name="btAction" value="Delete" />
                                        <c:param name="pk" value="${dto.username}" />
                                        <c:param name="searchLastName" 
                                                 value="${param.txtSearchvalue}" />
                                    </c:url>
                                    <a href="${deleteLink}">Delete</a> 
                                       
                                </td>
                                <td>
                                    <input type="hidden" name="searchLastValue" 
                                           value="${searchValue}" /> 
                                    <input type="submit" value="Update" name="btAction"/>
                                </td>
                            </tr>
                        </form>
                            
                            
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
            <c:if test="${empty result}">
                <h2>
                    <font color="red">
                        No record is matched!!!
                    </font>
                </h2>
            </c:if>
        </c:if>
              
              
        <%--<% 
            // service()
            String searchValue = request.getParameter("txtSearchvalue");
            if(searchValue != null){
                List<RegistrationDTO> result = (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");
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
                             for (RegistrationDTO dto : result) {
                                %> 
                                 <tr>
                                        <td><%= ++count %></td>                         
                                        <td><%= dto.getUsername() %></td>
                                        <td><%= dto.getPassword() %></td>
                                        <td><%= dto.getFullName() %></td>
                                        <td><%= dto.isRole() %></td>
                                 </tr> 

                                <%
                             }
                        
                       %>
                    </tbody>
                </table>     
                <%
                    
            }else{
                %> 
                <h2>
                    <font color="red">
                        No record is matched !!!
                    </font>
                </h2>
                
                <%
                 }
            }// vì truy cập mặc định là get không paramter nên check null   
        
        %>--%>
    </body>
</html>
