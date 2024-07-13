package org.example.coworking.service;

import org.example.coworking.domain.dto.request.user.UserLoginRequestDto;
import org.example.coworking.domain.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.domain.dto.response.UserResponseDto;

import java.util.Collection;

public interface UserService {

    UserResponseDto register(UserRegisterRequestDto userRegisterRequestDto);

    UserResponseDto login(UserLoginRequestDto userLoginRequestDto);

    Collection<UserResponseDto> getUsers();

    UserResponseDto updateUser(int id, UserRegisterRequestDto userRegisterRequestDto);

    UserResponseDto removeUserById(int id);

    UserResponseDto findUserByName(String username);

    UserResponseDto findUserById(int id);

    boolean checkUsernameExists(String username);
}
