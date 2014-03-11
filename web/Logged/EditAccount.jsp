<%@page import="java.util.ArrayList"%>
<%@page import="Database.DBLoginFinder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
    session.removeAttribute("access");
    session.removeAttribute("trackNameExist");
    
     DBLoginFinder finder = new DBLoginFinder();
     ArrayList<String> information = finder.getUserInformation(session.getAttribute("username").toString());
  
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Edit account</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">
        <link href="HTMLStyle/HomePageStyle/css/style.css" rel="stylesheet">

        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/scripts.js"></script>
           
        <% 
        if (information.get(3).toString().equalsIgnoreCase("null")) {
            
        } else {
            out.print("<script> $( document ).ready(function() {$(\"div.mydiv select\").val(\"" + information.get(3).toString() + "\");}); </script>");
        }
        %>
        
    </head>

    <body>
        <div class="container">
            
            <div id="top1"> 
                 <a id="modal-49447" href="#modal-container-49447" data-toggle="modal"></a>
			
                <div class="modal fade" id="modal-container-49447" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
			<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
					<h3 class="modal-title" id="myModalLabel">
                                            <b>Incorrect current password!!!</b>
					</h3>
			</div>
			<div class="modal-body">
				You must write correct password...
			</div>
			<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
				</div>
			</div>
					
		</div></div></div>
           
            <div id="top2"> 
                 <a id="modal-49448" href="#modal-container-49448" data-toggle="modal"></a>
			
                <div class="modal fade" id="modal-container-49448" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
			<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
					<h3 class="modal-title" id="myModalLabel">
                                            Password field is empty! 
					</h3>
			</div>
			<div class="modal-body">
				Please, write new password!
			</div>
			<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
				</div>
			</div>
					
		</div></div></div>
           
           <div id="top3"> 
                 <a id="modal-49448" href="#modal-container-49449" data-toggle="modal"></a>
			
                <div class="modal fade" id="modal-container-49449" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
			<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
					<h3 class="modal-title" id="myModalLabel">
                                            Password doesn't match retyped password! 
					</h3>
			</div>
			<div class="modal-body">
				Password and retyped password must be same! 
			</div>
			<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
				</div>
			</div>
					
		</div></div></div>
            
            
            
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <nav class="navbar navbar-default" role="navigation">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand" href="HomePage.jsp"><i class="fa fa-globe"></i>&nbsp;  GPSWebApp</a>
                        </div>

                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav">
                                <li>
                                    <a href="HomePage.jsp">Home</a>
                                </li>
                                <li>
                                    <a href="ShowTracks.jsp">My Tracks</a>
                                </li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Create track<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="UploadTrack1.jsp">Upload track</a>
                                        </li>

                                        <li class="divider">
                                        </li>
                                        <li>
                                            <a href="WriteTrack1.jsp">Write new track</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                            <form class="navbar-form navbar-left" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control home-search">
                                </div> <button type="submit" class="btn btn-default">Find</button>
                            </form>
                            <ul class="nav navbar-nav navbar-right">
                                <li>
                                    <a href="About.jsp">About</a>
                                </li>
                                <li class="dropdown active">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>  Account<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="ShowUserInfo.jsp">View account</a>
                                        </li>
                                        <li>
                                            <a href="EditAccount.jsp">Edit account</a>
                                        </li>
                                        <li>
                                            <a href="DeleteUser.jsp">Delete account</a>
                                        </li>
                                        <li class="divider">
                                        </li>
                                        <li>
                                            <a href="../Logout.jsp">Logout</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                    </nav>
                    <div class="tabbable" id="tabs-883724">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#panel-234896" data-toggle="tab">Edit account</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-234896">

                                <h3>
                                    Edit account
                                </h3>
                                <br>

                                <div class="container">
                                    <div class="row clearfix">
                                        <div class="col-md-4 column"></div>
                                        <div class="col-md-4 column">
                                    
                                            <form action="UpdateUser.jsp" name="form" class="form-signin" method="POST">
                                               
                                                <label for="email">Email</label>
                                                <input type="email" name="Login" class="form-control" readonly value="<%out.print(information.get(4));%>">
                                                <br>
                                                <label for="First name">First name</label>
                                                <input type="text" name="FirstName" class="form-control" placeholder="Your firstname" value="<%out.print(information.get(0));%>" >
                                                <br>
                                                <label for="Last name">Last name</label>
                                                <input type="text" name="LastName" class="form-control" placeholder="Your lastname" value="<%out.print(information.get(1));%>">
                                                <br>
                                                <label for="Age">Age</label>
                                                <input type="number" name="Age" class="form-control" placeholder="Age" min="1" value="<% if (information.get(2).toString().equalsIgnoreCase("-1")){} else out.print(information.get(2));%>">
                                                <br>
                                                <label for="Activity">Activity</label>
                                                <div class="mydiv">
                                                <select name="Activity" class="form-control" >
                                                    <option value="1" disabled selected>Select your Activity</option>
                                                    <option value="Hiking">Hiking</option>
                                                    <option value="Cycling">Cycling</option>
                                                    <option value="Paragliding">Paragliding</option>
                                                    <option value="Road tripping">Road tripping</option>
                                                    <option value="Skiing">Skiing</option>
                                                    <option value="Canoeing">Canoeing</option>
                                                    <option value="Sailing">Sailing</option>
                                                    <option value="Flying">Flying</option>
                                                </select>
                                                </div>
                                                <br>
                                                <input type="password" name="currPass" class="form-control" placeholder="Current password" required = "required">
                                                <br>
                                                <input type="password" name="Pass" class="form-control" placeholder="New password" required = "required">
                                                <br>
                                                <input type="password" name="RetypePass" class="form-control" placeholder="Retype new password" required = "required">
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
                                                <p style="line-height: 20px; text-align: center;"> <button class="btn btn-default btn-success" type="submit" onClick="return check();">Save</button> </p>
                                                </form>
                                            </div>
                                        <div class="col-md-4 column"></div>
                                        
                                        <%
                                        if (session.getAttribute("wrongPass") != null && session.getAttribute("wrongPass").equals("True")) {
                                                //out.print("<script>alert(\"Incorrect Login or Password!\")</script>");

                                                out.print("<script>$(document).ready(function() {$('#top1').find('a').trigger('click');});</script>");
                                                session.removeAttribute("wrongPass");

                                            } else if (session.getAttribute("emptyPass") != null && session.getAttribute("emptyPass").toString().equals("True")) {
                                                //out.print("<script>alert(\"Congrats you have been successfuly registered! You can now Sign in.\")</script>");
                                                session.removeAttribute("emptyPass");
                                                out.print("<script>$(document).ready(function() {$('#top2').find('a').trigger('click');});</script>");


                                            } else if (session.getAttribute("passNotMatch") != null && session.getAttribute("passNotMatch").equals("True")) {
                                                //out.print("<script>alert(\"Congrats you have been successfuly registered! You can now Sign in.\")</script>");
                                                
                                                session.removeAttribute("passNotMatch");
                                                out.print("<script>$(document).ready(function() {$('#top3').find('a').trigger('click');});</script>");
                                    
                                                }
                                        %>
                                           
                                            
                                        </div>
                                          
                                        
                                    </div>
                                </div>
                                    
                                    
                              </div>
                          </div>
                           

                        </div>
                    </div>
               </div>
           
         
    </body>
</html>

