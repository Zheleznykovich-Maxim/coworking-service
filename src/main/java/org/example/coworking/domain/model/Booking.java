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
}

