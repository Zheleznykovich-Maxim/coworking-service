package org.example.coworking.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionProviderImpl implements DatabaseConnectionProvider{

    private final String url;
    private final String username;
    private final String password;

    public DatabaseConnectionProviderImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
