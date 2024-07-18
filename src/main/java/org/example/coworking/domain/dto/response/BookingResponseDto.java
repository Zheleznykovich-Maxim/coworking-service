package org.example.coworking.domain.dto.response;

import org.example.coworking.domain.model.enums.ResourceType;
import java.time.LocalDateTime;

public record BookingResponseDto(
        int id,

        int userId,

        int resourceId,

        LocalDateTime startTime,

        LocalDateTime endTime,

        ResourceType resourceType,

        boolean available
) {
}
