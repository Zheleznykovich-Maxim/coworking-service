package org.example.coworking.service;

import lombok.AllArgsConstructor;
import org.example.coworking.annotations.Loggable;
import org.example.coworking.exception.EntityNotFoundException;
import org.example.coworking.model.User;
import org.example.coworking.repository.UserRepository;

/**
 * Service class for managing User operations.
 */
@Loggable
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
        return userRepository.findUserByUsername(user.getUsername()).get().getPassword()
                .equals(user.getPassword());
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for.
     * @return the user with the specified username, or null if not found.
     */
    public User findUserByName(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с таким именем не найдено!"));
    }

    /**
     * Finds a user by their id.
     *
     * @param id the username to search for.
     * @return the user with the specified username, or null if not found.
     */
    public User findUserById(int id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с таким именем не найдено!"));
    }

    /**
     * check a user for existence.
     *
     * @param username the username to search for.
     * @return true if user exists, or false if not found.
     */
    public boolean checkUsernameExists(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }
}
