package ir.maktabsharif.event.util;

import ir.maktabsharif.event.exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/hw17";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "2117";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException e) {
            throw new DatabaseConnectionException("Connection to Database Failed!" + e.getMessage());
        }
    }
}
