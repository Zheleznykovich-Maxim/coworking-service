package org.example.coworking.service;

import org.example.coworking.domain.dto.request.user.UserLoginRequestDto;
import org.example.coworking.domain.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.domain.dto.response.UserResponseDto;

import java.util.Collection;

/**
 * Service interface for managing users in the coworking system.
 */
public interface UserService {

    /**
     * Registers a new user based on the provided registration data.
     *
     * @param userRegisterRequestDto the data for the new user registration.
     * @return a {@link UserResponseDto} representing the registered user.
     */
    UserResponseDto register(UserRegisterRequestDto userRegisterRequestDto);

    /**
     * Authenticates a user based on the provided login data.
     *
     * @param userLoginRequestDto the data for the user login.
     * @return a {@link UserResponseDto} representing the authenticated user.
     */
    UserResponseDto login(UserLoginRequestDto userLoginRequestDto);

    /**
     * Retrieves all users.
     *
     * @return a collection of {@link UserResponseDto} representing all users.
     */
    Collection<UserResponseDto> getUsers();

    /**
     * Updates an existing user with the specified ID based on the provided registration data.
     *
     * @param id the ID of the user to be updated.
     * @param userRegisterRequestDto the data for the user update.
     * @return a {@link UserResponseDto} representing the updated user.
     */
    UserResponseDto updateUser(int id, UserRegisterRequestDto userRegisterRequestDto);

    /**
     * Removes a user by their ID.
     *
     * @param id the ID of the user to be removed.
     * @return a {@link UserResponseDto} representing the removed user.
     */
    UserResponseDto removeUserById(int id);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found.
     * @return a {@link UserResponseDto} representing the found user.
     */
    UserResponseDto findUserByName(String username);

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user to be found.
     * @return a {@link UserResponseDto} representing the found user.
     */
    UserResponseDto findUserById(int id);

    /**
     * Checks if a username already exists in the system.
     *
     * @param username the username to be checked.
     * @return {@code true} if the username exists, {@code false} otherwise.
     */
    boolean checkUsernameExists(String username);
}
