<%-- 
    Document   : Login
    Created on : 9.10.2013, 19:09:14
    Author     : matej_000
--%>

<%@page import="Database.DBLoginFinder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        DBLoginFinder finder = new DBLoginFinder();
        boolean isGood = finder.isCorrectLogin(request.getParameter("Login"), request.getParameter("Pass"));
        
        if(isGood){
            session.setAttribute("username", request.getParameter("Login"));
            response.sendRedirect("Logged/HomePage.jsp");
        } else{
            response.sendRedirect("BadLogin.html");
        } %>
</html>