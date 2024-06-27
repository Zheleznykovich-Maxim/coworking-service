package org.example.coworking.repository;

import lombok.AllArgsConstructor;
import org.example.coworking.model.User;

import java.util.Map;

/**
 * Repository class for managing users.
 */
@AllArgsConstructor
public class UserRepository {
    private final Map<String, User> userMap;

    /**
     * Registers a new user in the repository.
     *
     * @param user The user object to register.
     */
    public void registerUser(User user) {
        userMap.put(user.getUsername(), user);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return The user object with the specified username, or null if not found.
     */
    public User findUserByUsername(String username) {
        return userMap.get(username);
    }
}
