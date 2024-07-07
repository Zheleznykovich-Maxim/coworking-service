package org.example.coworking.repository.query;

public class BookingQuery {
    public static final String GET_ALL_BOOKINGS = "SELECT * FROM coworking.bookings";
    public static final String GET_ID_NEXT_BOOKING = "SELECT nextval('coworking.booking_seq')";
    public static final String ADD_BOOKING = "INSERT INTO coworking.bookings " +
            "(id, user_id, resource_id, start_time, end_time, resource_type, is_available)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_BOOKING = "DELETE FROM coworking.bookings WHERE id = ?";
    public static final String UPDATE_BOOKING = "UPDATE coworking.bookings SET user_id = ?, resource_id = ?, start_time = ?, end_time = ?, resource_type = ?, is_available = ? WHERE id = ?";
    public static final String FIND_BOOKING_BY_ID = "SELECT * FROM coworking.bookings WHERE id = ?";
    public static final String FILTER_BOOKINGS_BY_DATE = "SELECT * FROM coworking.bookings WHERE DATE(start_time) = ? OR DATE(end_time) = ?";
    public static final String FILTER_BOOKINGS_BY_RESOURCE = "SELECT * FROM coworking.bookings WHERE resource_type = ?";
    public static final String FILTER_BOOKING_BY_USER = "SELECT * FROM coworking.bookings WHERE user_id = ?";
}
