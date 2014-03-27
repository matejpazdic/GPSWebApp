/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package File;

import Database.DBLoginFinder;
import Database.DBTrackCreator;
import Logger.FileLogger;
import Parser.GPXParser;
import Parser.Utilities.MultimediaSearcher;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author matej_000
 */
public class WriteNewTrack extends HttpServlet {
    
    String system = System.getProperty("os.name");
    String pathToFile = null;
    String pathToMultimedia = null;
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String trackName = session.getAttribute("trackName").toString();
        String trackDescr = session.getAttribute("trackDescr").toString();
        String trackActivity = session.getAttribute("trackActivity").toString();
        String access = session.getAttribute("access").toString();

        session.setAttribute("trackNameExist", "False");
//        if (system.startsWith("Windows")) {
//            String tempPath = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
//            //String tempPath = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
//            File tempFile = new File(tempPath);
//            if (tempFile.exists()) {
//                System.out.println("Mam temp a vymazujem!");
//                FileUtils.deleteDirectory(tempFile);
//                //tempFile.delete();
//                FileLogger.getInstance().createNewLog("Warning: Found old temp folder which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
//            }
//        } else {
//            String tempPath = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/Temp" + "/";
//            File tempFile = new File(tempPath);
//            if (tempFile.exists()) {
//                System.out.println("Mam temp a vymazujem!");
//                FileUtils.deleteDirectory(tempFile);
//                FileLogger.getInstance().createNewLog("Warning: Found old temp folder which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
//            }
//        }
        
        if (system.startsWith("Windows")) {
           pathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
           pathToMultimedia = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\Multimedia\\";
            //pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
           //pathToMultimedia = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\Multimedia\\";
        } else {
            pathToFile = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/Temp" + "/";
            pathToMultimedia = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/Temp" + "/Multimedia/";
        }
        
        File tempF = new File(pathToFile + "Temp.txt");
        if(tempF.exists()){
            FileUtils.forceDelete(tempF);
            FileLogger.getInstance().createNewLog("Warning: Found tamp file Temp.txt in WriteNewTrack which belongs to user " + session.getAttribute("username") + " while writing new track " + trackName + " !!!");
        }
        
        File multF = new File(pathToMultimedia);
        MultimediaSearcher searcher = new MultimediaSearcher();
        searcher.setSearchFolder(pathToMultimedia);
        String[] files = searcher.startSearchWithoutTrack();
        if(files.length >= 1){
            session.setAttribute("isMultimedia", "True");
        }
        
        new File(pathToFile).mkdirs();
        File file = new File(pathToFile, "Temp.txt"); // Write to destination file. Pouyivaj filename!
        boolean create = file.createNewFile();
        //System.out.println("Vytvoril: " + create);
        
        if(file.exists()){
            String latLngStr = request.getParameter("textBox");
            
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter buf = new BufferedWriter(writer);
            
            writer.append(latLngStr);
            
            buf.close();
            writer.close();
        }
        if(session.getAttribute("isMultimedia") != null){
            request.getRequestDispatcher("SynchronizeDrawTrack.jsp").forward(request, response);
        }else{
            try {
                
                String pathToTemp;
                String pathToTempFile;
                String pathToMultimediaFiles;
                
                String filename = trackName + ".gpx";
                if (system.startsWith("Windows")) {
                    pathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                    pathToTemp = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\";
                    pathToTempFile = pathToFile + "Temp.txt";
                    //pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                     //pathToTemp = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\";
                    //pathToTempFile = pathToFile + "Temp.txt";
                    pathToMultimediaFiles = pathToFile + "Multimedia" + "\\";
//                    File fTemp = new File(pathToMultimediaFiles);
//                    if(!fTemp.exists()){
//                        fTemp.mkdirs();
//                    }
                } else {
                    pathToFile = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/" + trackName + "/";
                    pathToTemp = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/" + "Temp" + "/";
                    pathToTempFile = pathToFile + "Temp.txt";
                    pathToMultimediaFiles = pathToFile + "Multimedia" + "/";
//                    File fTemp = new File(pathToMultimediaFiles);
//                    if(!fTemp.exists()){
//                        fTemp.mkdirs();
//                    }
                }
                
                File oldFile = new File(pathToTemp);
                File newFile = new File(pathToFile);
                oldFile.renameTo(newFile);
                
                GPXParser parser = new GPXParser(pathToFile, filename, session.getAttribute("username").toString(), trackName);
                parser.readFromTrackPoints(pathToTempFile, trackName, trackDescr);
                FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly created GPXParser in STEP 3 for track " + trackName + " .");
                parser.searchForMultimediaFilesWithoutCorrection(pathToMultimediaFiles);
                
                FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly founded multimedia files in STEP 3 for track " + trackName + " .");
                parser.parseFromTrackPoints(trackActivity, trackDescr);
                FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly parsed GPX file in STEP 3 for track " + trackName + " .");
                
                
                
                
                DBTrackCreator tCreator = new DBTrackCreator();
                DBLoginFinder finder = new DBLoginFinder();
                
                
                
                tCreator.createNewTrack(trackName, trackDescr, trackActivity, pathToFile, finder.getUserId(session.getAttribute("username").toString()),
                        parser.getStartAndEndDate().get(0).toString(), parser.getStartAndEndDate().get(1).toString(), access, parser.getStartAddress(), parser.getEndAddress(), parser.getTrackLengthKm(), parser.getTrackMinElevation(), parser.getTrackMaxElevation(), parser.getTrackHeightDiff(), parser.getTrackDuration(), "Drawed");
                
                FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly created new track in STEP 3 for track " + trackName + " .");
                
            } catch (Exception ex) {
                System.out.println("Error: Unable to create .tlv file!");
            FileLogger.getInstance().createNewLog("ERROR: Unable to create user's " + request.getSession().getAttribute("username") + " track " + trackName + " in STEP 3 !!!");
            //vloyit oznacenie chyby parsera!!!
            ex.printStackTrace();
            }
            request.getRequestDispatcher("ShowTracks.jsp").forward(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
