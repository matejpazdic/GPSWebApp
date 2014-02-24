package File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Logger.FileLogger;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

/**
 *
 * @author matej_000
 */
public class UploadTrackFileOnly extends HttpServlet {
    
    private String pathToFile;
    private String system = System.getProperty("os.name");
    
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    
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
                
                //System.out.println(items.get(0).getString());
            } else {
                try {
                    // Process uploaded fields here.
                    HttpSession session = request.getSession();
                    session.setAttribute("trackNameExist", "False");
                    if (system.startsWith("Windows")) {
                        String tempPath = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
                        //String tempPath = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
                        File tempFile = new File(tempPath);
                        if (tempFile.exists()) {
                            System.out.println("Mam temp a vymazujem!");
                            tempFile.delete();
                            FileLogger.getInstance().createNewLog("Warning: Found old temp folder which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
                        }
                    }else{
                        String tempPath = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/Temp" + "/";
                        //String tempPath = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
                        File tempFile = new File(tempPath);
                        if (tempFile.exists()) {
                            System.out.println("Mam temp a vymazujem!");
                            tempFile.delete();
                            FileLogger.getInstance().createNewLog("Warning: Found old temp folder which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
                        }
                    }
                    
                    
                    String filename = item.getName();

                    String foldername = item.getName().substring(0, item.getName().lastIndexOf(".gpx"));
                    session.removeAttribute("trackFilename");
                    
                    if(system.startsWith("Windows")){
                        pathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
                        //pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
                    }else{
                        pathToFile = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/Temp" + "/";
                    }
                    
                    new File(pathToFile).mkdirs();                    
                    File file = new File(pathToFile, "Temp.gpx"); // Write to destination file. Pouyivaj filename!
                    item.write(file); // Write to destination file.
                    
                    //JSONObject json = new JSONObject(request.getParameter("vari"));
                    
//                    Iterator it = json.keys();
//                    
//                    while(it.hasNext()){
//                        String key = it.next().toString();
//                        
//                        System.out.println("Skuska: " + key + " >>> " + json.get(key));
//                    }
                    
                    FileLogger.getInstance().createNewLog("Successfully uploaded user's " + session.getAttribute("username") + " GPX file " + foldername + " in STEP 1.");
                    
                    //String replacedFilename = filename.replaceAll("[^a-z|0-9|A-Z|_| |+|\\-|(|)|.]", "");
                    session.setAttribute("trackFilename",filename );
                } catch (Exception ex) {
                   System.out.println("Cannot create a file!!!");
                   FileLogger.getInstance().createNewLog("ERROR: Cannot upload user's " + request.getSession().getAttribute("username") + " GPX file " + item.getName() + " in STEP 1!!!");
                   //ex.printStackTrace();
                }
            }
        }
        // Show result page.
        request.getRequestDispatcher("UploadTrack2.jsp").forward(request, response);
    }
}

