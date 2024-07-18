package org.example.coworking.mapper;

import org.example.coworking.domain.dto.request.user.UserLoginRequestDto;
import org.example.coworking.domain.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.domain.dto.response.UserResponseDto;
import org.example.coworking.domain.model.User;
import org.mapstruct.Mapper;
import java.util.Collection;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userRegisterRequestDtotoUser(UserRegisterRequestDto userRegisterRequestDto);

    User userLoginRequestDtotoUser(UserLoginRequestDto userLoginRequestDto);

    UserResponseDto userToUserResponseDto(User user);

    Collection<UserResponseDto> usersToUserResponseDtos(Collection<User> users);

}
