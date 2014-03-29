/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbfindskuska;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matej_000
 */
public class DBFinder {
    
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    public DBFinder(){
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/GPSWebApp?useUnicode=true&characterEncoding=UTF-8","root","Www4dm1n#");
        } catch (SQLException ex) {
            Logger.getLogger(DBFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Integer> findStringAll(String find){
        ArrayList<Integer> results = new ArrayList<Integer>();
        try {  
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS FULL JOIN USERS ON USER_ID=TRACK_USER_ID where TRACK_ACCESS='Public' AND (USER_EMAIL like '%" + find + "%' OR TRACK_NAME like '%" + find + "%' OR TRACK_DESCRIPTION like '%" + find + "%' OR TRACK_ACTIVITY like '%" + find + "%' OR TRACK_STARTDATE like '%" + find + "%' OR TRACK_ENDDATE like '%" + find + "%' OR TRACK_START_ADDRESS like '%" + find + "%' OR TRACK_END_ADDRESS like '%" + find + "%')");
            resultSet = statement.executeQuery();
            System.out.println("SELECT * from TRACKS where TRACK_ACCESS='Public' AND (TRACK_NAME like '%" + find + "%' OR TRACK_DESCRIPTION like '%" + find + "%' OR TRACK_ACTIVITY like '%" + find + "%' OR TRACK_START_ADDRESS like '%" + find + "%' OR TRACK_END_ADDRESS like '%" + find + "%')");
            while(resultSet.next()){
                String str = resultSet.getString("TRACK_ID");
                int i = Integer.parseInt(str);
                results.add(i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    
    @Deprecated
    public ArrayList<String> findStringOnlyUsers(String find, int userID){
        ArrayList<String> results = new ArrayList<String>();
        try {  
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS where TRACK_USER_ID=" + userID + " AND (TRACK_NAME like '%" + find + "%' OR TRACK_DESCRIPTION like '%" + find + "%' OR TRACK_ACTIVITY like '%" + find + "%' OR TRACK_START_ADDRESS like '%" + find + "%' OR TRACK_END_ADDRESS like '%" + find + "%')");
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                String str = resultSet.getString("TRACK_NAME");
                results.add(str);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    
    @Deprecated
    public ArrayList<String> findStringInParsed(String find){
        ArrayList<String> results = new ArrayList<String>();
        try {  
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS FULL JOIN USERS ON USER_ID=TRACK_USER_ID where TRACK_CREATION_TYPE='Parsed' AND TRACK_ACCESS='Public' AND (USER_EMAIL like '%" + find + "%' OR TRACK_NAME like '%" + find + "%' OR TRACK_DESCRIPTION like '%" + find + "%' OR TRACK_ACTIVITY like '%" + find + "%' OR TRACK_START_ADDRESS like '%" + find + "%' OR TRACK_END_ADDRESS like '%" + find + "%')");
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                String str = resultSet.getString("TRACK_NAME");
                results.add(str);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    
    @Deprecated
    public ArrayList<String> findStringInDrawed(String find){
        ArrayList<String> results = new ArrayList<String>();
        try {  
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS FULL JOIN USERS ON USER_ID=TRACK_USER_ID where TRACK_CREATION_TYPE='Drawed' AND TRACK_ACCESS='Public' AND (USER_EMAIL like '%" + find + "%' OR TRACK_NAME like '%" + find + "%' OR TRACK_DESCRIPTION like '%" + find + "%' OR TRACK_ACTIVITY like '%" + find + "%' OR TRACK_START_ADDRESS like '%" + find + "%' OR TRACK_END_ADDRESS like '%" + find + "%')");
            resultSet = statement.executeQuery();
            //System.out.println("SELECT * from TRACKS where TRACK_CREATION_TYPE='Drawed' AND (TRACK_NAME like '%" + find + "%' OR TRACK_DESCRIPTION like '%" + find + "%' OR TRACK_ACTIVITY like '%" + find + "%' OR TRACK_START_ADDRESS like '%" + find + "%' OR TRACK_END_ADDRESS like '%" + find + "%')");
            while(resultSet.next()){
                String str = resultSet.getString("TRACK_NAME");
                results.add(str);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    
     public ArrayList<Integer> findNewNTracks(int rowCount){
        ArrayList<Integer> results = new ArrayList<Integer>();
        try {  
            PreparedStatement statement = connect.prepareStatement("SELECT * from TRACKS ORDER BY TRACK_DATE_UPDATED DESC LIMIT " + rowCount);
            resultSet = statement.executeQuery();
            //System.out.println("SELECT * from TRACKS where TRACK_CREATION_TYPE='Drawed' AND (TRACK_NAME like '%" + find + "%' OR TRACK_DESCRIPTION like '%" + find + "%' OR TRACK_ACTIVITY like '%" + find + "%' OR TRACK_START_ADDRESS like '%" + find + "%' OR TRACK_END_ADDRESS like '%" + find + "%')");
            while(resultSet.next()){
                String str = resultSet.getString("TRACK_ID");
                int id = Integer.parseInt(str);
                results.add(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    
}
