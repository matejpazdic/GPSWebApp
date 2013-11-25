<%-- 
    Document   : Login
    Created on : 9.10.2013, 19:09:14
    Author     : matej_000
--%>

<%@page import="Database.DBLoginFinder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
%>
<!DOCTYPE html>
<html>
    <%
        DBLoginFinder finder = new DBLoginFinder();
        boolean isGood = finder.isCorrectLogin(request.getParameter("Login"), request.getParameter("Pass"));
        
        if(isGood){
            session.setAttribute("username", request.getParameter("Login"));
            session.setAttribute("isCorrectLogin", "True");
            response.sendRedirect("Logged/HomePage.jsp");
        } else{
            session.setAttribute("isCorrectLogin", "False");
            response.sendRedirect("LoginPage.jsp");
        } %>
</html>