package org.example.coworking.repository;

import lombok.RequiredArgsConstructor;
import org.example.coworking.config.DatabaseConnection;
import org.example.coworking.model.UserAction;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor
public class UserActionRepository {
    private final DatabaseConnection databaseConnection;

    public void save(UserAction userAction) throws SQLException {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS coworking.user_actions (\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    action VARCHAR(255) NOT NULL,\n" +
                    "    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL\n" +
                    ");\n";
            statement.executeUpdate(query);
        }
        String sql = "INSERT INTO coworking.user_actions (action, timestamp) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, userAction.getAction());
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(userAction.getTimestamp()));
            preparedStatement.executeUpdate();
        }
    }
}
