package org.example.coworking.dto.response;

import org.example.coworking.model.enums.ResourceType;
import java.time.LocalDateTime;

public record BookingResponseDto(
        int id,

        int userId,

        int resourceId,

        LocalDateTime startTime,

        LocalDateTime endTime,

        ResourceType resourceType,

        boolean isAvailable
) {
}
