package org.example.coworking.dto.request;

import org.example.coworking.model.enums.ResourceType;

import java.time.LocalDateTime;

public record BookingRequestDto(

        int userId,

        int resourceId,

        LocalDateTime startTime,

        LocalDateTime endTime,

        ResourceType resourceType,

        boolean isAvailable
) {
}
