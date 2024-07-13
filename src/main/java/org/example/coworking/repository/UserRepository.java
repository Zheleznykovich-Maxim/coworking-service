package org.example.coworking.repository;

import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.mapper.UserMapper;
import org.example.coworking.domain.model.User;
import org.example.coworking.repository.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Repository class for managing users.
 */
@Repository
public class UserRepository {
    private final UserMapper userMapper;
    private final DatabaseConfig databaseConfig;

    @Autowired
    public UserRepository(UserMapper userMapper, DatabaseConfig databaseConfig) {
        this.userMapper = userMapper;
        this.databaseConfig = databaseConfig;
    }

    public Collection<User> getAllUsers() {
        try (Connection connection =  databaseConfig.getConnection()) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(UserQuery.GET_ALL_USERS)) {
                Collection<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(userMapper.resultSetToUser(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Registers a new user in the repository.
     *
     * @param user The user object to register.
     */
    public void registerUser(User user) {
        try (Connection connection = databaseConfig.getConnection()) {

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

    public void removeUserById(int userId) {
        try (Connection connection = databaseConfig.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(UserQuery.DELETE_USER)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        try (Connection connection = databaseConfig.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(UserQuery.UPDATE_USER)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getRole().name());

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
        try (Connection connection = databaseConfig.getConnection()) {

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
        try (Connection connection = databaseConfig.getConnection()) {

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
