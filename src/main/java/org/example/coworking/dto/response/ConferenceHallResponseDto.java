package org.example.coworking.dto.response;

public record ConferenceHallResponseDto (
        int id,
        String name,
        boolean isAvailable
) {
}
