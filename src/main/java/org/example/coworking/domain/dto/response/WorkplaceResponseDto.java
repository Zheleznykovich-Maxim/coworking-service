package org.example.coworking.domain.dto.response;

public record WorkplaceResponseDto(
        int id,
        String name,
        boolean isAvailable
) {
}
