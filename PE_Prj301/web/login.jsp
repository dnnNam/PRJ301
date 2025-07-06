<%-- 
    Document   : login
    Created on : Apr 26, 2025, 8:58:20 AM
    Author     : Computing Fundamental - HCM Campus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        
        <h1>Login Page</h1>
        <form action="MainController" method="POST">
            username <input type="text" name="txtusername" value="" /> <br/>
            password <input type="password" name="txtpassword" value="" /><br/>
            <input type="submit" value="Login" name="action" />
            <input type="reset" value="reset" />
        </form>
        
    </body>
</html>
