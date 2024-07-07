package org.example.coworking.dto.response;

import org.example.coworking.model.enums.UserRole;

public record UserResponseDto(
        int id,
        String username,
        UserRole role,
        String message
) {
}
