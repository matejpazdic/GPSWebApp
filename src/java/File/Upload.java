package File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Parser.GPXParser;
import Database.DBLoginFinder;
import Database.DBTrackCreator;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.jni.OS;

/**
 *
 * @author matej_000
 */
public class Upload extends HttpServlet {
    
    private String pathToFile;
    private String trackName;
    private String trackDescr;
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
        for (FileItem item : items) {
            if (item.isFormField()) {
                // Process regular form fields here the same way as request.getParameter().
                // You can get parameter name by item.getFieldName();
                // You can get parameter value by item.getString();
                //System.out.println(items.size());
                trackName = items.get(0).getString();
                trackDescr = items.get(1).getString();
                trackActivity = items.get(2).getString();
                
                //System.out.println(items.get(0).getString());
            } else {
                try {
                    // Process uploaded fields here.
                    String filename = trackName + ".gpx"; // Get filena
                    HttpSession session = request.getSession();
                    session.removeAttribute("trackFilename");
                    
                    if(system.startsWith("Windows")){
                        pathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                    }else{
                        pathToFile = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/" + trackName + "/";
                    }
                    new File(pathToFile).mkdirs();                    
                    File file = new File(pathToFile, filename); // Write to destination file.
                    item.write(file); // Write to destination file.
                    
                    GPXParser parser = new GPXParser(pathToFile, filename);
                    parser.parseGpx(trackActivity, trackDescr);
                    
                    DBTrackCreator tCreator = new DBTrackCreator();
                    DBLoginFinder finder = new DBLoginFinder(); 
                    //Vymysliet ochranu proti -1 hodnote pri getUserId!!!
                    tCreator.createNewTrack(trackName , trackDescr, trackActivity, pathToFile, finder.getUserId(session.getAttribute("username").toString()));
                } catch (Exception ex) {
                   System.out.println("Cannot create a file!!!");
                }
            }
        }
        // Show result page.
        request.getRequestDispatcher("HomePage.jsp").forward(request, response);
    }
}

