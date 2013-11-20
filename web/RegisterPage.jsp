<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Register page</title>

    <!-- Bootstrap core CSS -->
    <link href="HTMLStyle/LoginPageStyle/css/bootstrap.css" rel="stylesheet" type="text/css">

    <!-- Add custom CSS here -->
    <link href="HTMLStyle/LoginPageStyle/css/stylish-portfolio.css" rel="stylesheet" type="text/css">
    <link href="HTMLStyle/LoginPageStyle/css/singin.css" rel="stylesheet" type="text/css">
  </head>

  <body>  
    <!-- Full Page Image Header Area -->
    <div id="top" class="header1">
       
       <div class="container">
        
           <form action="Register.jsp" name="form" class="form-signin" method="POST">
        <h2 class="form-signin-heading">Registration form</h2>
        <input type="email" name="Login" class="form-control" placeholder="Your email address" required = "required" autofocus>
        <br>
        <input type="text" name="FirstName" class="form-control" placeholder="Your firstname">
        <br>
        <input type="text" name="LastName" class="form-control" placeholder="Your lastname">
        <br>
        <input type="text" name="Age" class="form-control" placeholder="Age">
        <br>
        <select name="Activity" class="form-control">
            <option value="">Select your Activity</option>
            <option value="Hiking">Hiking</option>
            <option value="Cycling">Cycling</option>
            <option value="Paragliding">Paragliding</option>
            <option value="Road tripping">Road tripping</option>
            <option value="Skiing">Skiing</option>
            <option value="Canoeing">Canoeing</option>
            <option value="Sailing">Sailing</option>
            <option value="Flying">Flying</option>
        </select>
        <br>
        <input type="password" name="Pass" class="form-control" placeholder="Your password" required = "required">
        <br>
        <input type="password" name="RetypePass" class="form-control" placeholder="Retype your password" required = "required">
        <br><br>
        <script language='javascript' type='text/javascript'>
            function check() {
            if (document.form.Pass.value != document.form.RetypePass.value) {
                document.form.RetypePass.setCustomValidity('The two passwords must match!');
            } else {
                document.form.RetypePass.setCustomValidity('');
            }
        }
        </script>
        <%
            if (session.getAttribute("incorrectValues") != null && session.getAttribute("incorrectValues").toString().equals("email")) {
                out.print("<script>alert(\"This email has been already used!\")</script>");
                session.removeAttribute("incorrectValues");
            }
        %>
        <button class="btn btn-lg btn-primary btn-block" type="submit" onClick="return check();">Register</button>
      </form>

    </div>
    </div>
  </body>

</html>
