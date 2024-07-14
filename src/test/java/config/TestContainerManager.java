package config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.Getter;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class
TestContainerManager implements Startable {

    @Getter
    private static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");
    private static Connection connection;

    @Override
    public void start() {
        postgresContainer.start();
        establishConnection();
    }

    @Override
    public void stop() {
        if (postgresContainer != null) {
            postgresContainer.stop();
        }
    }

    public static void establishConnection() {
        try {
            String jdbcUrl = postgresContainer.getJdbcUrl();
            String username = postgresContainer.getUsername();
            String password = postgresContainer.getPassword();
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            initializeDatabase();
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public static void initializeDatabase() throws SQLException, LiquibaseException {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            String query = "CREATE SCHEMA IF NOT EXISTS liquibase_logs;";
            try (Statement statement = connection.createStatement()){
                statement.execute(query);
            }
            String query2 = "CREATE SCHEMA IF NOT EXISTS coworking;";
            try (Statement statement = connection.createStatement()){
                statement.execute(query2);
            }
            database.setDefaultSchemaName("coworking");
            database.setLiquibaseSchemaName("liquibase_logs");
            Liquibase liquibase = new Liquibase("db/changelog/main-changelog.xml",
                    new ClassLoaderResourceAccessor(), database);
            liquibase.update("");
    }
}
