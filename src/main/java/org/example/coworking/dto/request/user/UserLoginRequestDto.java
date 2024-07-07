package org.example.coworking.dto.request.user;

public record UserLoginRequestDto(
        String username,
        String password
) {
}
