package org.example.coworking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a conference hall in the coworking space.
 */
@Data
@AllArgsConstructor
public class ConferenceHall {
    /**
     * Unique identifier for the conference hall.
     */
    private int id;

    /**
     * The name of the conference hall.
     */
    private String name;

    /**
     * Indicates whether the conference hall is currently available.
     */
    private boolean isAvailable;
}
