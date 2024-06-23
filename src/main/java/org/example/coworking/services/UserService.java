package org.example.coworking.services;

import org.example.coworking.enums.UserRole;
import org.example.coworking.models.User;

import java.util.ArrayList;
import java.util.Objects;

public class UserService {
    private ArrayList<User> users = new ArrayList<>();

    public boolean register(String username, String password, UserRole userRole) {
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return false;
            }
        }
        users.add(new User(username, password, userRole));
        return true;
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public User findUserByName(String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        return null;
    }
}
