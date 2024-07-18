package org.example.coworking.domain.dto.response;

public record ConferenceHallResponseDto (
        int id,
        String name,
        boolean isAvailable
) {
}
