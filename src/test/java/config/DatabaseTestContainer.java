package config;

import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTestContainer {

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private static Connection connection;


    public static void startContainer() {
        if (!postgresContainer.isRunning()) {
            postgresContainer.start();
        }
        establishConnection();
    }

    private static void establishConnection() {
        try {
            String jdbcUrl = postgresContainer.getJdbcUrl();
            String username = postgresContainer.getUsername();
            String password = postgresContainer.getPassword();
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public static void initializeDatabase(String createTableSQL) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void stopContainer() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (postgresContainer.isRunning()) {
            postgresContainer.stop();
        }
    }
}
