package com.ptit.librarymanagement.common.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;


// singleton pattern
public final class DbConnection {
    private static final Connection CONNECTION;
    private static String userName = "root";
    private static String password = "123456";
    private static String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/library_management";
//    private static String url = "jdbc:mysql://localhost:3306/dbk";
    private DbConnection() {}
    static {
        try {
            Class.forName(driverClassName);
            CONNECTION = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection () {
        return DbConnection.CONNECTION;
    }

}
