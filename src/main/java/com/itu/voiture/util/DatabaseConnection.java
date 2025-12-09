package com.itu.voiture.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

    private static final String URL      = "jdbc:postgresql://localhost:5432/voiture";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "itu16";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
             
            }
        }
    }
}