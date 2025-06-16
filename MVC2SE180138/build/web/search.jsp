<%-- 
    Document   : search
    Created on : Jun 14, 2025, 5:43:09 PM
    Author     : Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="namnd.registration.RegistrationDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <h1>Search Page</h1>
        <form action="DispatchServlet" method="GET">
            Search Value<input type="text" name="txtSearchvalue" 
                               value="<%= request.getParameter("txtSearchvalue") %>" /> <br/>     
            <input type="submit" value="Search"  name="btAction"/>
        </form> <br/>
        <% 
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
        
        %>
    </body>
</html>
