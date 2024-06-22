package models;


import java.time.LocalDateTime;

public class Booking {
    private int id;
    private User user;
    private int resourceId;
    private String resourceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Booking(int id,
                   User user,
                   int resourceId,
                   String resourceName,
                   LocalDateTime startTime,
                   LocalDateTime endTime) {
        this.id = id;
        this.user = user;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + user +
                ", resourceId=" + resourceId +
                ", resourceName='" + resourceName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
