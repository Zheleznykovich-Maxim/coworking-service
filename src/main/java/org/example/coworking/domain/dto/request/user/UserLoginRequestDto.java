package org.example.coworking.domain.dto.request.user;

public record UserLoginRequestDto(
        String username,
        String password
) {
}
