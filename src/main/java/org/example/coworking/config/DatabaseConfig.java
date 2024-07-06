package org.example.coworking.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    public static DatabaseConnection getDatabaseConnection() throws IOException {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                throw new IOException("Sorry, unable to find application.properties");
            }
            properties.load(input);

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            return new DatabaseConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IOException("Failed to connect to database", e);
        }
    }
}
