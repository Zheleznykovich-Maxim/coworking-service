package org.example.coworking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.coworking.model.enums.UserRole;

/**
 * Represents a user in the coworking space system.
 */
@Data
@AllArgsConstructor
public class User {
    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The role of the user.
     */
    private UserRole role;

    /**
     * Returns a string representation of the user.
     *
     * @return A string representing the user, excluding the password.
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
