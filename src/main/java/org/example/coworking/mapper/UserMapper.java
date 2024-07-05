package org.example.coworking.mapper;

import org.example.coworking.model.User;
import org.example.coworking.model.enums.UserRole;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User resultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(UserRole.valueOf(resultSet.getString("role")));
        return user;
    }
}
