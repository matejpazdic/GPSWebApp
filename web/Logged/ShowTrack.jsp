<%-- 
    Document   : ShowTrack
    Created on : 21.11.2013, 13:51:05
    Author     : matej_000
--%>

<%@page import="Database.DBTrackFinder"%>
<%@page import="Parser.TLVLoader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
//Tu je nacitanie celeho suboru podla trackID. Robi sa to na zaciatku aby sa dali pouzivat detaily na celej stranke
//ako je napr. nazov trasy, description atd...
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%out.print(file);%></title>
    </head>
    <body>
        <h1><%out.print(file);%></h1>
        <h4><%out.print(loader.getTrackDescription());%></h4>
        <p>
        //Tu mas vypis jednotlivych track pointov do tvaru uz zadaneho pre javascript :). 
        //Pocuvaj tento subor nevymayuj, iba prepis obsah, pretoze uz je nastaveny v ShowTracks.jsp a da sa kliknut na jednotlivu trasu.
        <%
            for(int i =0; i < loader.getTrackPoints().size(); i++){
                out.print("new google.maps.LatLng(" + loader.getTrackPoints().get(i).getLatitude() + ", " + loader.getTrackPoints().get(i).getLongitude() + ")");
                if(i != loader.getTrackPoints().size()-1){
                    out.println(",");
                }
            }
         %>
        </p>
    </body>
</html>
