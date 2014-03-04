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
        <title><%out.print("Track: " + file);%></title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">
        <link href="HTMLStyle/HomePageStyle/css/style.css" rel="stylesheet">
        
        <link href="http://vjs.zencdn.net/4.3/video-js.css" rel="stylesheet">
        
        <link type="text/css" rel="stylesheet" href="HTMLStyle/GalleryStyle/themes/classic/galleria.classic.css">

        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/scripts.js"></script>
        
        <script src="http://vjs.zencdn.net/4.3/video.js"></script>
        
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>
        
        <script src="HTMLStyle/GalleryStyle/galleria-1.3.3.min.js"></script>

        <script type="text/javascript" src="HTMLStyle/GalleryStyle/themes/classic/galleria.classic.min.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    

        <style>
            
                       
            #map_canvas {
                
                display: block;
                width: 100%;
                height: 550px;
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
                    
                    var photoTimeout;
                    var presentTimeout;
                    
                    var isVideoShowed = false;
                    var isPlayerReady = false;
                    var isVideoPaused = false;
                    
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
      
                
            %>

                var tag = document.createElement('script');
                    tag.src = "https://www.youtube.com/iframe_api";
                var firstScriptTag = document.getElementsByTagName('script')[0];
                    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
            
                var player;
                            function onYouTubeIframeAPIReady() {
                                player = new YT.Player('ytplayer', {
                                  playerVars: {
                                    autohide: "1",
                                    modestbranding: "1",
                                    rel: "0",
                                    showinfo: "0",
                                    iv_load_policy: "3",
                                    theme: "light"
                                  },
                                  height: '275',
                                  width: '100%',
                           
                                  events: {
                                    'onReady': onPlayerReady,
                                    'onStateChange': onPlayerStateChange
                                  }
                                });
                              }
                            
                            function onPlayerReady(event) {
                                event.target.playVideo();
                                isPlayerReady = true;
                                document.getElementById("play").disabled = false;
                                //alert("som ready");
                            }
                            
                            function onPlayerStateChange(event) {
                                    if (event.data == YT.PlayerState.ENDED) {
                                      next();                                     
                                    }
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
                    drawChart();
                    
            }

            function draw() {
                
                    document.getElementById("play").disabled = true;
                    document.getElementById("pause").disabled = false;
                    document.getElementById("stop").disabled = false;
                    
                    isPresented = true;
                    //graphDataFinal = google.visualization.arrayToDataTable(gData);    ZAKOMENTOVANE POZOR
                    
                      if (marker) {    
                        marker.setMap(null);
                      }
                    
                    clearTimeout(presentTimeout); //  SKUSKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa

                    if(isPolylineAlreadyCreated == false){
                        
                        if (isAlreadyMark == true) {
                        mark.setMap(null); }
                        
                    polylineOK.setPath([]);
                    polylineOK.setMap(null);
                    
                    a = 0;
                    i = 0;
                    
                    polylineOK = new google.maps.Polyline({
                         path: polylineCoordinatesListFinal,
                         strokeColor: '#FF0000',
                         geodesic: true,
                         strokeOpacity: 1.0,
                         strokeWeight: 2,
                         editable: false
                         });
                     isEnd = false;    
                     }
                    isPolylineAlreadyCreated = true;
                    function drawingMap() {
              
                            clearTimeout(presentTimeout); //  SKUSKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa
              
                            polylineCoordinatesListFinal.push(polylineCoordinatesList[a]);
                            polylineOK.setPath(polylineCoordinatesListFinal);
                            polylineOK.setMap(map);
                            
                            //graphEx = google.visualization.arrayToDataTable(graphDataFinal);
                                
                                chart.setSelection([{row:a, column:null}]);
                            
                            
                            if (isFiles[a] == true) {
                                isEnd = true;
                                presentMultimedia();
                            }     
                            presentTimeout = setTimeout(function() { if (isEnd != true) {a++}; if (a <= polylineCoordinatesList.length) { if (isEnd != true) drawingMap(); } else clearmap(); }, 40);
                    };
                    drawingMap();
            }
            
            
            
            
            
            function presentMultimedia(){
                            
                                    if(a == filesPoints[index]){
                                                             
                                        document.getElementById("next").disabled = false;   
                                        
                                        if (filesPath[index].toString().indexOf("YTB") === 0) {

                                            marker = new google.maps.Marker({
                                                position: polylineCoordinatesList[a],
                                                map: map,
//                                              icon: iconF,
                                                title: 'Kalvarka :)'
                                             });
                                
                                              marker.setMap(map);
                                            
                                            document.getElementById('ytplayer').style.display="inline";
                                            document.getElementById('img').style.display="none";
                                            
                                            if (isPlayerReady && !isVideoPaused) {
                                                player.loadVideoById(filesPath[index].toString().substr(4), 0, "default");
                                                isVideoShowed = true;
                                            }
                                            
                                            else if (isPlayerReady && isVideoPaused){
                                          
                                                player.playVideo();
                                                isVideoPaused = false;
                                            }
                                            
                                        } else {
                                         
                                             marker = new google.maps.Marker({
                                                position: polylineCoordinatesList[a],
                                                map: map,
//                                              icon: iconF,
                                                title: 'Kalvarka :)'
                                             });
                                
                                             marker.setMap(map);
                                             
                                                        document.getElementsByName("img")[0].src = filesPath[index];
                                                        document.getElementsByName("img")[0].style.height = 'auto';
                                                        
                                                  
                                                        photoTimeout = setTimeout(function() {next();return} , 5000);   // automaticky mod
                                                    }
                                        }
                                        else {
                                                if(filesPoints.length != index){
                                                    index++;
                                                    presentMultimedia();
                                                } else{
                                                isEnd = false;
                                                a++;
                                                index = 0;
                                                draw();
                                                }
                                    }

            }
            
            
            function next() {
               
                document.getElementById("next").disabled = true;
                
                clearTimeout(photoTimeout);
                
                document.getElementsByName("img")[0].src = "HTMLStyle/PV.PNG";
              
                document.getElementById('ytplayer').style.display="none";
                document.getElementById('img').style.display="inline";
                
                if(isVideoShowed) {
                    player.stopVideo();
                    isVideoShowed = false;
                    isVideoPaused = false;
                    //alert("som v isVideoShowed v nexte");
                }
                
                if (marker) {    
                marker.setMap(null);
                }
            
                if(filesPoints.length != index){
                    index++;
                    presentMultimedia();
                } else {
                    a++;
                    isEnd = false;                                               
                    draw();
               }
                
            }
            
            function clearmap() {
            
            document.getElementById("play").disabled = false;
            document.getElementById("pause").disabled = true;
            document.getElementById("stop").disabled = true;
            document.getElementById("next").disabled = true;
            
            clearTimeout(photoTimeout); // automaticky mod
            
            isEnd = true;
            polylineCoordinatesListFinal = [];
            a = 0;
            index = 0;
            isPolylineAlreadyCreated = false;
            
            document.getElementsByName("img")[0].src = "HTMLStyle/PV.PNG";
            
            document.getElementById('ytplayer').style.display="none";
            document.getElementById('img').style.display="inline";
            
            if(isVideoShowed) {
                    player.stopVideo();
                    isVideoShowed = false;
                    isVideoPaused = false;
                    //alert("som v isVideoShowed v stope");
                }
            
            
            initialize();             
            }

            google.maps.event.addDomListener(window, 'load', initialize); 
        
            google.load("visualization", "1", {packages:["corechart"]});
            
            function drawChart() {
                              
                var graphData = google.visualization.arrayToDataTable(gData);

               
                options = {
                    chartArea:{left:50,top:35, height: "70%", width:"100%"},
                    hAxis:  {textPosition: 'none' , title: '',  titleTextStyle: {color: '#666'}},
                    vAxis: { gridlines: {count: 8}},
                    legend: {position: "top", alignment: "center", textStyle: {bold: true, fontSize: "12"}},
                    colors: ['green','blue']
                    };
                    
                    chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                    
                    function selectHandler() {
                       
                        var selectedItem = chart.getSelection()[0];
                        if (selectedItem && isPresented == false) {
                            var value = graphData.getValue(selectedItem.row,0);

                            if (isAlreadyMark == true) {
                                   mark.setMap(null);
                            }
                            mark = new google.maps.Marker({
                                                position: polylineCoordinatesList[value],
                                                map: map,
//                                              icon: iconF,
                                                animation: google.maps.Animation.DROP,
                                                title: 'Kalvarka :)'
                                             });
                                             
                                             mark.setMap(map);
                                             
                                             isAlreadyMark=true;
                             
                        }
                     
                                
                    }

                    google.visualization.events.addListener(chart, 'select', selectHandler);
                    chart.draw(graphData, options);
                    
                }
       
                function pause() {
                    
                    document.getElementById("play").disabled = false;
                    document.getElementById("pause").disabled = true;
                    
                    clearTimeout(photoTimeout);
                    clearTimeout(presentTimeout);
                    
                    if(isVideoShowed) {
                        player.pauseVideo();
                        isVideoPaused = true;
                        //alert("pauseVideo");
                    }
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
                    
                    <div class="tabbable" id="tabs-747520">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-740839" data-toggle="tab">Track on map</a>
					</li>
					<li>
						<a href="#panel-536799" data-toggle="tab">Track information</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-740839">
						                                            
                                        <h3> <% out.print(file); %></h3>
                                        <br>
                                        
                                        <div class="row">
                                        <div class="col-md-8">
                                            <div id="map_canvas"></div>
                                        </div>
                                        
                                        <div class="col-md-4" style="height:550px">
                                                <div class="text-center" style="height:275px;">
                                                <img name="img" src="HTMLStyle\PV.PNG" alt="..." class="fixed_width">
     
                                                </div>
                                            
                                            <div id="ytplayer" style="display:none" >
                                
                                            </div>
                                            <img id="img" name="img" src="HTMLStyle\VP.PNG" alt="..." class="fixed_width" style="height:100%">
                                            
                                            
					</div></div>
                                        
                                        
                                        <div id="chart_div" style="width: 100%; height: 200px;"></div>                                                                             
                                            <p style="line-height: 20px; text-align: center;">
                                                <!--<div class="btn-group">-->
                                                    <button id="play" type="button" class="btn btn-sm btn-danger" onclick="draw();" disabled> <span class="glyphicon glyphicon-play"></span></button>
                                                    <button id="pause" type="button" class="btn btn-sm btn-danger" onclick="pause();" disabled> <span class="glyphicon glyphicon-pause"></span></button> 
                                                    <button id="next" type="button" class="btn btn-sm btn-danger" onclick="next();" disabled> <span class="glyphicon glyphicon-step-forward"></span></button>
                                                    <button id="stop" type="button" class="btn btn-sm btn-danger" onclick="clearmap();" disabled> <span class="glyphicon glyphicon-stop"></span></button>
                                                <!--</div>-->
                                            </p>
                                            
					</div>
					<div class="tab-pane" id="panel-536799">
                                        
                                        <h3>Info and multimedia files</h3>

                                        <br>

                                        <div class="col-md-3">

                                        <% 
                                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date modifiedDate = df.parse(trackFinder.getChangeDate(trkID).substring(0,19));
                                        modifiedDate.toGMTString(); 
                            
                                        %>    
                                            
                                        <label for="TrackDesc" style="font-size:13px; margin-bottom: 0px">Track description</label>
                                        <h6> <% out.println(loader.getTrackDescription()); %> </h6>

                                        <label for="TrackActivity" style="font-size:13px; margin-bottom: 0px">Track activity</label>
                                        <h6> <% out.println(loader.getTrackType());%> </h6>
                                        
<!--                                        <label for="Privacy" style="font-size:13px; margin-bottom: 0px">Privacy</label>
                                        <h6> <% out.println(trackFinder.getAccess(trkID));%> </h6>
                                        
                                        <label for="Uploaded" style="font-size:13px; margin-bottom: 0px">Uploaded</label>
                                        <h6> <% out.println(trackFinder.getUploadedDate(trkID));%> </h6>-->
                                        
                                        <label for="StartPlace" style="font-size:13px; margin-bottom: 0px">Start place</label>
                                        <h6> <% out.println(loader.getStartAddress());%> </h6>
                                        
                                        <label for="EndPlace" style="font-size:13px; margin-bottom: 0px">End place</label>
                                        <h6> <% out.println(loader.getEndAddress());%> </h6>
                                        
                                        <label for="Track Length" style="font-size:13px; margin-bottom: 0px">Track Length</label>
                                        <h6> <% out.println(trackFinder.getTrackLengthKm(trkID));%> km</h6>
                                        
                                        <label for="MinElevation" style="font-size:13px; margin-bottom: 0px">Min Elevation</label>
                                        <h6> <% out.println(trackFinder.getMinElevation(trkID));%> m </h6>
                                        
                                        <label for="MaxElevation" style="font-size:13px; margin-bottom: 0px">Max Elevation</label>
                                        <h6> <% out.println(trackFinder.getMaxElevation(trkID));%> m</h6>

                                        <label for="HeightDiff" style="font-size:13px; margin-bottom: 0px">Height Difference</label>
                                        <h6> <% out.println(trackFinder.getHeightDifference(trkID));%> m</h6>
                                        
                                        <label for="StartDate" style="font-size:13px; margin-bottom: 0px">Start</label>
                                        <h6> <% out.println(trackFinder.getTrackStartDate(trkID));%> </h6>
                                        
                                        <label for="EndDate" style="font-size:13px; margin-bottom: 0px">End</label>
                                        <h6> <% out.println(trackFinder.getTrackEndDate(trkID));%> </h6>
                                        
                                        <label for="Duration" style="font-size:13px; margin-bottom: 0px">Duration</label>
                                        <h6> <% out.println(trackFinder.getTrackDuration(trkID));%> </h6>
   
                                        
                                        </div>

                                        <div class="col-md-9">
                                            
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
                                                Galleria.loadTheme('HTMLStyle/GalleryStyle/themes/classic/galleria.classic.min.js');
                                                Galleria.configure({
                                                    transition: 'fade',
                                                    imageCrop: true,
                                                    wait: '20 000'
                                                });
                                                Galleria.run('.galleria', {
                                                    height: 640,
                                                    width: 'auto'
                                                });
                                            });
                                        </script>
                                            
                                        </div>
				</div>
			</div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
