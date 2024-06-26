package org.example.coworking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConferenceHall {
    private int id;
    private String name;
    private boolean isAvailable;
}
