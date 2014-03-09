<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Database.DBLoginFinder"%>
<%@page import="Database.DBTrackFinder"%>
<%@page import="Parser.TLVLoader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
    session.removeAttribute("access");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="Windows-1250">
        <title>Your tracks</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">
        <link href="HTMLStyle/HomePageStyle/css/style.css" rel="stylesheet">

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
                                <li class="active">
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
                    
                    <div class="carousel slide" id="carousel-646485" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li class="active" data-slide-to="0" data-target="#carousel-646485">
                            </li>
                            <li data-slide-to="1" data-target="#carousel-646485">
                            </li>
                            <li data-slide-to="2" data-target="#carousel-646485">
                            </li>
                        </ol>
                        <div class="carousel-inner">
                            <div class="item active">
                                <img alt="" src="HTMLStyle/HomePageStyle/car1.jpg">
                                <div class="carousel-caption">
                                    <h4>
                                        Release your soul...
                                    </h4>
                                    <p>
                                        ...conquer the highest mountain in the world...
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <img alt="" src="HTMLStyle/HomePageStyle/car2.jpg">
                                <div class="carousel-caption">
                                    <h4>
                                        Discover the beauty of world...
                                    </h4>
                                    <p>
                                        ...take your bike and go...
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <img alt="" src="HTMLStyle/HomePageStyle/car3.jpg">
                                <div class="carousel-caption">
                                    <h4>
                                        Take off to the sky...
                                    </h4>
                                    <p>
                                        ...get your paraglide and fly with the wind...
                                    </p>
                                </div>
                            </div>
                        </div> <a class="left carousel-control" href="#carousel-646485" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a> <a class="right carousel-control" href="#carousel-646485" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
                    </div>
                    
                    <h3>
                        Explore your tracks
                    </h3>

                    <br>
                    
                     <%
    
                        DBTrackFinder trackFinder = new DBTrackFinder();
                        DBLoginFinder loginFinder = new DBLoginFinder();
                        int userID = loginFinder.getUserId(session.getAttribute("username").toString());
                        ArrayList<String> tracks = trackFinder.getUserTracks(userID);
                        ArrayList<String> trackFiles = trackFinder.getUserTracksFiles(userID);
                        ArrayList<Integer> trackIDs = trackFinder.getTracksIDs(userID);
                        
                        if (tracks.size() == 0) {
                            out.print("<div class=\"alert alert-danger\">Sorry, you don't have any uploaded tracks...</div>");
                        }
                        
                        for(int i = 0; i < tracks.size(); i++){
                            
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date modifiedDate = df.parse(trackFinder.getChangeDate(trackIDs.get(i)).substring(0,19));
                            modifiedDate.toGMTString(); 
                                                       
                            
                            
                            //String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(uploadedDate);
     
                            
                            out.print("<div class=\"panel-group\" id=\"panel-297555\">");
                                                        
                            out.print("<div class=\"panel panel-default\">");
                            out.print("<div class=\"panel-heading\">");
                            out.println("<a class=\"panel-title collapsed\" data-toggle=\"collapse\" data-parent=\"#panel-297555\" href=\"#panel-element-" + i + "\">" + tracks.get(i) + "</a>");
                            out.print("</div>");
                            out.print("<div id=\"panel-element-" + i + "\" class=\"panel-collapse collapse\">");
                            out.print("<div class=\"panel-body\">\n");
                            
                 
                            out.print("<div style=\"word-wrap: break-word\" class=\"col-md-5 column\">");
                            
                            out.print("<label for=\"TrackDesc\">Track description:</label><h5>" + trackFinder.getTrackDescription(trackIDs.get(i)) + " </h5> <label for=\"TrackActivity\">Track activity:</label> "
                                    + "<h5>" + trackFinder.getTrackActivity(trackIDs.get(i)) + "</h5> <label for=\"TrackUpl\">Uploaded:</label><h5>" + trackFinder.getUploadedDate(trackIDs.get(i)) + " </h5> <label for=\"TrackUpl\">Start place:</label><h5>" + trackFinder.getStartAddress(trackIDs.get(i)) + " </h5> </div><div class=\"col-md-5 column\"> <label for=\"TrackSD\">Start:</label><h5>" + 
                                    trackFinder.getTrackStartDate(trackIDs.get(i)) + "</h5><label for=\"TrackED\">End:</label><h5> " + trackFinder.getTrackEndDate(trackIDs.get(i)) + 
                                    " </h5>  <label for=\"TrackMod\">Modified:</label><h5>" + modifiedDate + " </h5> <label for=\"TrackUpl\">End place:</label><h5>" + trackFinder.getEndAddress(trackIDs.get(i)) + " </h5></div></div> <a href=NewShowTrackBETA.jsp?trkID=" + trackIDs.get(i) +  " class=\"btn btn-success btn-sm pull-right\">Show</a>"
                                    + " <a href=DeleteTrack.jsp?trkID=" + trackIDs.get(i) +  "  class=\"btn btn-danger btn-sm pull-right\">Delete</a>"); 

                            
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            
                        }
                    %>   
                    <br>
                </div>
            </div>
        </div>
    </body>
</html>


