package com.mycompany.atmbancario.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:derby://localhost:1527/ATMBancoDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123";  

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}