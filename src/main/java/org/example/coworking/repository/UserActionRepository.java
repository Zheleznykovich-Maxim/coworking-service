package org.example.coworking.repository;

import lombok.RequiredArgsConstructor;
import org.example.coworking.config.DatabaseConnection;
import org.example.coworking.model.UserAction;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Repository class for managing user actions in the database.
 */
@RequiredArgsConstructor
public class UserActionRepository {
    private final DatabaseConnection databaseConnection;

    /**
     * Saves a user action to the database.
     *
     * @param userAction the user action to be saved
     * @throws SQLException if a database access error occurs
     */
    public void saveUserAction(UserAction userAction) throws SQLException {
        String sql = "INSERT INTO coworking.user_actions (action, timestamp) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, userAction.getAction());
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(userAction.getTimestamp()));
            preparedStatement.executeUpdate();
        }
    }
}
