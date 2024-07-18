package org.example.coworking.domain.dto.request;

import org.example.coworking.domain.model.enums.ResourceType;

import java.time.LocalDateTime;

public record BookingRequestDto(

        int userId,

        int resourceId,

        LocalDateTime startTime,

        LocalDateTime endTime,

        ResourceType resourceType,

        boolean available
) {
}
