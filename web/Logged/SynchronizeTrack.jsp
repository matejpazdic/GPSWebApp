<%-- 
    Document   : SynchronizeTrack
    Created on : 4.3.2014, 14:04:48
    Author     : Lubinko
--%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
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

<%
    TLVLoader loader = new TLVLoader();
    String system = System.getProperty("os.name");
    int trkID = Integer.parseInt(request.getParameter("trkID"));
    DBTrackFinder trackFinder = new DBTrackFinder();
    String path = trackFinder.getTrackFilePath(trkID);
    String file = trackFinder.getTrackFileName(trkID);
    
    if (system.startsWith("Windows")) {
        path = path.replaceAll("/", "\\\\"); // vymazat pri pouziti na serveri LINUX!!!
    }
    loader.readTLVFile(path, file);
%>
<html lang="en">
    <head>
        <meta charset="Windows-1250">
        <title>Synchronize your multimedia</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">
        <link href="HTMLStyle/HomePageStyle/css/style.css" rel="stylesheet">

        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/scripts.js"></script>
                
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>
    	<script src="HTMLStyle/GalleryStyle/galleria-1.3.3.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/GalleryStyle/themes/classic2/galleria.classic.min.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript" src="http://underscorejs.org/underscore-min.js"></script>
    

        <style>

            
            #map_canvas {
                
                display: block;
                width: 100%;
                height: 680px;
            }

        </style>
        
        
        
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBH31FxBV_cLA7hdbY2dBTUsJjAaDEE0MI&sensor=true"></script>
        <script>

                    var map_options = {
                    mapTypeId: google.maps.MapTypeId.HYBRID
                    };
                    
                    var map;
                    var polylineOK = null;
                    var isEnd = false;
                    var isPresented = false;
                    var index = 0;
                    var a = 0;
                    var isActualMultimediaEnd = false;
                    var infowindow;
                    var contentString;
                    var marker;
                    var newWidth = 50;
                    var chart;

                    
                    var polylineCoordinatesListFinal = [];
                    var isPolylineAlreadyCreated = false;
                    
                    var graphDataFinal = [];
                    var graphEx = [];
                    var options;
                    
                    var isAlreadyMark = false;
                    var mark;
                    
                    var markersArray = [];
                    var indeX;
                    
            <%
                out.print("var polylineCoordinatesList = [\n");
                for (int i = 0; i < loader.getTrackPoints().size(); i++) {
                    out.print("new google.maps.LatLng(" + loader.getTrackPoints().get(i).getLatitude() + ", " + loader.getTrackPoints().get(i).getLongitude() + ")");
                    if (i != loader.getTrackPoints().size() - 1) {
                        out.println(",");
                    }
                }
                out.print("\n];");
                
                out.print("var isFiles = [\n");
                for (int i = 0; i < loader.getTrackPoints().size(); i++) {
                    out.print(loader.getIsFiles()[i]);
                    if (i != loader.getTrackPoints().size() - 1) {
                        out.println(",");
                    }
                }
                out.print("\n];");
                
                out.print("\nvar filesPath = [\n");
                for (int i = 0; i < loader.getMultimediaFiles().size(); i++) {
                    String temp = null;
                    
                    if(System.getProperty("os.name").startsWith("Windows")){
                        File f = new File(loader.getMultimediaFiles().get(i).getPath());
                        temp = f.toURI().toString().substring(f.toURI().toString().lastIndexOf("/Logged/") + 8);
                    }else{
                        if (!loader.getMultimediaFiles().get(i).getPath().toString().contains("YTB")) {
                        String temp1 = loader.getMultimediaFiles().get(i).getPath().substring(38);
                        temp = temp1.replaceAll(" ", "%20");}
                    }
                    
                    String newPath = null;
                    
                    if (!loader.getMultimediaFiles().get(i).getPath().toString().contains("YTB")) {
                    
                        String extension = temp.substring(temp.lastIndexOf("."), temp.length());
                        newPath = temp.substring(0,temp.lastIndexOf(".")) + "_THUMB" + extension; 
                      
                    } else { 
                        
                        if (System.getProperty("os.name").startsWith("Windows")) {
                        newPath = temp.substring(temp.length()-17);
                        newPath = newPath.replaceAll("%20"," ");
                        }
                        else {
                        newPath = loader.getMultimediaFiles().get(i).getPath();
                    }
                        
                    }
                    //System.out.print("ORIGINAL: " + temp);
                    //String temp1 = temp.replaceAll("\\\\", "\\\\\\\\");
                    //String temp2 = temp1.replaceAll("/", "\\\\\\\\");
                    //System.out.print("NEW: " + temp1);
                    out.print("\"" + newPath + "\"");
                    if (i != loader.getMultimediaFiles().size() - 1) {
                        out.println(",");
                   }
                }
                out.print("\n];");
                
                out.print("\nvar filesPoints = [\n");
                for (int i = 0; i < loader.getMultimediaFiles().size(); i++) {
                    out.print(loader.getMultimediaFiles().get(i).getTrackPointIndex());
                    if (i != loader.getMultimediaFiles().size() - 1) {
                        out.println(",");
                    }
                }
                out.print("\n];");
                
                
                int maxy = -500;
                int miny = 10000; 
                
                out.print("\nvar gData = [\n ['', 'Device elevation', 'Elevation on the map'],\n");
                for (int i = 0; i < loader.getTrackPoints().size(); i++) {
                   
                    if (i==loader.getTrackPoints().size()-1){
                                
                                
                                out.print("['"+ i +"', " + loader.getTrackPoints().get(i).getDeviceElevation() + ","+ loader.getTrackPoints().get(i).getInternetElevation() +"]];\n");
                                        }
                    else {
                       
                                out.print("['"+ i +"', " + loader.getTrackPoints().get(i).getDeviceElevation() + ","+ loader.getTrackPoints().get(i).getInternetElevation() +"],\n");
                    
                         }   
                     
            }
      
                out.print("\nvar minElevation = " + miny + "\n");
                out.print("\nvar maxElevation = " + maxy + "\n");
                
            %>
                
    
                function clearOverlays() {
                    for (var i = 0; i < markersArray.length; i++ ) {
                        markersArray[i].setMap(null);
                    }
                        markersArray.length = 0;
                }

                function initialize() {

                isAlreadyMark = false;
                isPresented = false;
                var bounds = new google.maps.LatLngBounds();

                for (var i = 0; i < polylineCoordinatesList.length; i++) {
                    bounds.extend(polylineCoordinatesList[i]);
                }

                map_canvas = document.getElementById('map_canvas');
                map = new google.maps.Map(map_canvas, map_options);


                        polylineOK = new google.maps.Polyline({
                        path: polylineCoordinatesList,
                                strokeColor: '#3300FF',
                                geodesic: true,
                                strokeOpacity: 1.0,
                                strokeWeight: 2,
                                editable: false
                        });

                        polylineOK.setMap(map);
                        map.fitBounds(bounds);

                        for (i=0; i<filesPath.length; i++) {
                            marker = new google.maps.Marker({
                                                    position: polylineCoordinatesList[filesPoints[i]],
                                                    map: map,
    //                                              icon: iconF,
                                                    title: 'Kalvarka :)'
                                                 });

                                                 marker.setMap(map);
                                                 markersArray.push(marker);
                        }

                }
            
                function startSync (){

                    if (marker) {    
                            clearOverlays();
                          }
                    document.getElementById("inp").disabled = true;
                  
                    
                    Galleria.on('image', function(e) {
                        syncImg(this.getIndex());
                    });
                    
                    Galleria.ready(function(options) {
                        syncImg(this.getIndex());
                    });        
                
                }

                function syncImg (index){
                        
                        if (marker) {    
                            marker.setMap(null);
                          }
                        
                        marker = new google.maps.Marker({
                                                    position: polylineCoordinatesList[filesPoints[index]],
                                                    map: map,
                                                    draggable:true,
    //                                              icon: iconF,
                                                    title: 'Kalvarka :)'
                                                 });

                                                 marker.setMap(map);
                                                 
                      indeX = index;                           
                                                 
                                                 
                      google.maps.event.addDomListener(marker, 'dragend', function(e) {
                        marker.setPosition(find_closest_point_on_path(e.latLng,polylineCoordinatesList));
                        newPos(find_closest_point_on_path(e.latLng,polylineCoordinatesList));
                        });

                     google.maps.event.addDomListener(marker, 'drag', function(e) {
                        marker.setPosition(find_closest_point_on_path(e.latLng,polylineCoordinatesList));
                        });
                }
                
                 function find_closest_point_on_path(drop_pt,path_pts){
                    distances = new Array();//Stores the distances of each pt on the path from the marker point 
                    distance_keys = new Array();//Stores the key of point on the path that corresponds to a distance

                    //For each point on the path
                    $.each(path_pts,function(key, path_pt){
                        //Find the distance in a linear crows-flight line between the marker point and the current path point
                        var R = 6371; // km
                        var dLat = (path_pt.lat()-drop_pt.lat()).toRad();
                        var dLon = (path_pt.lng()-drop_pt.lng()).toRad();
                        var lat1 = drop_pt.lat().toRad();
                        var lat2 = path_pt.lat().toRad();

                        var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
                        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
                        var d = R * c;
                        //Store the distances and the key of the pt that matches that distance
                        distances[key] = d;
                        distance_keys[d] = key; 

                    });
                    //Return the latLng obj of the second closest point to the markers drag origin. If this point doesn't exist snap it to the actual closest point as this should always exist
                    return (path_pts[distance_keys[_.min(distances)]]);
                }

                 /** Converts numeric degrees to radians */
                if (typeof(Number.prototype.toRad) === "undefined") {
                    Number.prototype.toRad = function() {
                    return this * Math.PI / 180;
                }
                }

                
                function newPos (position){
                    
                var newPoint = polylineCoordinatesList.indexOf(position);
                
                   filesPoints[indeX] = newPoint;
                }
                
                google.maps.event.addDomListener(window, 'load', initialize);


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
                    <div class="tabbable" id="tabs-883724">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#panel-234896" data-toggle="tab">Synchronize multimedia</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-234896">

                                <h3>
                                    Synchronize multimedia files (Step 4)
                                </h3>
                                <br>

                                
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="galleria">
                                                <%
                                                    String temp = null;
                                                    for(int i = 0; i < loader.getMultimediaFiles().size(); i++){
                                                    if(System.getProperty("os.name").startsWith("Windows")){
                                                        File f = new File(loader.getMultimediaFiles().get(i).getPath());
                                                        temp = f.toURI().toString().substring(f.toURI().toString().lastIndexOf("/Logged/") + 8);
                                                    }else{
                                                        if (!loader.getMultimediaFiles().get(i).getPath().toString().contains("YTB")) {
                                                        String temp1 = loader.getMultimediaFiles().get(i).getPath().substring(38);
                                                        temp = temp1.replaceAll(" ", "%20");}
                                                    }
                                                    
                                                        String newPath = null;
                                                        
                                                        if (!loader.getMultimediaFiles().get(i).getPath().toString().contains("YTB")) {
                                                        
                                                            String extension = temp.substring(temp.lastIndexOf("."), temp.length());
                                                        
                                                            newPath = temp.substring(0,temp.lastIndexOf(".")) + "_THUMB" + extension; 
                                                        
                                                            out.println("<img src=\"" + newPath + "\" " + "data-title=\"" + temp.substring(temp.lastIndexOf("/") + 1, temp.length()) + "\">");
                                                        }
                                                        
                                                        else {
                                                            if (System.getProperty("os.name").startsWith("Windows")) {
                                                                newPath = temp.substring(temp.length() -11); }
                                                            else {
                                                                newPath = loader.getMultimediaFiles().get(i).getPath().substring(4);
                                                                
                                                                } 
                                                            out.println("<a href=\"http://www.youtube.com/watch?v=" + newPath + "\"><span class=\"video\">Watch this on Vimeo!</span></a>");
                                                            }
                                                            
                                                         }
                                                    
                                                %>
                                            </div>
                                        <script>
                                            
                                            $( document ) .ready(function() {
                                                Galleria.loadTheme('HTMLStyle/GalleryStyle/themes/classic2/galleria.classic.min.js');
                                                Galleria.configure({
                                                    transition: 'fade',
                                                    imageCrop: true,
                                                    wait: '20 000'
                                                });
                                                Galleria.run('.galleria', {
                                                    height: 320,
                                                    width: 'auto'
                                                });
                                            });
                                        </script>
                                        
                                        <br>
                                        <p style="line-height: 20px; text-align: center;"> <button id="inp" class="btn btn-default btn-success" onClick="startSync();">Start Synchronizing</button></p>
                                            
                                            
                                        </div>
                                        <div class="col-md-8">
                                            
                                            <div id="map_canvas"></div>
                                        </div></div>
                                      
                                    </div>
                         </div>
                     </div>                                        
		</div>
            </div>
	</div>
           
    </body>
</html>

