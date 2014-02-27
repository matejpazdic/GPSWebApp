/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Logger.FileLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matej_000
 */
public class DBLoginEraser {
    
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    public DBLoginEraser() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/GPSWebApp?useUnicode=true&characterEncoding=UTF-8", "root", "Www4dm1n#");
        } catch (Exception ex) {
            FileLogger.getInstance().createNewLog("ERROR: Cannot connect to DB server in DBLoginEraser!!!");
        }
    }
    
    public boolean eraseUser(int userID){
        try {
            statement =  connect.createStatement();
            //statement.executeQuery();
            String stat = "DELETE FROM USERS where USER_ID=" + userID;
            statement.executeUpdate(stat);
            FileLogger.getInstance().createNewLog("User with userID " + userID + " was successfuly deleted from DB.");
            close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Nevymazal som z DB!!!");
            FileLogger.getInstance().createNewLog("ERROR: Cannot delete user with userID " + userID + " from DB!!!");
            close();
            return false;
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
