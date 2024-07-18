package org.example.coworking.domain.dto.request.user;

import org.example.coworking.domain.model.enums.UserRole;

public record UserRegisterRequestDto(
        String username,
        String password,
        UserRole role
) {
}
