package org.example.coworking.repository;

import org.example.coworking.domain.model.Booking;
import org.example.coworking.domain.model.enums.ResourceType;
import org.example.coworking.repository.query.BookingQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Repository class for managing Booking entities.
 */
@Repository
public class BookingRepository {

    private final DataSource dataSource;

    @Autowired
    public BookingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieves all bookings.
     *
     * @return a collection of all bookings.
     */
    public Collection<Booking> getAllBookings() {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(BookingQuery.GET_ALL_BOOKINGS)) {
                    Collection<Booking> bookings = new ArrayList<>();
                    while (resultSet.next()) {
                        bookings.add(resultSetToBooking(resultSet));
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
    public void addBooking(Booking booking) {
        try (Connection connection = dataSource.getConnection()) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(BookingQuery.GET_ID_NEXT_BOOKING)) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    booking.setId(generatedId);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(BookingQuery.ADD_BOOKING)) {
                preparedStatement.setInt(1, booking.getId());
                preparedStatement.setInt(2, booking.getUserId());
                preparedStatement.setInt(3, booking.getResourceId());
                preparedStatement.setTimestamp(4, Timestamp.valueOf(booking.getStartTime()));
                preparedStatement.setTimestamp(5, Timestamp.valueOf(booking.getEndTime()));
                preparedStatement.setString(6, booking.getResourceType().name());
                preparedStatement.setBoolean(7, booking.isAvailable());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes a booking from the repository by its ID.
     *
     * @param bookingId the ID of the booking to remove.
     */
    public void removeBookingById(int bookingId) {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(BookingQuery.DELETE_BOOKING)) {
                preparedStatement.setInt(1, bookingId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a booking from the repository by its ID.
     *
     * @param booking the booking to update.
     */
    public void updateBooking(Booking booking) {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(BookingQuery.UPDATE_BOOKING)) {
                preparedStatement.setInt(1, booking.getUserId());
                preparedStatement.setInt(2, booking.getResourceId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(booking.getStartTime()));
                preparedStatement.setTimestamp(4, Timestamp.valueOf(booking.getEndTime()));
                preparedStatement.setString(5, booking.getResourceType().name());
                preparedStatement.setBoolean(6, booking.isAvailable());
                preparedStatement.setInt(7, booking.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a booking by its ID.
     *
     * @param bookingId the ID of the booking to find.
     * @return the booking with the specified ID, or null if not found.
     */
    public Optional<Booking> findBookingById(int bookingId) {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(BookingQuery.FIND_BOOKING_BY_ID)) {
                preparedStatement.setInt(1, bookingId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(resultSetToBooking(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /**
     * Filters bookings by a specific date.
     *
     * @param date the date to filter bookings by.
     * @return a collection of bookings that start or end on the specified date.
     */
    public Collection<Booking> filterBookingsByDate(LocalDate date) {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(BookingQuery.FILTER_BOOKINGS_BY_DATE)) {
                preparedStatement.setDate(1, Date.valueOf(date));
                preparedStatement.setDate(2, Date.valueOf(date));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    Collection<Booking> bookings = new ArrayList<>();
                    while (resultSet.next()) {
                        bookings.add(resultSetToBooking(resultSet));
                    }
                    return bookings;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Filters bookings by a specific resource type.
     *
     * @param resourceType the resource type to filter bookings by.
     * @return a collection of bookings with the specified resource type.
     */
    public Collection<Booking> filterBookingsByResource(ResourceType resourceType) {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(BookingQuery.FILTER_BOOKINGS_BY_RESOURCE)) {
                preparedStatement.setString(1, resourceType.name());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    Collection<Booking> bookings = new ArrayList<>();
                    while (resultSet.next()) {
                        bookings.add(resultSetToBooking(resultSet));
                    }
                    return bookings;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Filters bookings by a specific user.
     *
     * @param userId the user to filter bookings by.
     * @return a collection of bookings made by the specified user.
     */
    public Collection<Booking> filterBookingsByUser(int userId) {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(BookingQuery.FILTER_BOOKING_BY_USER)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    Collection<Booking> bookings = new ArrayList<>();
                    while (resultSet.next()) {
                        bookings.add(resultSetToBooking(resultSet));
                    }
                    return bookings;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Booking resultSetToBooking(ResultSet resultSet) throws SQLException {
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
