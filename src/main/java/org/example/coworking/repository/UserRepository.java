package org.example.coworking.repository;

import lombok.AllArgsConstructor;
import org.example.coworking.config.DatabaseConnection;
import org.example.coworking.mapper.UserMapper;
import org.example.coworking.mapper.UserMapperImpl;
import org.example.coworking.model.User;
import org.example.coworking.repository.query.UserQuery;
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
    private final UserMapper userMapper = new UserMapperImpl();
    private final DatabaseConnection databaseConnection;

    /**
     * Registers a new user in the repository.
     *
     * @param user The user object to register.
     */
    public void registerUser(User user) {
        try (Connection connection = databaseConnection.getConnection()) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(UserQuery.GET_ID_NEXT_USER)) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    user.setId(generatedId);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(UserQuery.ADD_USER)) {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getRole().name());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
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
        try (Connection connection = databaseConnection.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(UserQuery.FIND_USER_BY_USERNAME)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(userMapper.resultSetToUser(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /**
     * Finds a user by their id.
     *
     * @param id The username of the user to find.
     * @return The user object with the specified username, or null if not found.
     */
    public Optional<User> findUserById(int id) {
        try (Connection connection = databaseConnection.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(UserQuery.FIND_USER_BY_ID)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(userMapper.resultSetToUser(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
