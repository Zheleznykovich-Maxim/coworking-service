package org.example.coworking.service;

import lombok.AllArgsConstructor;
import org.example.coworking.model.User;
import org.example.coworking.repository.UserRepository;

/**
 * Service class for managing User operations.
 */
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Registers a new user.
     *
     * @param user the user to register.
     */
    public void register(User user) {
        userRepository.registerUser(user);
    }

    /**
     * Logs in a user by checking if the credentials match.
     *
     * @param user the user attempting to log in.
     * @return true if the credentials match, otherwise false.
     */
    public boolean login(User user) {
        return userRepository.findUserByUsername(user.getUsername()).getPassword()
                .equals(user.getPassword());
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for.
     * @return the user with the specified username, or null if not found.
     */
    public User findUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }
}
