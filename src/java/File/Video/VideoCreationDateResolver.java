/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package File.Video;

import Parser.Utilities.TimezoneLoader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matej_000
 */
public class VideoCreationDateResolver {
    public VideoCreationDateResolver(){
        
    }
    
    public Date resolveCreationDate(String videoFilePath, double lat, double lon){  
         try {
            ProcessBuilder builder = new ProcessBuilder("ffprobe", videoFilePath);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(line.trim().startsWith("creation")){
                    String stringDate = line.trim().substring(line.trim().indexOf(":") + 2);
                    //System.out.println(stringDate);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = format.parse(stringDate);
                    //System.out.println(date);
                    
                    TimezoneLoader timezoneLoader = new TimezoneLoader();
                    Date newDate = timezoneLoader.correctTimeZone(date, lat, lon);
                    //System.out.println(newDate);
                    
                    return newDate;
                }
            }
        } catch (Exception ex) {
            return null;
        }
         return null;
    }
}
