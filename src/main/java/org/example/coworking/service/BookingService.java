package org.example.coworking.service;

import lombok.RequiredArgsConstructor;
import org.example.coworking.annotations.Loggable;
import org.example.coworking.exception.EntityNotFoundException;
import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.model.Booking;
import org.example.coworking.model.User;
import org.example.coworking.repository.BookingRepository;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Service class for managing bookings.
 */
@Loggable
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    /**
     * Adds a new booking to the repository.
     *
     * @param booking The booking to add.
     */
    public void addBooking(Booking booking) throws SQLException {
        bookingRepository.addBooking(booking);
    }

    /**
     * Deletes a booking from the repository based on its ID.
     *
     * @param bookingId The ID of the booking to delete.
     */
    public void deleteBooking(int bookingId) {
        bookingRepository.removeBookingById(bookingId);
    }

    /**
     * Retrieves all bookings from the repository.
     *
     * @return A collection of all bookings.
     */
    public Collection<Booking> getAllBookings() {
        return bookingRepository.getAllBookings();
    }

    /**
     * Updates a booking based on its ID to the repository.
     *
     * @param booking The booking to update.
     */
    public void updateBooking(Booking booking) throws SQLException {
        bookingRepository.updateBooking(booking);
    }

    /**
     * Filters bookings based on a specified date.
     *
     * @param date The date to filter bookings by.
     * @return A collection of bookings that match the specified date.
     */
    public Collection<Booking> filterBookingsByDate(LocalDate date) {
        return bookingRepository.filterBookingsByDate(date);
    }

    /**
     * Filters bookings based on a specified user.
     *
     * @param user The user to filter bookings by.
     * @return A collection of bookings that belong to the specified user.
     */
    public Collection<Booking> filterBookingsByUser(User user) {
        return bookingRepository.filterBookingsByUser(user);
    }

    /**
     * Filters bookings based on a specified resource type.
     *
     * @param resourceType The resource type to filter bookings by.
     * @return A collection of bookings that match the specified resource type.
     */
    public Collection<Booking> filterBookingsByResource(ResourceType resourceType) {
        return bookingRepository.filterBookingsByResource(resourceType);
    }

    /**
     * Finds a booking by its ID.
     *
     * @param id The ID of the booking to find.
     * @return The booking with the specified ID, or null if not found.
     */
    public Booking findBookingById(int id) {
        return bookingRepository.findBookingById(id)
                .orElseThrow(() -> new EntityNotFoundException("Бронь с таким id не найдена!"));
    }
}
