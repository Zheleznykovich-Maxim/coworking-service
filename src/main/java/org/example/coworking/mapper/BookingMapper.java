package org.example.coworking.mapper;

import org.example.coworking.domain.dto.request.BookingRequestDto;
import org.example.coworking.domain.dto.response.BookingResponseDto;
import org.example.coworking.domain.model.Booking;
import org.example.coworking.domain.model.enums.ResourceType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Mapper
public interface BookingMapper {
    Booking bookingRequestDtotoBooking(BookingRequestDto bookingRequestDto);
    BookingResponseDto bookingToBookingResponseDto(Booking booking);
    Collection<BookingResponseDto> bookingsToBookingResponseDtos(Collection<Booking> bookings);

    @Named("resultSetToBooking")
    default Booking resultSetToBooking(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        Booking booking = new Booking();
        booking.setId(resultSet.getInt("id"));
        booking.setUserId(resultSet.getInt("user_id"));
        booking.setResourceId(resultSet.getInt("resource_id"));
        booking.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
        booking.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());
        booking.setResourceType(ResourceType.valueOf(resultSet.getString("resource_type")));
        booking.setAvailable(resultSet.getBoolean("is_available"));
        return booking;
    }
}
