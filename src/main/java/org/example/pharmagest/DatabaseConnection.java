package org.example.pharmagest;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/pharmagest";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Pazerty11";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
