package org.example.coworking.model;

import lombok.Data;
import org.example.coworking.model.enums.ResourceType;
import java.time.LocalDateTime;

@Data
public class Booking {
    private static int nextBookingId = 1;

    private int id;
    private User user;
    private int resourceId;
    private String resourceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ResourceType resourceType;
    private boolean isAvailable;

    public Booking(User user, int resourceId, String resourceName, LocalDateTime endTime, LocalDateTime startTime, ResourceType resourceType, boolean isAvailable) {
        this.id = nextBookingId++;
        this.user = user;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.endTime = endTime;
        this.startTime = startTime;
        this.resourceType = resourceType;
        this.isAvailable = isAvailable;
    }

    public boolean isUserNull() {
        return this.user == null;
    }
}
