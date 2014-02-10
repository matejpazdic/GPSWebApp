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
public class DBTrackEraser {
    
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public DBTrackEraser() throws Exception {
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost:3306/GPSWebApp?useUnicode=true&characterEncoding=UTF-8","root","Www4dm1n#");
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void eraseTrack(int trackID){
        try {
            statement =  connect.createStatement();
            //statement.executeQuery();
            String stat = "DELETE FROM TRACKS where TRACK_ID=" + trackID;
            statement.executeUpdate(stat);
            close();
        } catch (SQLException ex) {
            System.out.println("Nevymazal som z DB!!!");
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