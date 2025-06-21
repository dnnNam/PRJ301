<%-- 
    Document   : search
    Created on : Jun 14, 2025, 5:43:09 PM
    Author     : Hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter" >
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${dto.username}
                                </td>
                                <td>
                                    ${dto.password}
                                </td>
                                <td>
                                    ${dto.fullName}
                                </td>
                                <td>
                                    ${dto.role}
                                </td>
                            </tr>
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
