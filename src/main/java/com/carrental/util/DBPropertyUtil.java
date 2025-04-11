package com.carrental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class DBPropertyUtil {

    public static Connection getConnection() {
        Connection conn = null;
        try {
        	ResourceBundle rb = ResourceBundle.getBundle("db");
            String url = rb.getString("url");
            String username = rb.getString("username");
            String password = rb.getString("password");
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        return conn;
    }
}
