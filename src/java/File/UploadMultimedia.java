package File;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
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

/**
 *
 * @author matej_000
 */
public class UploadMultimedia extends HttpServlet {
    

    private String system = System.getProperty("os.name");
    private String path;
    
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
                //trackName = items.get(0).getString();
                //System.out.println(items.get(0).getString());
            } else {
                    System.out.println(item.getName());
                try {
                    HttpSession session = request.getSession();
                    String trackName =  session.getAttribute("trackName").toString();
                    if(system.startsWith("Windows")){
                        path = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\" + "Multimedia" + "\\";
                    }else{
                        path = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/" + trackName + "/" + "Multimedia" + "/";
                    }
                    new File(path).mkdirs();
                    item.write(new File(path, item.getName()));
                } catch (Exception ex) {
                    System.out.println("Error: Cannot save multimedia files!");
                }
            }
        }
         //Show result page.
         //request.getRequestDispatcher("HomePage.jsp").forward(request, response);
    }
}

