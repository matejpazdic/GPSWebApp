<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
    session.removeAttribute("access");
    session.removeAttribute("trackNameExist");
  
    String isUser = session.getAttribute("Admin").toString();
    
    String logLink;
    String system = System.getProperty("os.name");
    
    if (system.startsWith("Windows")) {
        logLink = "http://localhost:8084/GPSWebApp/Logged/uploaded_from_server/GPSWebAppLog.log";
    } else {
        logLink = "http://gps.kpi.fei.tuke.sk/Logged/uploaded_from_server/GPSWebAppLog.log";
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>About</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">
        <link href="HTMLStyle/HomePageStyle/css/style.css" rel="stylesheet">

        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/scripts.js"></script>
        
        <script>  
        
        var isUser = "<% out.print(isUser.toString());%>";
        
        if (isUser.toString() === "True") {
            $( document ).ready(function() {document.getElementById('admin').style.display="inline";});
        }

        </script>
        
    </head>

    <body>
        <div class="container">
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
                                <li class="dropdown active">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Create track<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="UploadTrack1.jsp">Upload track</a>
                                        </li>

                                        <li class="divider">
                                        </li>
                                        <li>
                                            <a href="DrawTrack.jsp">Write new track</a>
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
                                <li class="active">
                                    <a href="About.jsp">About</a>
                                </li>
                                <li class="dropdown">
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
                                <a href="#panel-234896" data-toggle="tab">About GPSWebApp</a>
                            </li>
                        </ul>
                        

                                <h3>
                                    <b>GPSWebApp v. 0.77</b>
                                </h3>

                                 <div class="row clearfix">
                                        <div class="col-md-4 column">
                                            <div class="text-center">
                                                <br>
                                                <img id="kpi" name="img" src="HTMLStyle\kpi.png" alt="..." class="img-rounded" style="height:120px;width:120px">
                                                
                                            </div>
                                        </div>
                                        
                                        
                                        <div class="col-md-4 column">
                                            <div class="text-center">
                                                <br>
                                                <img id="fei" name="img" src="HTMLStyle\fei.png" alt="..." class="img-rounded" style="height:120px;width:120px">
                                                
                                            
                                        </div>
                                        </div>
                                        <div class="col-md-4 column">
                                        <div class="text-center">
                                                <br>
                                                <img id="tuke" name="img" src="HTMLStyle\tuke.png" alt="..." class="img-rounded" style="height:120px;width:120px">
                                                
                                            
                                        </div>
                                        </div>
                                     </div>
                        
                                 <br>
           
                                    <h4>This website is developed as a practical Part of Ľubomír Petrus and Matej Pazdič thesis. 
                                        Take note of the copyrights owns Petrus, Pazdič and at last but not least Technical University of Košice, 
                                        Faculty of Electrical Engineering and Informatics.</h4>
                                    
                                                                   
                                    <div class="row clearfix">
                                        <div class="col-md-6 column">
                                                <dl>
                                                    <dt>Web design, Front-end functionality, Map services and showing, drawing and presentating tracks with related multimedia files: </dt>
                                                        <dd><h5> <b>Thesis:</b> <br>Services for Publication a Presentation GPS Records with Multimedia Information </h5></dd>
                                                    
                                                    <dt>Author:</dt>
                                                    <dd><h5>Ľubomír Petrus</h5></dd>
                                        
                                                    <dt>Supervisor:</dt>
                                                    <dd><h5>doc. Ing. Zdeněk Havlice, CSc.</h5></dd>
                                                </dl>
                                        </div>
                                        <div class="col-md-6 column">
                                            <dl>
                                                    <dt>Back-end design, processing input files, generating output files, designing and managment of database: </dt>
                                                        <dd><h5> <b>Thesis:</b> <br>Services for Processing and Storage GPS Records with related Multimedia Information </h5></dd>
                                                    <dt>Author:</dt>
                                                    <dd><h5>Matej Pazdič</h5></dd>
                                               
                                                    <dt>Supervisor:</dt>
                                                    <dd><h5>doc. Ing. Zdeněk Havlice, CSc.</h5></dd>
                                                </dl>
                                        </div>
                                    
                                      
                                   
                                
                            </div>
                        </div>
                                   
                                    <div class="tabbable" id="admin" style="display:none">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                            <a href="#panel-234666" data-toggle="tab">Admin</a>
                                        </li>
                                         </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="panel-234666">

                                    <div class="row clearfix">
                                        <div class="col-md-6 column">
                                            <h3>
                                            <b>Whats new in v. 0.77 »</b>
                                        </h3>
                                                <ul>
                                                         <li>Edited registration - now with activation email</li>
                                                         <li>Edited registration form</li>
                                                         <li>Added new account type - Admin</li>
                                                         <li>Added logger for entire application</li>
                                                         <li>Added delete user function</li>
                                                         <li>Added View account details page and function</li>
                                                         <li>Added Edit account page and function</li>
                                                         <li>Added About page, two different views, for Admin or User </li>
                                                         <li>Completly new track display and presentation mode</li>
                                                         <li>Finish auto presentation mode without the need of interaction with user</li>
                                                         <li>Added new info about track - length, speed, max elevation, min elevation, duration, track height diff</li>
                                                         <li>Added track delete protection (delete another user's track)</li>                                                    
                                                         
                                                </ul>
                                    </div>

                                    <div class="col-md-6 column">
                                        
                                        <h3>
                                            <b>Admin Section</b>
                                            </h3>    
                                            
                                                <p>
                                                    <a href="https://github.com/matejpazdic/GPSWebApp">GPSWebApp project on GitHub »</a>
                                                </p>
                                    
                                                <p>
                                                    <a href="<% out.print(logLink);%>">GPSWebApp Log »</a>
                                                </p>
                                        
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