package org.example.coworking.config;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Encapsulates the details of database connection creation.
 */
@AllArgsConstructor
public class DatabaseConnection {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    /**
     * Establishes and returns a connection to the database.
     *
     * @return a {@link Connection} object that represents the connection to the database.
     * @throws SQLException if a database access error occurs or the url is {@code null}.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
