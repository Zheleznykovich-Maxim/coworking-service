package org.example.coworking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a conference hall in the coworking space.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * Constructs a new ConferenceHall with the given parameters.
     * The id is auto-generated and incremented.
     *
     * @param name the name of the conferenceHall
     * @param isAvailable the value of the availability of the conferenceHall
     */
    public ConferenceHall(String name, boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
    }
}
