/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matej_000
 */
public class DBTrackFinder {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    
    public DBTrackFinder() throws Exception {
    try {

      Class.forName("com.mysql.jdbc.Driver").newInstance();
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost:3306/GPSWebApp","root","Www4dm1n#");
      
//      while (resultSet.next()) {
//        String user = resultSet.getString("user_email");
//        String number = resultSet.getString("user_pass");
//        System.out.println("User: " + user);
//        System.out.println("ID: " + number);
//      }
    } catch (Exception e) {
      throw e;
    } 
//    finally {
//      close();
//    }

  }

  public void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }
  
  public ArrayList getUserTracks(int userID){
      ArrayList<String> tracks = new ArrayList<String>();
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_USER_ID = " + userID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                String str = resultSet.getString("TRACK_NAME");
                System.out.println("TRACKFINDER: "+str);
                tracks.add(str);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD1!!!");
        }
        
        //close();
        return tracks;
  }
  
  public ArrayList getUserTracksFiles(int userID){
      ArrayList<String> trackFiles = new ArrayList<String>();
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_USER_ID = " + userID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                String str = resultSet.getString("TRACK_FILE");
                trackFiles.add(str);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        
        //close();
        return trackFiles;
  }
  
  public ArrayList getTracksIDs(int userID){
      ArrayList<Integer> trackFiles = new ArrayList<Integer>();
        try {
            System.out.println(userID);
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_USER_ID = " + userID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                String str = resultSet.getString("TRACK_ID");
                trackFiles.add(Integer.parseInt(str));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        
        //close();
        return trackFiles;
  }
  
  public String getTrackFilePath(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_FILE");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
  
  public String getTrackFileName(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_NAME");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
  
  public String getTrackDescription(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_DESCRIPTION");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
  
  public String getTrackActivity(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_ACTIVITY");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
  
  public String getTrackStartDate(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_STARTDATE");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
  
  public String getTrackEndDate(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_ENDDATE");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
  
   public String getUploadedDate(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_DATE_CREATED");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
   
    public String getChangeDate(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_DATE_UPDATED");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
    
    public String getStartAddress(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_START_ADDRESS");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
    
    public String getEndAddress(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_END_ADDRESS");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }
    
    public String getAccess(int trackID){
      String str = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_ID = " + trackID);
            resultSet = statement.executeQuery();
            resultSet.next();
            str = resultSet.getString("TRACK_ACCESS");
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot read table TRACKS from BD!!!");
        }
        return str;
  }

} 
