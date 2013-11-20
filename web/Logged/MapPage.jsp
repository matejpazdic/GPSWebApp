<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="Windows-1250">
        <title>Example track</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">
        <link href="HTMLStyle/HomePageStyle/css/style.css" rel="stylesheet">

        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/scripts.js"></script>

        <style>

            #map_canvas {

                width: 800px;
                height: 650px;
                margin-left: auto ;
                margin-right: auto ;
            }
        </style>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBH31FxBV_cLA7hdbY2dBTUsJjAaDEE0MI&sensor=true"></script>
        <script>
            
             var map_options = {
                    center: new google.maps.LatLng(10.012869, 76.328802),
                    zoom: 17,
                    mapTypeId: google.maps.MapTypeId.HYBRID
                };
                
            var map;
            
            function initialize() {
                var map_canvas = document.getElementById('map_canvas');
                map = new google.maps.Map(map_canvas, map_options);

            }
            var polylineOK;

            function draw() {

                var a=0;

                var polylineCoordinatesList = [
                    new google.maps.LatLng(10.013566, 76.331549),
                    new google.maps.LatLng(10.013566, 76.331463),
                    new google.maps.LatLng(10.013503, 76.331313),
                    new google.maps.LatLng(10.013482, 76.331205),
                    new google.maps.LatLng(10.013419, 76.330926),
                    new google.maps.LatLng(10.013334, 76.330712),
                    new google.maps.LatLng(10.013313, 76.330411),
                    new google.maps.LatLng(10.013292, 76.330175),
                    new google.maps.LatLng(10.013228, 76.329854),
                    new google.maps.LatLng(10.013144, 76.329553),
                    new google.maps.LatLng(10.013059, 76.329296),
                    new google.maps.LatLng(10.012996, 76.329017),
                    new google.maps.LatLng(10.012869, 76.328802),
                    new google.maps.LatLng(10.012785, 76.328545),
                    new google.maps.LatLng(10.012700, 76.328223),
                    new google.maps.LatLng(10.012679, 76.328030),
                    new google.maps.LatLng(10.012658, 76.327837),
                    new google.maps.LatLng(10.012637, 76.327600),
                    new google.maps.LatLng(10.012573, 76.327322),
                    new google.maps.LatLng(10.012552, 76.327043),
                    new google.maps.LatLng(10.012552, 76.326807),
                    new google.maps.LatLng(10.012510, 76.326613),
                    new google.maps.LatLng(10.012447, 76.326399),
                    new google.maps.LatLng(10.012404, 76.326227)
                ];
                
                var polylineCoordinatesListFinal = [];

                i = 0;
                                        
                function drawingMap() {
                    polylineCoordinatesListFinal.push(polylineCoordinatesList[a]);
                    polylineOK = new google.maps.Polyline({
                    path: polylineCoordinatesListFinal,
                    strokeColor: '#FF0000',
                    geodesic: true,
                    strokeOpacity: 1.0,
                    strokeWeight: 2,
                    editable: false
                    });

                    polylineOK.setMap(map);
                
                    setTimeout(function() { a++; if (a < 23) { drawingMap(); } }, 70);
                    
                    
                };
            
                drawingMap();

                }
                
                function clearmap() {
                    initialize();
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
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Upload track<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="UploadFile.jsp">Upload track only</a>
                                        </li>
                                        <li>
                                            <a href="UploadFile.jsp">Upload track with multimedia files</a>
                                        </li>

                                        <li class="divider">
                                        </li>
                                        <li>
                                            <a href="#">Write new track</a>
                                        </li>
                                        <li class="divider">
                                        </li>
                                        <li>
                                            <a href="MapPage.jsp">Experimental button</a>
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
                                    <a href="#">About</a>
                                </li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>  Account<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="#">View account</a>
                                        </li>
                                        <li>
                                            <a href="#">Edit account</a>
                                        </li>
                                        <li>
                                            <a href="#">Delete account</a>
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
                                <a href="#panel-234896" data-toggle="tab">Overview</a>
                            </li>
                            <li>
                                <a href="#panel-42556" data-toggle="tab">Services for our users</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-234896">

                                <h3>
                                    Your XYZ track
                                </h3>

                                <br>

                                <div id="map_canvas"></div>

                                <br>
                                <br>
                                
                                <p style="line-height: 20px; text-align: center;"><button type="button" class="btn btn-sm btn-sucess" onclick="draw();">Play</button>    <button type="button" class="btn btn-sm btn-danger" onclick="clearmap();">Clear</button></p>
                                
                                <br>
                            </div>
                            <div class="tab-pane" id="panel-42556">

                                <h1>
                                    Our web service provide...
                                </h1>

                                <br>


                                <ul>
                                    <li>
                                        more then 10Gb space for user files
                                    </li>
                                    <li>
                                        adding tracks from GPS device
                                    </li>
                                    <li>
                                        adding a lot formats multimedia files
                                    </li>
                                    <li>
                                        drawing tracks on high quality map
                                    </li>
                                    <li>
                                        drawing advanced altitude graph
                                    </li>
                                    <li>
                                        presentating entire trip with relevant multimedia files
                                    </li>
                                    <li>
                                        possiblity draw own track on map (unrecorded trip)
                                    </li>
                                    <li>
                                        user-friendly page interface
                                    </li>
                                    <li>
                                        inteligent searching on website
                                    </li>
                                </ul>

                                <br>

                            </div>
                        </div>
                    </div>



                    <p>
                        This website is developing as a practical part of <strong>Ľubomír Petrus</strong> and <strong>Matej Pazdič</strong> thesis. Take note of the copyrights owns <em> Petrus </em> , <em> Pazdič </em> and at last but not least<em> Technical University of Košice, Faculty of Electrical Engineering and Informatics.</em> Thanks our close people for support and a special thank you we would like to dedicate doc. Zdenek Havlice. We salute you...<small>   -->> No makame dalej tak trimce palce... Ani Poruban nas nezastavi. Btw nasa posila: <a href="http://www.saris.sk">www.saris.sk</a></small>
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>


