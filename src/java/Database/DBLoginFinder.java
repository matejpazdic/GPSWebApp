/*
 * To change this template, choose Tools | Templates
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

public class DBLoginFinder {
  private Connection connect = null;
  private Statement statement = null;
  private ResultSet resultSet = null;

  public DBLoginFinder() throws Exception {
    try {

      Class.forName("com.mysql.jdbc.Driver").newInstance();
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost:3306/GPSWebApp","root","Www4dm1n#");
      PreparedStatement statement = connect
          .prepareStatement("SELECT * from USERS");

      resultSet = statement.executeQuery();
//      while (resultSet.next()) {
//        String user = resultSet.getString("user_email");
//        String number = resultSet.getString("user_pass");
//        System.out.println("User: " + user);
//        System.out.println("ID: " + number);
//      }
    } catch (Exception e) {
        FileLogger.getInstance().createNewLog("ERROR: Cannot load users from DB in DBLoginFinder!!!");
      throw e;
    } 
//    finally {
//      close();
//    }

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
  
  public boolean isCorrectLogin(String username, String userpass) throws Exception{
      while (resultSet.next()) {
        String email = resultSet.getString("user_email");
        String pass = resultSet.getString("user_pass");
        if(email.equals(username) && pass.equals(userpass)){
            close();
            return true;
        }
        
  }
      close();
      FileLogger.getInstance().createNewLog("ERROR: Cannot log user " + username + " !!!");
      return false;
  }
  
    public boolean isExistingLogin(String username) throws Exception {
        while (resultSet.next()) {
            String email = resultSet.getString("user_email");
            if (email.equals(username)) {
                //close();
                return true;
            }

        }
        close();
        FileLogger.getInstance().createNewLog("ERROR: User " + username + " is non existing user!!!");
        return false;
    }
    
    public boolean isExistingLoginNonLog(String username) throws Exception {
        while (resultSet.next()) {
            String email = resultSet.getString("user_email");
            if (email.equals(username)) {
                //close();
                return true;
            }

        }
        close();
        //FileLogger.getInstance().createNewLog("ERROR: User " + username + " is non existing user!!!");
        return false;
    }

    public int getUserId(String username) throws SQLException {
        while (resultSet.next()) {
            String email = resultSet.getString("user_email");
            if (email.equals(username)) {
                int userID = Integer.parseInt(resultSet.getString("USER_ID"));
                close();
                return userID;
            }

        }
        close();
        FileLogger.getInstance().createNewLog("ERROR: Cannot find userID of user " + username + " !!!");
        return -1;
    }
    
    public boolean isUserStatus(String username) throws Exception {
        boolean condition = true;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connect1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/GPSWebApp", "root", "Www4dm1n#");
            PreparedStatement statement1 = connect1.prepareStatement("SELECT * from USERS where USER_EMAIL='" + username + "'");
            ResultSet resultSet1 = statement1.executeQuery();

            resultSet1.next();
            String userStatus = resultSet1.getString("USER_STATUS");
            connect1.close();
            statement1.close();
            resultSet1.close();
            if(userStatus.equals("USER")){
                condition = true;
                return condition;
            }else{
                condition = false;
                return condition;
            }
        } catch (Exception e) {
            FileLogger.getInstance().createNewLog("ERROR: Cannot load user status from DB in isUserStatus!!!");
            return condition;
        }
    }
    
    public ArrayList<String> getUserInformation(String username) throws Exception {
        ArrayList<String> information = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connect1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/GPSWebApp", "root", "Www4dm1n#");
            PreparedStatement statement1 = connect1.prepareStatement("SELECT * from USERS where USER_EMAIL='" + username + "'");
            ResultSet resultSet1 = statement1.executeQuery();
            resultSet1.next();
            
            information.add(resultSet1.getString("USER_FIRST_NAME"));
            information.add(resultSet1.getString("USER_LAST_NAME"));
            information.add(resultSet1.getString("USER_AGE"));
            information.add(resultSet1.getString("USER_ACTIVITY"));
            information.add(resultSet1.getString("USER_EMAIL"));
            information.add(resultSet1.getString("USER_PASS"));
            information.add(resultSet1.getString("USER_STATUS"));
            
            connect1.close();
            statement1.close();
            resultSet1.close();
            
            return information;
        } catch (Exception e) {
            FileLogger.getInstance().createNewLog("ERROR: Cannot load user status from DB in getUserInformation!!!");
            return information;
        }
    }
    
} 
