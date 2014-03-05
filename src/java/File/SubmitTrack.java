package File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Parser.GPXParser;
import Database.DBLoginFinder;
import Database.DBTrackCreator;
import File.Video.YouTubeAgent;
import Logger.FileLogger;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.jni.OS;

/**
 *
 * @author matej_000
 */
public class SubmitTrack extends HttpServlet {
    
    private String pathToFile;
    private String pathToMultimediaFiles;
    private String trackName;
    private String trackDescr;
    private String access;
    private String trackActivity;
    private String system = System.getProperty("os.name");
    

protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Process regular form fields here the same way as request.getParameter().
            // You can get parameter name by item.getFieldName();
            // You can get parameter value by item.getString();
            //System.out.println(items.size());
            
            YouTubeAgent uploader = new YouTubeAgent("skuska.api.3", "skuskaapi3");
            
            String arrayString = request.getParameter("textBox");
            String[] list = arrayString.split(",");
            ArrayList<String> filePaths = new ArrayList<String>();
            ArrayList<Integer> filePoints = new ArrayList<Integer>();
            for(int i = 0; i < list.length; i++){
                String[] temp = list[i].split(";");
                //String ext = temp[0].substring(temp[0].lastIndexOf("."));
                filePaths.add(temp[0]);
                //System.out.println("File: " + temp[0].substring(temp[0].lastIndexOf("/"), temp[0].lastIndexOf("_THUMB")) + ext);
                
                System.out.println("Cesta: " + temp[0]);
                System.out.println("Point: " + temp[1]);
                filePoints.add(Integer.parseInt(temp[1]));
            }
            
            
            
            HttpSession session = request.getSession();
            trackName = session.getAttribute("trackName").toString();
            trackDescr = session.getAttribute("trackDescr").toString();
            trackActivity = session.getAttribute("trackActivity").toString();
            access = session.getAttribute("access").toString();

            
            String filename = trackName + ".gpx";
            if (system.startsWith("Windows")) {
                pathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                //pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                pathToMultimediaFiles = pathToFile + "\\" + "Multimedia" + "\\";
                File fTemp = new File(pathToMultimediaFiles);
                if(!fTemp.exists()){
                    fTemp.mkdirs();
                }
            } else {
                pathToFile = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/" + trackName + "/";
                pathToMultimediaFiles = pathToFile + "Multimedia" + "/";
                File fTemp = new File(pathToMultimediaFiles);
                if(!fTemp.exists()){
                    fTemp.mkdirs();
                }
            }

            GPXParser parser = new GPXParser(pathToFile, filename, session.getAttribute("username").toString(), trackName);
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly created GPXParser in STEP 3 for track " + trackName + " .");
            parser.searchForMultimediaFiles(pathToMultimediaFiles);
            
            System.out.println("Mam Multi: " + parser.getFiles().size() + " " + filePoints.size());
            
            int index = parser.getFiles().size();
            for(int i = 0; i < index; i++){
                if(filePoints.get(i) != -1){
                    if(filePaths.get(i).startsWith("YTB")){
                        parser.getFiles().get(i).setPath(filePaths.get(i));
                    }
                    parser.getFiles().get(i).setDate(parser.getTrack().get(filePoints.get(i)).getTime());
                }else{
                    if(filePaths.get(i).startsWith("YTB")){
                        uploader.deleteVideo(filePaths.get(i).substring(4));
                        System.out.println("Vymazujem " + filePaths.get(i).substring(4));
                    }
                    parser.getFiles().remove(i);
                    filePaths.remove(i);
                    filePoints.remove(i);
                    System.out.println("Mam Multim: " + parser.getFiles().size() + " " + filePoints.size());
                    index--;
                    i--;
                }
            }
            
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly founded multimedia files in STEP 3 for track " + trackName + " .");
            parser.parseGpx(trackActivity, trackDescr);
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly parsed GPX file in STEP 3 for track " + trackName + " .");

            
            

            DBTrackCreator tCreator = new DBTrackCreator();
            DBLoginFinder finder = new DBLoginFinder();
            

            
            tCreator.createNewTrack(trackName, trackDescr, trackActivity, pathToFile, finder.getUserId(session.getAttribute("username").toString()), 
                                                    parser.getStartAndEndDate().get(0).toString(), parser.getStartAndEndDate().get(1).toString(), access, parser.getStartAddress(), parser.getEndAddress(), parser.getTrackLengthKm(), parser.getTrackMinElevation(), parser.getTrackMaxElevation(), parser.getTrackHeightDiff(), parser.getTrackDuration());
            
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly created new track in STEP 3 for track " + trackName + " .");
        } catch (Exception ex) {
            System.out.println("Error: Unable to create .tlv file!");
            FileLogger.getInstance().createNewLog("ERROR: Unable to create user's " + request.getSession().getAttribute("username") + " track " + trackName + " in STEP 3 !!!");
            //vloyit oznacenie chyby parsera!!!
            ex.printStackTrace();
        }
        // Show result page.
        request.getRequestDispatcher("ShowTracks.jsp").forward(request, response);
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