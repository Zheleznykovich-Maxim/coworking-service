package org.example.coworking.mapper;

import org.example.coworking.dto.request.user.UserLoginRequestDto;
import org.example.coworking.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.dto.response.UserResponseDto;
import org.example.coworking.model.User;
import org.example.coworking.model.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Mapper
public interface UserMapper {
    User userRegisterRequestDtotoUser(UserRegisterRequestDto userRegisterRequestDto);
    User userLoginRequestDtotoUser(UserLoginRequestDto userLoginRequestDto);
    UserResponseDto userToUserResponseDto(User user, String message);
    Collection<UserResponseDto> usersToUserResponseDtos(Collection<User> users);

    @Named("resultSetToUser")
    default User resultSetToUser(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(UserRole.valueOf(resultSet.getString("role")));
        return user;
    }
}
