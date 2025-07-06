<%-- 
    Document   : search
    Created on : Apr 26, 2025, 8:59:02 AM
    Author     : Computing Fundamental - HCM Campus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body> 
        <!--your code here-->
        <font color="red">
        WELCOME , ${sessionScope.USER_INFOR.fullName}
        </font>

        <div style="display: flex ; align-items: center ; gap: 8px;">
            <h1>SEARCH PAGE</h1>
            <form action="MainController">
                <input type="submit" value="Logout" name="action" />
            </form>
        </div>

        <form action="MainController">
            minimum <input type="text" name="txtsearchMini" value="${param.txtsearchMini}" /> <br/>
            max <input type="text" name="txtSearchMax" value="${param.txtSearchMax}" /> <br/>
            Search <input type="submit" value="Search" name="action" />
        </form>
        <br/>

        <c:set var="miniValue" value="${param.txtsearchMini}" />
        <c:set var="maxValue" value="${param.txtSearchMax}" />

        <c:if test="${not empty miniValue and not empty maxValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}" />

            <c:if test="${not empty result}">


                <table border="1">
                    <thead>
                        <tr>
                            <th>ordinary number</th>
                            <th>name</th>
                            <th>brand name</th>
                            <th>description</th>
                            <th>price</th>
                            <th>size</th>
                            <th>quantity</th>
                            <th>Edit Link</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" >

                        <form action="MainController">
                            <tr>
                                <td>
                                    ${dto.id}
                                    <input type="hidden" name="txtID" value="${dto.id}" />
                                </td>
                                <td>
                                    ${dto.name}
                                </td>
                                <td>
                                    ${dto.brandName}
                                    <label 
                                </td>
                                <td>
                                    <input type="text" name="txtDescription" value="${dto.description}" />
                                </td>
                                <td>
                                    <input type="text" name="txtPrice" value="${dto.price}" />
                                </td>
                                <td>
                                    ${dto.size}
                                </td>
                                <td>
                                    <input type="text" name="txtQuantity" value="${dto.quantity}" />
                                </td>
                                <td>
                                    <input type="hidden" name="txtsearchMini" value="${miniValue}" />
                                        <input type="hidden" name="txtSearchMax" value="${maxValue}" />
                                    <input type="submit" value="Update" name="action" />
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
                No Record Is Matched!!!
                </font>
            </h2>   
        </c:if>

    </c:if>

</body>
</html>
