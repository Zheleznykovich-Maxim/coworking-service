package org.example.coworking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.coworking.domain.model.enums.ResourceType;

import java.time.LocalDateTime;

/**
 * Represents a booking for a coworking space resource.
 * Each booking is uniquely identified by an id which is auto-incremented.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    /**
     * Unique identifier for the booking.
     */
    private int id;

    /**
     * The user who made the booking.
     */
    private int userId;

    /**
     * The id of the resource being booked.
     */
    private int resourceId;

    /**
     * The start time of the booking.
     */
    private LocalDateTime startTime;

    /**
     * The end time of the booking.
     */
    private LocalDateTime endTime;

    /**
     * The type of the resource being booked.
     */
    private ResourceType resourceType;

    /**
     * Indicates whether the booking is currently available.
     */
    private boolean available;
    /**
     * Constructs a new Booking with the given parameters.
     * The id is auto-generated and incremented.
     *
     * @param userId the id of the user who made the booking
     * @param resourceId the id of the resource being booked
     * @param startTime the start time of the booking
     * @param endTime the end time of the booking
     * @param resourceType the type of the resource being booked
     * @param isAvailable whether the booking is currently available
     */
    public Booking(int userId, int resourceId, LocalDateTime startTime, LocalDateTime endTime, ResourceType resourceType, boolean isAvailable) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resourceType = resourceType;
        this.available = isAvailable;
    }
}

