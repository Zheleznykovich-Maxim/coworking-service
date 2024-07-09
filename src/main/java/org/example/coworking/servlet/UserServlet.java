package org.example.coworking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.coworking.annotations.Loggable;
import org.example.coworking.dto.request.user.UserLoginRequestDto;
import org.example.coworking.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.dto.response.UserResponseDto;
import org.example.coworking.factory.UserServiceFactory;
import org.example.coworking.mapper.UserMapper;
import org.example.coworking.mapper.UserMapperImpl;
import org.example.coworking.model.User;
import org.example.coworking.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Servlet for handling user registration and login.
 */
@Loggable
@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    /**
     * Initializes the servlet with necessary services and mappers.
     *
     * @throws IOException if an I/O error occurs
     */
    public UserServlet() throws IOException {
        this.userMapper = new UserMapperImpl();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.userService = new UserServiceFactory().create();
    }

    /**
     * Handles POST requests for user operations (register or login).
     *
     * @param req  HTTP servlet request
     * @param resp HTTP servlet response
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            if (pathInfo.equals("/register")) {
                handleRegister(req, resp);
            } else if (pathInfo.equals("/login")) {
                handleLogin(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Url not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        UserRegisterRequestDto userRegisterRequestDto = objectMapper.readValue(reader, UserRegisterRequestDto.class);
        User user = userMapper.userRegisterRequestDtotoUser(userRegisterRequestDto);
        if (userService.checkUsernameExists(user.getUsername()))
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists");
        else {
            userService.register(user);
        }
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user, "Регистрация прошла успешно!");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getOutputStream(), userResponseDto);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        UserLoginRequestDto userLoginRequestDto = objectMapper.readValue(reader, UserLoginRequestDto.class);
        User user = userMapper.userLoginRequestDtotoUser(userLoginRequestDto);
        boolean success = userService.login(user);
        if (success) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            User foundUser = userService.findUserByName(user.getUsername());
            UserResponseDto userResponseDto = userMapper.userToUserResponseDto(foundUser, "Авторизация прошла успешно!");
            objectMapper.writeValue(resp.getOutputStream(), userResponseDto);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
        }
    }
}
