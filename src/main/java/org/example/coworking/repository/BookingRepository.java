package org.example.coworking.repository;

import lombok.AllArgsConstructor;
import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.mapper.BookingMapper;
import org.example.coworking.model.Booking;
import org.example.coworking.model.User;
import org.example.coworking.model.enums.ResourceType;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public Collection<Booking> getAllBookings() throws IOException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM coworking.booking";
            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
                    Collection<Booking> bookings = new ArrayList<>();
                    while (resultSet.next()) {
                        bookings.add(BookingMapper.resultSetToBooking(resultSet));
                    }
                    return bookings;
                } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new booking to the repository.
     *
     * @param booking the booking to add.
     */
    public void addBooking(Booking booking) throws IOException, SQLException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO coworking.booking " +
                    "(user_id, resource_id, resource_name, start_time, end_time, resource_type, is_available)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, booking.getUserId());
                preparedStatement.setInt(2, booking.getResourceId());
                preparedStatement.setString(3, booking.getResourceName());
                preparedStatement.setTimestamp(4, Timestamp.valueOf(booking.getStartTime()));
                preparedStatement.setTimestamp(5, Timestamp.valueOf(booking.getEndTime()));
                preparedStatement.setString(6, booking.getResourceType().name());
                preparedStatement.setBoolean(7, booking.isAvailable());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            booking.setId(generatedKeys.getInt(1));
                        }
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Removes a booking from the repository by its ID.
     *
     * @param bookingId the ID of the booking to remove.
     */
    public void removeBookingById(int bookingId) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM coworking.booking WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, bookingId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a booking by its ID.
     *
     * @param bookingId the ID of the booking to find.
     * @return the booking with the specified ID, or null if not found.
     */
    public Booking findBookingById(int bookingId) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM coworking.booking WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, bookingId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return BookingMapper.resultSetToBooking(resultSet);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
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
//        try (Connection connection = DatabaseConfig.getConnection()) {
//            String query = "SELECT * FROM coworking.booking WHERE DATE(start_time) = ? OR DATE(end_time) = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setDate(1, Date.valueOf(date));
//                preparedStatement.setDate(2, Date.valueOf(date));
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//
//                }
//            }
//        }
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
                .filter(entry -> entry.getValue().getUserId() > 0 && entry.getValue().getUserId() == (user.getId()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }
}
