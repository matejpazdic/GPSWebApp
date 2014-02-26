/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Logger.FileLogger;
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
public class DBLoginUpdater {
    
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    public DBLoginUpdater(){
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/GPSWebApp?useUnicode=true&characterEncoding=UTF-8", "root", "Www4dm1n#");

        } catch (Exception e) {
            FileLogger.getInstance().createNewLog("ERROR: Cannot create DB connection in DBLoginUpdater!!!");
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
    
    public int updateUserData(String currentEmail, String newEmail, String firstName, String lastName, String activity,String oldPassword, String newPassword, int age){
        try {
            DBLoginFinder finder = new DBLoginFinder();
            boolean existing = finder.isExistingLoginNonLog(newEmail);
            DBLoginFinder finder1 = new DBLoginFinder();
            ArrayList<String> info = finder1.getUserInformation(currentEmail);
            
            if (oldPassword.equals(info.get(5))) {
                if (!existing || currentEmail.equals(newEmail)) {
                    Statement statement = connect.createStatement();
                    statement.executeUpdate("UPDATE USERS set USER_EMAIL='" + newEmail + "',USER_FIRST_NAME='" + firstName + "',USER_LAST_NAME='" + lastName + "',USER_ACTIVITY='" + activity + "',USER_AGE=" + age + ",USER_PASS='" + newPassword + "' WHERE USER_EMAIL='" + currentEmail + "'");
                    FileLogger.getInstance().createNewLog("Successfuly updated user data for old user " + currentEmail + " to new email " + newEmail + ".");
                    return 0;
                } else {
                    FileLogger.getInstance().createNewLog("ERROR: User " + info.get(4) +  " has entered existing new email!!! Cannot update user data!!!");
                    return 1;
                }
            }else{
                FileLogger.getInstance().createNewLog("ERROR: User " + info.get(4) +  " has entered wrong password!!! Cannot update user data!!!");
                return 2;
            }
            
            
        } catch (Exception ex) {
            FileLogger.getInstance().createNewLog("ERROR: Cannot connect to DB in DBLoginUpdater!!!");
            this.close();
            return -1;
        }
    }
    
}
