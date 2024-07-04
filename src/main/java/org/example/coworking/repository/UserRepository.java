package org.example.coworking.repository;

import lombok.AllArgsConstructor;
import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.mapper.UserMapper;
import org.example.coworking.model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Repository class for managing users.
 */
@AllArgsConstructor
public class UserRepository {

    /**
     * Registers a new user in the repository.
     *
     * @param user The user object to register.
     */
    public void registerUser(User user) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String getIdQuery = "SELECT nextval('coworking.user_seq')";
            try (Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery(getIdQuery);
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    user.setId(generatedId);
                }
            }
            String insertQuery = "INSERT INTO coworking.users " +
                    "(id, username, password, role)" +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getRole().name());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return The user object with the specified username, or null if not found.
     */
    public Optional<User> findUserByUsername(String username) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM coworking.users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(UserMapper.resultSetToUser(resultSet));
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
