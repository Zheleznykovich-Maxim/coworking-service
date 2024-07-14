package org.example.coworking.service;

import org.example.coworking.domain.dto.request.BookingRequestDto;
import org.example.coworking.domain.dto.response.BookingResponseDto;
import org.example.coworking.domain.model.enums.ResourceType;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

public interface BookingService {

    Collection<BookingResponseDto> getBookings();

    BookingResponseDto addBooking(BookingRequestDto bookingRequestDto) throws SQLException;

    BookingResponseDto updateBooking(int id, BookingRequestDto bookingRequestDto);

    BookingResponseDto findBookingById(int id);

    BookingResponseDto deleteBooking(int id);

    Collection<BookingResponseDto> filterBookingsByDate(LocalDate date);

    Collection<BookingResponseDto> filterBookingsByUser(int userId);

    Collection<BookingResponseDto> filterBookingsByResource(ResourceType resourceType) throws SQLException;
}
