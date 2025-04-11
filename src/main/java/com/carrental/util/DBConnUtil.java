package com.carrental.util;

import java.sql.Connection;

public class DBConnUtil {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            connection = DBPropertyUtil.getConnection(); 
        }
        return connection;
    }
}
