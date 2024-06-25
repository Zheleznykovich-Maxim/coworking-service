    package org.example.coworking.model;

    import org.example.coworking.model.enums.ResourceType;
    import java.time.LocalDateTime;

    public class Booking {
        private int id;
        private User user;
        private int resourceId;
        private String resourceName;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private ResourceType resourceType;
        private boolean isAvailable;

        public Booking(int id,
                       int resourceId,
                       String resourceName,
                       LocalDateTime startTime,
                       LocalDateTime endTime,
                       ResourceType resourceType) {
            this.id = id;
            this.resourceId = resourceId;
            this.resourceName = resourceName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.resourceType = resourceType;
            isAvailable = true;
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

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
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

        public ResourceType getResourceType() {
            return resourceType;
        }

        public void setResourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
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
                    ", resourceType=" + resourceType +
                    ", isAvailable=" + isAvailable +
                    '}';
        }

        public boolean isUserNull() {
            return this.user == null;
        }
    }
