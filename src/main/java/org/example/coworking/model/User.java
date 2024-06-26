package org.example.coworking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.coworking.model.enums.UserRole;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private UserRole role;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
