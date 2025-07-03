<%-- 
    Document   : createAccount
    Created on : Jul 2, 2025, 5:07:13 PM
    Author     : Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Account Page</title>
    </head>
    <body>
       <h1>Create Account Page</h1>
       <form action="DispatchServlet" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERRORS}" />
            Username* <input type="text" name="txtUsername" value="${param.txtUsername}" />(6 - 30 chars)<br/>
            <c:if test="${not empty errors.usernameLengthErr}">
                <font color="red">
                    ${errors.usernameLengthErr}
                </font> <br/>
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                <font color="red">
                    ${errors.usernameIsExisted}
                </font> <br/>
            </c:if>
                Password* <input type="password" name="txtPassword" value="" />(6 - 20 chars)<br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                    ${errors.passwordLengthErr}
                </font> <br/>
            </c:if>
                Confirm*  <input type="password" name="txtConfirm" value="" /><br/>
            <c:if test="${not empty errors.confirmNotMatched}">
                <font color="red">
                    ${errors.confirmNotMatched}
                </font> <br/>
            </c:if>
                Full name* <input type="text" name="txtFullName" value="${param.txtFullName}" />(2 - 50 chars)<br/>
            <c:if test="${not empty errors.fullNameLengthErr}">
                <font color="red">
                    ${errors.fullNameLengthErr}
                </font> <br/>
            </c:if>
            <input type="submit" value="CreateAccount" name="btAction" /> 
            <input type="reset" value="reset" />   
        </form>
    </body>
</html>
