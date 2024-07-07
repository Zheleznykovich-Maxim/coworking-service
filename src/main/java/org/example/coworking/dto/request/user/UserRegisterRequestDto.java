package org.example.coworking.dto.request.user;

import org.example.coworking.model.enums.UserRole;

public record UserRegisterRequestDto(
        String username,
        String password,
        UserRole role
) {
}
