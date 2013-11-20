<%-- 
    Document   : LoginPage
    Created on : 9.10.2013, 20:27:56
    Author     : matej_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            if(session.getAttribute("username") != null){
                response.sendRedirect("Logged/HomePage.jsp");
            }
            %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Login Page</title>

    <!-- Bootstrap core CSS -->
    <link href="HTMLStyle/LoginPageStyle/css/bootstrap.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="HTMLStyle/LoginPageStyle/css/stylish-portfolio.css" rel="stylesheet">
    <link href="HTMLStyle/LoginPageStyle/css/singin.css" rel="stylesheet">
  </head>

  <body>  
    <!-- Full Page Image Header Area -->
    <div id="top" class="header">
       
       <div class="container">
        
           <form action="Login.jsp" class="form-signin" method="POST">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="email" name="Login" class="form-control" placeholder="Email address" autofocus>
        <input type="password" name="Pass" class="form-control" placeholder="Password">
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
           <a href="RegisterPage.jsp" class="label">Or register!</a>
           <br>
           <%
            if (session.getAttribute("isCorrectLogin") != null && session.getAttribute("isCorrectLogin").equals("False")) {
                    out.print("<script>alert(\"Incorrect Login or Password!\")</script>");
                    session.removeAttribute("isCorrectLogin");
                } else if (session.getAttribute("correctRegistration") != null && session.getAttribute("correctRegistration").toString().equals("True")) {
                    out.print("<script>alert(\"Congrats you have been successfuly registered! You can now Sign in.\")</script>");
                    session.removeAttribute("correctRegistration");
                }
            %>
           
    </div>
    </div>
    <!-- /Full Page Image Header Area -->
  
    <!-- Intro -->
    

  </body>

</html>