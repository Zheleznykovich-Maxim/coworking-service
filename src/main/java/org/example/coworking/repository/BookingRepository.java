package org.example.coworking.repository;

import org.example.coworking.model.Booking;
import org.example.coworking.model.User;
import org.example.coworking.model.enums.ResourceType;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingRepository {
    private final Map<Integer, Booking> bookingMap;

    public BookingRepository(Map<Integer, Booking> bookings) {
        this.bookingMap = bookings;
    }

    public Collection<Booking> getAllBookings() {
        return bookingMap.values();
    }

    public void addBooking(Booking booking) {
        bookingMap.put(booking.getId(), booking);
    }

    public void removeBookingById(int bookingId) {
        bookingMap.remove(bookingId);
    }

    public Booking findBookingById(int bookingId) {
        return bookingMap.get(bookingId);
    }

    public Collection<Booking> filterBookingsByDate(LocalDate date) {
        return bookingMap.entrySet().stream()
                .filter(entry -> entry.getValue().getStartTime().toLocalDate().equals(date)
                        || entry.getValue().getEndTime().toLocalDate().equals(date))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }

    public Collection<Booking> filterBookingByResource(ResourceType resourceType) {
        return bookingMap.entrySet().stream()
                .filter(entry -> entry.getValue().getResourceType().equals(resourceType))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }

    public Collection<Booking> filterBookingByUser(User user) {
        return bookingMap.entrySet().stream()
                .filter(entry -> entry.getValue().getUser() != null && entry.getValue().getUser().equals(user))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }
}
