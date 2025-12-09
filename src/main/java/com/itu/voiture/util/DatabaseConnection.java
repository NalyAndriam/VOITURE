package com.itu.voiture.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    private final String url;
    private final String username;
    private final String password;

    public DatabaseConnection(
            @Value("${db.url}") String url,
            @Value("${db.username}") String username,
            @Value("${db.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver PostgreSQL manquant !", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // TA MÉTHODE CLOSE EST GARDÉE ET CORRIGÉE
    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable c : closeables) {
            if (c != null) {
                try {
                    c.close();                    // ← appelle la vraie méthode close()
                } catch (Exception ignored) {      // ← plus de récursion
                }
            }
        }
    }

    // Surcharges pratiques (tu peux les garder)
    public static void close(Connection conn, java.sql.Statement stmt, java.sql.ResultSet rs) {
        close(rs, stmt, conn);
    }

    public static void close(Connection conn, java.sql.Statement stmt) {
        close(stmt, conn);
    }

    public static void close(Connection conn) {
        close((AutoCloseable) conn);
    }
}