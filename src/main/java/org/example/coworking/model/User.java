package org.example.coworking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.coworking.model.enums.UserRole;

/**
 * Represents a user in the coworking space system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private static int nextUserId = 1;
    /**
     * The id of the user.
     */
    private int id;
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

    public User(String username, String password, UserRole role) {
        this.id = nextUserId++;
        this.username = username;
        this.password = password;
        this.role = role;
    }

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
