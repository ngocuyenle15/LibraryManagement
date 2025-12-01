package com.ptit.librarymanagement.common.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// singleton pattern
public final class DbConnection {
    private static final Connection CONNECTION;
    private static String userName = System.getProperty("application.database.username");
    private static String password = System.getProperty("application.database.password");
    private static String driverClassName = System.getProperty("application.database.driver-class-name");
    private static String url = System.getProperty("application.database.url");
    private DbConnection() {}
    static {
        try {
            Class.forName(driverClassName);
            CONNECTION = DriverManager.getConnection(url, userName, password);
            System.out.println("Kết nối thàng công đến MySQL server");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection () {
        return DbConnection.CONNECTION;
    }

    public static void closeConnection () {
        try {
            CONNECTION.close();
            System.out.printf("Đã ngắt kết nối đến MySQL Server");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
