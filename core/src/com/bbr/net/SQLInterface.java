package com.bbr.net;

import com.bbr.menu.Data;

import java.awt.*;
import java.sql.*;

public class SQLInterface {
    public static final String IP = "127.0.0.1";
    public static final String URL = "jdbc:mysql://"+IP+":3306/dbpbb";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    private static Connection getConnection(){
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
    public static void createTables(){
        Connection c = getConnection();
        String query1 = "CREATE TABLE  IF NOT EXISTS `dbpbb`.`tblaccount`(`accid` INT NOT NULL AUTO_INCREMENT , `username` VARCHAR(30) NOT NULL , `color1` INT NOT NULL , `color2` INT NOT NULL , `color3` INT NOT NULL , PRIMARY KEY (`accid`), UNIQUE (`username`));";
        String query2 = "CREATE TABLE  IF NOT EXISTS `dbpbb`.`tblstats`  (`statsid` INT NOT NULL AUTO_INCREMENT , `wins` INT NOT NULL DEFAULT '0' , `kos` INT NOT NULL DEFAULT '0' , `outs` INT NOT NULL DEFAULT '0' , `accid` INT NOT NULL , PRIMARY KEY (`statsid`), FOREIGN KEY (`accid`) REFERENCES tblaccount(`accid`) ON DELETE CASCADE)";
        try {
            Statement statement = c.createStatement();
            statement.execute(query1);
            statement.execute(query2);
            System.out.println("Tables created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void loginSignUp(String username){
        if(!verifyLogin(username)){
            System.out.println("Signing UP");
            signUp(username);
            verifyLogin(username);
            System.out.println("logged in as "+Data.account.color1);
        }
    }
    public static void signUp(String username){
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO tblaccount (username, color1, color2, color3) VALUES (?,?,?,?)"
             );
             PreparedStatement statement2 = c.prepareStatement(
                     "INSERT INTO tblstats (wins, kos, outs, accid) VALUES (0,0,0,?)"
             );
             Statement statement3 = c.createStatement()
        ) {
            statement.setString(1, username);
            statement.setInt(2,  255);
            statement.setInt(3,  255);
            statement.setInt(4,  255);
            int rowsInserted = statement.executeUpdate();
            System.out.println("ROWS INSERTED: "+rowsInserted);
            ResultSet res = statement3.executeQuery("SELECT * FROM tblaccount WHERE username='"+username+"'");
            if(res.next()){
                    statement2.setInt(1,  res.getInt("accid"));
            }
            statement2.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static boolean verifyLogin(String username) {
        try(
                Connection c = getConnection();
                Statement statement = c.createStatement();
                Statement statement2 = c.createStatement();
        ){
            String query = "SELECT * FROM tblaccount WHERE username='"+username+"'";
            ResultSet res = statement.executeQuery(query);
            if(res.next()){
                Data.account.id = res.getInt("accid");
                Data.account.color1 = res.getInt("color1");
                Data.account.color2 = res.getInt("color2");
                Data.account.color3 = res.getInt("color3");
                Data.account.username = res.getString("username");
            } else return false;
            query = "SELECT * FROM tblstats WHERE accid='"+Data.account.id+"'";
            ResultSet res2 = statement2.executeQuery(query);
            if(res2.next()){
                Data.account.wins = res2.getInt("wins");
                Data.account.kos = res2.getInt("kos");
                Data.account.outs = res2.getInt("outs");
                Data.account.statsid = res2.getInt("statsid");
            } else return false;
            return true;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static void updateStats(int statsid,int wins, int kos,int outs) {
        try(
                Connection c = getConnection();
                PreparedStatement statement = c.prepareStatement("UPDATE tblstats SET wins=?, kos=?, outs=? WHERE statsid=?");
        ){
            statement.setInt(1,wins);
            statement.setInt(2,kos);
            statement.setInt(3,outs);
            statement.setInt(4,statsid);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows Updated: "+rowsUpdated);
            Data.account.kos = kos;
            Data.account.wins = wins;
            Data.account.outs = outs;
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static Account readAccount(int accid){
        Account acc = new Account();
        try(
                Connection c = getConnection();
                Statement statement = c.createStatement();
                Statement statement2 = c.createStatement();
        ){
            String query = "SELECT * FROM tblaccount WHERE id="+accid;
            ResultSet res = statement.executeQuery(query);
            if(res.next()){
                acc.id = accid;
                acc.username = res.getString("username");
                acc.color1 = res.getInt("color1");
                acc.color2 = res.getInt("color2");
                acc.color3 = res.getInt("color3");
            } else return null;
            res = null;
            String query2 = "SELECT * FROM tblstats WHERE accid="+accid;
            res = statement.executeQuery(query);
            if(res.next()){
                acc.id = accid;
                acc.kos = res.getInt("kos");
                acc.wins = res.getInt("wins");
                acc.outs = res.getInt("outs");
            } else return null;

            return acc;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean deleteAccount(int accid){
        try(
                Connection c = getConnection();
                PreparedStatement statement = c.prepareStatement("DELETE FROM tblaccount WHERE accid=?");
        ){
            statement.setInt(1,accid);
            int rowsDeleted = statement.executeUpdate();
            Data.account = new Account();
            return (rowsDeleted>0);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
