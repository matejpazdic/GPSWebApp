/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matej_000
 */
public class FileLogger {
    
    private static FileLogger logger = null;
    private String system = System.getProperty("os.name");
    private final String fileName = "GPSWebAppLog.log";
    private String path = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/";
    private File logFile;
    
    protected FileLogger(){
        if(system.startsWith("Windows XP")){
            path = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\";
        }else if(system.startsWith("Windows")){
            path = "D:\\GitHub\\GPSWebApp\\web\\Logged\\uploaded_from_server\\";
        }
        logFile = new File(path, fileName);
    }
    
    public static FileLogger getInstance(){
        if(logger == null){
            logger = new FileLogger();
        }
        return logger;
    }
    
    private void checkLogFileIsCreated(){
        if(!logFile.exists()){
            try {
                //logFile.mkdirs();
                logFile.createNewFile();
            } catch (IOException ex) {
                System.out.println("ERROR: Cannot create log file!!!");
            }
        }
    }
    
    public void createNewLog(String message){
        this.checkNewDay();
        FileWriter writer = null;
        try {
            this.checkLogFileIsCreated();
            writer = new FileWriter(logFile, true);
            
            BufferedWriter buf = new BufferedWriter(writer);
            
            writer.append(new Date().toString() + " >>> " + message + "\n");
            
            buf.close();
        } catch (IOException ex) {
            System.out.println(system);
            System.out.println("ERROR: Cannot write into a log file!!!");
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
               System.out.println("ERROR: Cannot write into a log file!!!");
            }
        }
        
    }
    
    private void checkNewDay() {

        BufferedReader br = null;

        String currentLine = null;
        String lastLine = null;

        try {
            br = new BufferedReader(new FileReader(logFile));

            while((currentLine = br.readLine()) != null){
                lastLine = currentLine;
            }
            
            String stringDate = lastLine.substring(0, lastLine.lastIndexOf(" >>> ") - 5);
            
            Date lastDate = new Date(stringDate);
            Date currentDate = new Date();
            
            if(currentDate.getDay() > lastDate.getDay()){
                this.createNewLog("New Date\n");
            }
            
            
        } catch (Exception ex) {
            Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void resetLogFile(){
        if(logFile.exists()){
            logFile.delete();
            this.checkLogFileIsCreated();
        }
    }
    
}
