package org.example.coworking.controller;

import org.example.coworking.domain.dto.request.user.UserLoginRequestDto;
import org.example.coworking.domain.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.domain.dto.response.UserResponseDto;
import org.example.coworking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserResponseDto>> getUsers() {
        Collection<UserResponseDto> userResponseDtos = this.userService.getUsers();
        return ResponseEntity.ok(userResponseDtos);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") int id) {
        UserResponseDto workplaceResponseDto = userService.findUserById(id);
        return ResponseEntity.ok(workplaceResponseDto);
    }

    @GetMapping(value = "/username", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> getUserByUsername(@RequestParam("username") String username) {
        UserResponseDto workplaceResponseDto = userService.findUserByName(username);
        return ResponseEntity.ok(workplaceResponseDto);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        UserResponseDto userResponseDto = userService.register(userRegisterRequestDto);
        return ResponseEntity.status(201).body(userResponseDto);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        UserResponseDto userResponseDto = userService.login(userLoginRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") int id,
                                                                @RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        UserResponseDto userResponseDto = userService.updateUser(id,userRegisterRequestDto);
        return ResponseEntity.ok(userResponseDto);

    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable("id") int id) {
        UserResponseDto userResponseDto = userService.removeUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }
}
