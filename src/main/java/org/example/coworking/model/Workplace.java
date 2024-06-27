package org.example.coworking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a workplace in the coworking space system.
 */
@Data
@AllArgsConstructor
public class Workplace {
    /**
     * The unique identifier of the workplace.
     */
    private int id;

    /**
     * The name of the workplace.
     */
    private String name;

    /**
     * The availability status of the workplace.
     */
    private boolean isAvailable;
}
