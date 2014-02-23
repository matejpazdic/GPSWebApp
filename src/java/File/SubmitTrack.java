package File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Parser.GPXParser;
import Database.DBLoginFinder;
import Database.DBTrackCreator;
import Logger.FileLogger;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
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
    
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          
        List<FileItem> items = null;
        try {
            items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }
        try {
            // Process regular form fields here the same way as request.getParameter().
            // You can get parameter name by item.getFieldName();
            // You can get parameter value by item.getString();
            //System.out.println(items.size());
            HttpSession session = request.getSession();
            trackName = session.getAttribute("trackName").toString();
            trackDescr = session.getAttribute("trackDescr").toString();
            trackActivity = session.getAttribute("trackActivity").toString();
            access = session.getAttribute("access").toString();

            
            String filename = trackName + ".gpx";
            if (system.startsWith("Windows")) {
                //pathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
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
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly founded multimedia files in STEP 3 for track " + trackName + " .");
            //System.out.println(pathToFile + " , " + pathToMultimediaFiles);
            parser.parseGpx(trackActivity, trackDescr);
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly parsed GPX file in STEP 3 for track " + trackName + " .");

            
            

            DBTrackCreator tCreator = new DBTrackCreator();
            DBLoginFinder finder = new DBLoginFinder();
            //Vymysliet ochranu proti -1 hodnote pri getUserId!!!
            

            
            tCreator.createNewTrack(trackName, trackDescr, trackActivity, pathToFile, finder.getUserId(session.getAttribute("username").toString()), 
                                                    parser.getStartAndEndDate().get(0).toString(), parser.getStartAndEndDate().get(1).toString(), access, parser.getStartAddress(), parser.getEndAddress(), parser.getTrackLengthKm(), parser.getTrackMinElevation(), parser.getTrackMaxElevation(), parser.getTrackHeightDiff(), parser.getTrackDuration());
            
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly created new track in STEP 3 for track " + trackName + " .");
        } catch (Exception ex) {
            System.out.println("Error: Unable to create .tlv file!");
            FileLogger.getInstance().createNewLog("ERROR: Unable to create user's " + request.getSession().getAttribute("username") + " track " + trackName + " in STEP 3 !!!");
            //vloyit oznacenie chyby parsera!!!
        }
        // Show result page.
        request.getRequestDispatcher("ShowTracks.jsp").forward(request, response);
    }
}
