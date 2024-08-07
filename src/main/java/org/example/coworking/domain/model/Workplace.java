package org.example.coworking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a workplace in the coworking space system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
