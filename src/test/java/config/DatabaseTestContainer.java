package config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.Getter;
import org.example.coworking.config.DatabaseConnection;
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
    @Getter
    private static DatabaseConnection databaseConnection;

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
            databaseConnection = new DatabaseConnection(jdbcUrl, username, password);
            initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public static void initializeDatabase() throws SQLException {
        try (Connection conn = databaseConnection.getConnection()) {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            String query = "CREATE SCHEMA IF NOT EXISTS liquibase_logs;";
            try (Statement statement = connection.createStatement()){
                statement.execute(query);
            }
            database.setDefaultSchemaName("coworking");
            database.setLiquibaseSchemaName("liquibase_logs");
            Liquibase liquibase = new Liquibase("db/changelog/main-changelog.xml",
                    new ClassLoaderResourceAccessor(), database);
            liquibase.update("");
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
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
