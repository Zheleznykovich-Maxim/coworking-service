package org.example.coworking.repository;

import org.example.coworking.domain.model.UserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Repository class for managing user actions in the database.
 */
@Repository
public class UserActionRepository {

    private final DataSource dataSource;

    @Autowired
    public UserActionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Saves a user action to the database.
     *
     * @param userAction the user action to be saved
     * @throws SQLException if a database access error occurs
     */
    public void saveUserAction(UserAction userAction) throws SQLException {
        String sql = "INSERT INTO coworking.user_actions (action, timestamp) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, userAction.getAction());
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(userAction.getTimestamp()));
            preparedStatement.executeUpdate();
        }
    }
}
