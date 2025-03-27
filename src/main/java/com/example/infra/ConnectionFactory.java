package com.example.infra;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(".env")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar arquivo .env", e);
        }

        URL = properties.getProperty("DB_URL");
        USER = properties.getProperty("DB_USER");
        PASSWORD = properties.getProperty("DB_PASSWORD");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}