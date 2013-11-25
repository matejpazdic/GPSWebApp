<%-- 
    Document   : skuska
    Created on : 1.11.2013, 19:36:17
    Author     : matej_000
--%>

<%@page import="Database.DBTrackEraser"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="Database.DBTrackFinder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String system = System.getProperty("os.name");
            int trkID = Integer.parseInt(request.getParameter("trkID"));
            DBTrackFinder trackFinder = new DBTrackFinder();
            String path = trackFinder.getTrackFilePath(trkID);
            if (system.startsWith("Windows")) {
                   path = path.replaceAll("/", "\\\\"); // vymazat pri pouziti na serveri LINUX!!!
                }
            out.println(path);
            FileUtils.deleteDirectory(new File(path));
            
            DBTrackEraser eraser = new DBTrackEraser();
            eraser.eraseTrack(trkID);
            
            response.sendRedirect("ShowTracks.jsp");
        %>
            
    </body>
</html>
