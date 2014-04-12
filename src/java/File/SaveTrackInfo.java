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
public class SaveTrackInfo extends HttpServlet {
    
    private String pathToFile;
    private String trackName;
    private String trackDescr;
    private String trackActivity;
    private String access;
    private String system = System.getProperty("os.name");
    

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
        
                // Process regular form fields here the same way as request.getParameter().
                // You can get parameter name by item.getFieldName();
                // You can get parameter value by item.getString();
                //System.out.println(items.size());
                trackName = request.getParameter("trkName").toString();
                
                if(request.getParameter("descr") != null){
                    trackDescr = request.getParameter("descr").toString();
                }else{
                    trackDescr = "";
                }
                
                trackActivity = request.getParameter("Activity").toString();
                access = request.getParameter("Access").toString();
               
                String filename;
                HttpSession session = request.getSession();
                
                FileLogger.getInstance().createNewLog("User " + session.getAttribute("username") + "is in servlet SaveTrackInfo, and name of track is " + session.getAttribute("trackName"));
                
                session.setAttribute("trackNameExist", "False");
                filename = session.getAttribute("trackFilename").toString();
                String foldername =filename.substring(0, filename.lastIndexOf(".gpx"));
                //session.removeAttribute("trackFilename");
                session.setAttribute("trackName", trackName);
                session.setAttribute("trackDescr", trackDescr);
                session.setAttribute("trackActivity", trackActivity);
                session.setAttribute("access", access);


                if (system.startsWith("Windows")) {
                   //String oldPathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\";
                   //pathToFile = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                    
                    String oldPathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\";
                    pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
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
                        String pathToMultimedia = oldPathToFile + "Multimedia\\";
                        File f = new File(pathToMultimedia);
                        if(f.exists()){
                            FileUtils.forceDelete(f);
                        }
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
                        String pathToMultimedia = oldPathToFile + "Multimedia/";
                        File f = new File(pathToMultimedia);
                        if (f.exists()) {
                            //f.delete();
                            FileUtils.forceDelete(f);
                        }
                    }
                }

                //System.out.println(items.get(0).getString());
        // Show result page.
        request.getRequestDispatcher("UploadTrack3.jsp").forward(request, response);
        return;
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

