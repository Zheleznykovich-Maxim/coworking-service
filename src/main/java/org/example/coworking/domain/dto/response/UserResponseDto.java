package org.example.coworking.domain.dto.response;

import org.example.coworking.domain.model.enums.UserRole;

public record UserResponseDto(
        int id,
        String username,
        UserRole role,
        String message
) {
}
