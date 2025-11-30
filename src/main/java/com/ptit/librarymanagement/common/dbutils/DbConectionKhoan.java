package com.ptit.librarymanagement.common.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConectionKhoan {
    private static final Connection CONNECTION;
    private static String userName = "root";
    private static String passWord = "123456";
    private static String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3309/library_management_java?sslMode=REQUIRED";
    private DbConectionKhoan() {}

    static {
        try {
            Class.forName(driverClassName);
            CONNECTION = DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnectionKhoan() {
        return CONNECTION;
    }

}
