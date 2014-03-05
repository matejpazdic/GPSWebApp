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
public class SaveTrackInfo extends HttpServlet {
    
    private String pathToFile;
    private String trackName;
    private String trackDescr;
    private String trackActivity;
    private String access;
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
                access = items.get(3).getString();
               
                String filename;
                HttpSession session = request.getSession();
                session.setAttribute("trackNameExist", "False");
                filename = session.getAttribute("trackFilename").toString();
                String foldername =filename.substring(0, filename.lastIndexOf(".gpx"));
                //session.removeAttribute("trackFilename");
                session.setAttribute("trackName", trackName);
                session.setAttribute("trackDescr", trackDescr);
                session.setAttribute("trackActivity", trackActivity);
                session.setAttribute("access", access);


                if (system.startsWith("Windows")) {
                    String oldPathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\";
                    pathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                    
                    //String oldPathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\";
                    //pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                    File oldFile = new File(oldPathToFile);
                    File newFile = new File(pathToFile);
                    
                    if(oldFile.exists() && newFile.exists()){
                        System.out.println("Mam Rovnaku trasu!!!");
                        FileLogger.getInstance().createNewLog("Warning: User " + session.getAttribute("username") + " attempted to create duplicate of track with track name " + foldername + " !!!");
                        session.setAttribute("trackNameExist", "True");
                        request.getRequestDispatcher("UploadTrack2.jsp").forward(request, response);
                        //response.sendRedirect("UploadTrack2.jsp");
                        return;
                    }else{

                        oldFile.renameTo(newFile);

                        String old = pathToFile + "Temp.gpx"; // Pou+y=ivaj filename premennu!
                        String newS = pathToFile + trackName + ".gpx";
                        File oldF = new File(old);
                        File newF = new File(newS);
                        oldF.renameTo(newF);
                    }


                } else {
                    String oldPathToFile = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/" + "Temp" + "/";
                    pathToFile = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/" + trackName + "/";
                    File oldFile = new File(oldPathToFile);
                    File newFile = new File(pathToFile);
                    
                    if(oldFile.exists() && newFile.exists()){
                        System.out.println("Mam Rovnaku trasu!!!");
                        FileLogger.getInstance().createNewLog("Warning: User " + session.getAttribute("username") + " attempted to create duplicate of track with track name " + foldername + " !!!");
                        session.setAttribute("trackNameExist", "True");
                        request.getRequestDispatcher("UploadTrack2.jsp").forward(request, response);
                        //response.sendRedirect("UploadTrack2.jsp");
                        return;
                    }else{

                        oldFile.renameTo(newFile);

                        String old = pathToFile + "Temp.gpx"; // Pou+y=ivaj filename premennu!
                        String newS = pathToFile + trackName + ".gpx";
                        File oldF = new File(old);
                        File newF = new File(newS);
                        oldF.renameTo(newF);
                        //
                    }
                }

                //System.out.println(items.get(0).getString());
            } else {
                try {
                    // Process uploaded fields here.
                } catch (Exception ex) {
                   System.out.println("Cannot create a file!!!");
                   FileLogger.getInstance().createNewLog("ERROR: For user " + request.getSession().getAttribute("username") + " cannot create gpx file in STEP 2!!!");
                }
            }
        }
        // Show result page.
        request.getRequestDispatcher("UploadTrack3.jsp").forward(request, response);
        return;
    }
}

