/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author matej_000
 */
public class DBTrackCreator {
    
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String system = System.getProperty("os.name");

    public DBTrackCreator() throws Exception {
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost:3306/GPSWebApp","root","Www4dm1n#");
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void createNewTrack(String trackName, String trackDescr, String trackActivity, String trackPath, int userID){
        try {
            statement =  connect.createStatement();
            //statement.executeQuery();
            String stat = "INSERT INTO TRACKS (TRACK_NAME, TRACK_DESCRIPTION, TRACK_ACTIVITY, TRACK_FILE, TRACK_USER_ID) VALUES ('"+ trackName +"' , '"+ trackDescr + "' , '" + trackActivity + "' , '" + trackPath +"' ," + userID + ")";
            if (system.startsWith("Windows")) {
                stat = stat.replaceAll("\\\\", "/");
            }
            //System.out.println(stat);
            statement.executeUpdate(stat);
            close();
        } catch (SQLException ex) {
            System.out.println("Nezapisal som do DB!!!");
            close();
        }
    }
    
    private void close() {
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
}
