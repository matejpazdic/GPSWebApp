<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <%
        String os = System.getProperty("os.name");
        String pathToMultimedia = null;

        if (os.startsWith("Windows XP")) {
            pathToMultimedia = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + session.getAttribute("trackName") + "\\Multimedia\\";
        } else if (os.startsWith("Windows")) {
            pathToMultimedia = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + session.getAttribute("trackName") + "\\Multimedia\\";
        } else {
            pathToMultimedia = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/" + session.getAttribute("trackName") + "/Multimedia/";
        }

        File f = new File(pathToMultimedia);
        f.mkdirs();
        FileUtils.forceMkdir(f);
    %>
    <head>
        <meta charset="UTF-8">
        <title>Upload multimedia files</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">
        <link href="HTMLStyle/HomePageStyle/css/style.css" rel="stylesheet">
        
        <link href="HTMLStyle/MultimediaUploaderStyle/css/dropzone.css" type="text/css" rel="stylesheet" />

        <script src="HTMLStyle/MultimediaUploaderStyle/dropzone.min.js"></script>

        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/scripts.js"></script>

        
        
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
                                <li>
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
                                <a href="#panel-234896" data-toggle="tab">Track upload</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-234896">

                                <h3>
                                    Upload your multimedia files (Step 3)
                                </h3>
                                <br>

                                <div class="container">
                                    <div class="row clearfix">
                                        <div class="col-md-4 column"></div>
                                        <div class="col-md-4 column"></div>
                                        <div class="col-md-4 column"></div>
                                    </div>
                                </div>
                                    <form action="UploadMultimedia" class="dropzone" enctype="multipart/form-data">
                                                <div class="fallback">
                                                <input name="file" type="file" multiple />
                                                </div>
                                            </form>
                                <div class="container">    
                                    <div class="row clearfix">
                                        <div class="col-md-4 column"></div>
                                        <div class="col-md-4 column">
                                            
                                            <br>
                                            <form action="SynchronizeTrack.jsp" method="post" enctype="multipart/form-data">
                                                <p style="line-height: 20px; text-align: center;"> <button type="submit" class="btn btn-default btn-success ">Final step</button></p>
                                            </form>
                                        </div>
                                        <div class="col-md-4 column"></div>
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


