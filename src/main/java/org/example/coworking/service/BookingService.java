package org.example.coworking.service;

import org.example.coworking.domain.dto.request.BookingRequestDto;
import org.example.coworking.domain.dto.response.BookingResponseDto;
import org.example.coworking.domain.model.enums.ResourceType;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Service interface for managing bookings in the coworking system.
 */
public interface BookingService {

    /**
     * Retrieves all bookings.
     *
     * @return a collection of {@link BookingResponseDto} representing all bookings.
     */
    Collection<BookingResponseDto> getBookings();

    /**
     * Adds a new booking based on the provided request data.
     *
     * @param bookingRequestDto the data for the new booking.
     * @return a {@link BookingResponseDto} representing the added booking.
     * @throws SQLException if a database access error occurs.
     */
    BookingResponseDto addBooking(BookingRequestDto bookingRequestDto) throws SQLException;

    /**
     * Updates an existing booking with the specified ID based on the provided request data.
     *
     * @param id the ID of the booking to be updated.
     * @param bookingRequestDto the data for the booking update.
     * @return a {@link BookingResponseDto} representing the updated booking.
     */
    BookingResponseDto updateBooking(int id, BookingRequestDto bookingRequestDto);

    /**
     * Finds a booking by its ID.
     *
     * @param id the ID of the booking to be found.
     * @return a {@link BookingResponseDto} representing the found booking.
     */
    BookingResponseDto findBookingById(int id);

    /**
     * Deletes a booking by its ID.
     *
     * @param id the ID of the booking to be deleted.
     * @return a {@link BookingResponseDto} representing the deleted booking.
     */
    BookingResponseDto deleteBooking(int id);

    /**
     * Filters bookings by a specific date.
     *
     * @param date the date to filter bookings by.
     * @return a collection of {@link BookingResponseDto} representing the filtered bookings.
     */
    Collection<BookingResponseDto> filterBookingsByDate(LocalDate date);

    /**
     * Filters bookings by a specific user ID.
     *
     * @param userId the ID of the user to filter bookings by.
     * @return a collection of {@link BookingResponseDto} representing the filtered bookings.
     */
    Collection<BookingResponseDto> filterBookingsByUser(int userId);

    /**
     * Filters bookings by a specific resource type.
     *
     * @param resourceType the type of resource to filter bookings by.
     * @return a collection of {@link BookingResponseDto} representing the filtered bookings.
     * @throws SQLException if a database access error occurs.
     */
    Collection<BookingResponseDto> filterBookingsByResource(ResourceType resourceType) throws SQLException;
}
