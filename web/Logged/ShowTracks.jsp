<%-- 
    Document   : ShowTracks
    Created on : 1.11.2013, 16:09:41
    Author     : matej_000
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Database.DBLoginFinder"%>
<%@page import="Database.DBTrackFinder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><% out.println(session.getAttribute("username").toString() + "  " + "submited tracks!"); %></h1>
        <%
            DBTrackFinder trackFinder = new DBTrackFinder();
            DBLoginFinder loginFinder = new DBLoginFinder();
            int userID = loginFinder.getUserId(session.getAttribute("username").toString());
            ArrayList<String> tracks = trackFinder.getUserTracks(userID);
            ArrayList<String> trackFiles = trackFinder.getUserTracksFiles(userID);
            ArrayList<Integer> trackIDs = trackFinder.getTracksIDs(userID);
            for(int i = 0; i < tracks.size(); i++){
                out.println("<a href=ShowTrack.jsp?trkID=" + trackIDs.get(i) + ">"+tracks.get(i)+"</a>" + "    >>>   " + trackFiles.get(i));
                out.println("    ");
                out.println("<a href=DeleteTrack.jsp?trkID=" + trackIDs.get(i) + ">=Delete=</a>");
                out.println("<br>-----------------------------------------------------------------------------<br>");
            }
        %>
    </body>
</html>
