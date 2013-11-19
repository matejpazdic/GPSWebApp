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
      return false;
  }
  
    public boolean isExistingLogin(String username) throws Exception {
        while (resultSet.next()) {
            String email = resultSet.getString("user_email");
            if (email.equals(username)) {
                close();
                return true;
            }

        }
        close();
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
        return -1;
    }

} 
