package org.example.coworking.mapper;

import org.example.coworking.model.Booking;
import org.example.coworking.model.enums.ResourceType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingMapper {
    public static Booking resultSetToBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setId(resultSet.getInt("id"));
        booking.setUserId(resultSet.getInt("user_id"));
        booking.setResourceId(resultSet.getInt("resource_id"));
        booking.setResourceName(resultSet.getString("resource_name"));
        booking.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
        booking.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());
        booking.setResourceType(ResourceType.valueOf(resultSet.getString("resource_type")));
        booking.setAvailable(resultSet.getBoolean("is_available"));
        return booking;
    }
}
