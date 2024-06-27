package org.example.coworking.repository;

import lombok.AllArgsConstructor;
import org.example.coworking.model.Booking;
import org.example.coworking.model.User;
import org.example.coworking.model.enums.ResourceType;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repository class for managing Booking entities.
 */
@AllArgsConstructor
public class BookingRepository {
    private final Map<Integer, Booking> bookingMap;

    /**
     * Retrieves all bookings.
     *
     * @return a collection of all bookings.
     */
    public Collection<Booking> getAllBookings() {
        return bookingMap.values();
    }

    /**
     * Adds a new booking to the repository.
     *
     * @param booking the booking to add.
     */
    public void addBooking(Booking booking) {
        bookingMap.put(booking.getId(), booking);
    }

    /**
     * Removes a booking from the repository by its ID.
     *
     * @param bookingId the ID of the booking to remove.
     */
    public void removeBookingById(int bookingId) {
        bookingMap.remove(bookingId);
    }

    /**
     * Finds a booking by its ID.
     *
     * @param bookingId the ID of the booking to find.
     * @return the booking with the specified ID, or null if not found.
     */
    public Booking findBookingById(int bookingId) {
        return bookingMap.get(bookingId);
    }

    /**
     * Filters bookings by a specific date.
     *
     * @param date the date to filter bookings by.
     * @return a collection of bookings that start or end on the specified date.
     */
    public Collection<Booking> filterBookingsByDate(LocalDate date) {
        return bookingMap.entrySet().stream()
                .filter(entry -> entry.getValue().getStartTime().toLocalDate().equals(date)
                        || entry.getValue().getEndTime().toLocalDate().equals(date))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }

    /**
     * Filters bookings by a specific resource type.
     *
     * @param resourceType the resource type to filter bookings by.
     * @return a collection of bookings with the specified resource type.
     */
    public Collection<Booking> filterBookingsByResource(ResourceType resourceType) {
        return bookingMap.entrySet().stream()
                .filter(entry -> entry.getValue().getResourceType().equals(resourceType))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }

    /**
     * Filters bookings by a specific user.
     *
     * @param user the user to filter bookings by.
     * @return a collection of bookings made by the specified user.
     */
    public Collection<Booking> filterBookingsByUser(User user) {
        return bookingMap.entrySet().stream()
                .filter(entry -> entry.getValue().getUser() != null && entry.getValue().getUser().equals(user))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }
}
