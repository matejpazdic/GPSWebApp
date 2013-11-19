<%-- 
    Document   : Register
    Created on : 10.10.2013, 18:04:18
    Author     : matej_000
--%>

<%@page import="Database.DBLoginCreator"%>
<%@page import="javax.naming.spi.DirStateFactory.Result"%>
<%@page import="Database.DBLoginFinder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <%
            String email = request.getParameter("Login");
            String pass = request.getParameter("Pass");
            String reenteredPass = request.getParameter("RetypePass");
            String firstName = request.getParameter("FirstName");
            String lastName = request.getParameter("LastName");
            String age = request.getParameter("Age");
            String activity = request.getParameter("Activity");
                
                if(!pass.equals("")){
                if (pass.equals(reenteredPass)) {
                    DBLoginFinder finder = new DBLoginFinder();
                    if (finder.isExistingLogin(email) == false) {
                        DBLoginCreator creator = new DBLoginCreator();
                        creator.createNewLogin(email, firstName, lastName, age, activity, pass);
                        response.sendRedirect("LoginPage.jsp");
                    } else {
                        response.sendRedirect("RegisterUser.html");
                    }
                } else{
                    response.sendRedirect("RegisterUser.html");
                    }
                } else{
                    response.sendRedirect("RegisterUser.html");
                }
        %>
    </body>
</html>
