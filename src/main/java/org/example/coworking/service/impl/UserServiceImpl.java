package org.example.coworking.service.impl;

import org.example.coworking.annotations.Auditable;
import org.example.coworking.domain.dto.request.user.UserLoginRequestDto;
import org.example.coworking.domain.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.domain.dto.response.UserResponseDto;
import org.example.coworking.domain.model.User;
import org.example.coworking.exception.EntityNotFoundException;
import org.example.coworking.mapper.UserMapper;
import org.example.coworking.repository.UserRepository;
import org.example.coworking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Auditable(action = "Регистрация пользователя")
    public UserResponseDto register(UserRegisterRequestDto userRegisterRequestDto) {
        User user = userMapper.userRegisterRequestDtotoUser(userRegisterRequestDto);
        if (checkUsernameExists(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        } else {
            userRepository.registerUser(user);
        }
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    @Auditable(action = "Авторизация пользователя")
    public UserResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        User user = userMapper.userLoginRequestDtotoUser(userLoginRequestDto);
        User foundUser = userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("user", user.getId()));
        boolean success = userRepository.findUserByUsername(user.getUsername()).get().getPassword()
                .equals(foundUser.getPassword());
        if (!success) {
            throw new RuntimeException("Incorrect username or password");
        }
        return userMapper.userToUserResponseDto(foundUser);
    }

    @Override
    public Collection<UserResponseDto> getUsers() {
        Collection<User> users = userRepository.getAllUsers();
        return userMapper.usersToUserResponseDtos(users);
    }

    @Override
    public UserResponseDto updateUser(int id, UserRegisterRequestDto userRegisterRequestDto) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("user", id));
        User updatedUser = userMapper.userRegisterRequestDtotoUser(userRegisterRequestDto);
        updatedUser.setId(user.getId());
        userRepository.updateUser(updatedUser);
        return userMapper.userToUserResponseDto(updatedUser);
    }

    @Override
    public UserResponseDto removeUserById(int id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("user", id));
        userRepository.removeUserById(id);
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto findUserByName(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("The user with this username not found"));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto findUserById(int id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("user", id));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }
}
