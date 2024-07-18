package org.example.coworking.mapper;

import org.example.coworking.domain.dto.request.BookingRequestDto;
import org.example.coworking.domain.dto.response.BookingResponseDto;
import org.example.coworking.domain.model.Booking;
import org.mapstruct.Mapper;
import java.util.Collection;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking bookingRequestDtotoBooking(BookingRequestDto bookingRequestDto);

    BookingResponseDto bookingToBookingResponseDto(Booking booking);

    Collection<BookingResponseDto> bookingsToBookingResponseDtos(Collection<Booking> bookings);

}
