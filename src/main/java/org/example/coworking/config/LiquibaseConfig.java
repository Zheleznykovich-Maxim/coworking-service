package org.example.coworking.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LiquibaseConfig {
    public static void runMigrations() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/application.properties"));

            Connection connection = DatabaseConfig.getConnection();
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            String query = "CREATE SCHEMA IF NOT EXISTS liquibase_logs;";
            try (Statement statement = connection.createStatement()){
                statement.execute(query);
            }
            database.setDefaultSchemaName("coworking");
            database.setLiquibaseSchemaName("liquibase_logs");
            Liquibase liquibase = new Liquibase(properties.getProperty("liquibase.changelog"), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            connection.close();
            System.out.println("Liquibase migration completed successfully");
        } catch (LiquibaseException e) {
            System.out.println("Liquibase Exception in migration " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException Exception in migration " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
        }
    }
}
