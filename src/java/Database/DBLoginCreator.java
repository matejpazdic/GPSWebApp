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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matej_000
 */
public class DBLoginCreator {
    
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public DBLoginCreator() throws Exception {
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
            FileLogger.getInstance().createNewLog("ERROR: Cannot connect to Database in DBLoginCreator!!!");
            throw e;
        }
//    finally {
//      close();
//    }

    }
    
    public void createNewLogin(String email, String password, String userToken){
        try {
            statement =  connect.createStatement();
            //statement.executeQuery();
            statement.executeUpdate("INSERT INTO USERS (USER_EMAIL, USER_PASS, USER_TOKEN) VALUES ('"+ email +"' ,'" + password + "' ,'" + userToken + "')");
            
            FileLogger.getInstance().createNewLog("User " + email + " was successfuly created!");
            
            close();
        } catch (SQLException ex) {
            System.out.println("Nezapisal som do DB!!!");
            FileLogger.getInstance().createNewLog("ERROR: User " + email + " was NOT successfuly created!");
            close();
        }
    }
    
    public void createNewLogin(String email, String firstName, String lastName, String age, String activity, String password, String userToken){
        try {
            if(age.isEmpty()){
                age = "-1";
            }
            statement =  connect.createStatement();
            //statement.executeQuery();
            statement.executeUpdate("INSERT INTO USERS (USER_EMAIL, USER_FIRST_NAME, USER_LAST_NAME , USER_AGE, USER_ACTIVITY, USER_PASS, USER_TOKEN) VALUES ('"+ email +"' ,'" + firstName + "' ,'"+ lastName +"' ," + age + " ,'"+ activity +"' ,'" + password + "' ,'" + userToken + "')");
            
            FileLogger.getInstance().createNewLog("User " + email + " was successfuly created!");
            
            close();
        } catch (SQLException ex) {
            System.out.println("Nezapisal som do DB!!!");
            FileLogger.getInstance().createNewLog("ERROR: User " + email + " was NOT successfuly created!");
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
