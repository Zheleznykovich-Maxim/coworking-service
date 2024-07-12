package org.example.coworking.service;

import org.example.coworking.domain.dto.request.BookingRequestDto;
import org.example.coworking.domain.dto.response.BookingResponseDto;
import java.util.Collection;

public interface BookingService {

    Collection<BookingResponseDto> getBookings();

    BookingResponseDto addBooking(BookingRequestDto bookingRequestDto);

    BookingResponseDto updateBooking(int id, BookingRequestDto bookingRequestDto);

    BookingResponseDto findBookingById(int id);

    BookingResponseDto deleteBooking(int id);
}
