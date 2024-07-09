package org.example.coworking.dto.response;

public record WorkplaceResponseDto(
        int id,
        String name,
        boolean isAvailable
) {
}
