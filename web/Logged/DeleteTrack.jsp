<%-- 
    Document   : skuska
    Created on : 1.11.2013, 19:36:17
    Author     : matej_000
--%>

<%@page import="File.Video.YouTubeAgent"%>
<%@page import="File.FileImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Parser.TLVLoader"%>
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
            YouTubeAgent agent = new YouTubeAgent("skuska.api3@gmail.com", "skuskaapi3");
            String system = System.getProperty("os.name");
            int trkID = Integer.parseInt(request.getParameter("trkID"));
            DBTrackFinder trackFinder = new DBTrackFinder();
            String path = trackFinder.getTrackFilePath(trkID);
            if (system.startsWith("Windows")) {
                   path = path.replaceAll("/", "\\\\");
                }
            out.println(path);
            
            TLVLoader loader = new TLVLoader();
            loader.readTLVFile(path, trackFinder.getTrackFileName(trkID));
            ArrayList<FileImpl> files = loader.getMultimediaFiles();
            System.out.println("Musim deletnut: " + files.size());
            
            for(int i = 0; i < files.size(); i++){
                String filePath = files.get(i).getPath();
                System.out.println("Som tu konecne!");
                if(filePath.startsWith("YTB ")){
                    String videoEntryID = filePath.substring(filePath.indexOf("YTB ") + 4);
                    System.out.println("DELETE: " + filePath + " ??? " + videoEntryID);
                    agent.deleteVideo(videoEntryID);
                }
            }
            
            FileUtils.deleteDirectory(new File(path));
            
            DBTrackEraser eraser = new DBTrackEraser();
            eraser.eraseTrack(trkID);
            
            response.sendRedirect("ShowTracks.jsp");
        %>
            
    </body>
</html>
