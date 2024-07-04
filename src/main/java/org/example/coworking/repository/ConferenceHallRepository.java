package org.example.coworking.repository;

import lombok.AllArgsConstructor;
import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.mapper.ConferenceHallMapper;
import org.example.coworking.model.ConferenceHall;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Repository class for managing ConferenceHall entities.
 */
@AllArgsConstructor
public class ConferenceHallRepository {

    /**
     * Retrieves all conference halls.
     *
     * @return a collection of all conference halls.
     */
    public Collection<ConferenceHall> getAllConferenceHalls() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM coworking.conference_halls";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                Collection<ConferenceHall> conferenceHalls = new ArrayList<>();
                while (resultSet.next()) {
                    conferenceHalls.add(ConferenceHallMapper.resultSetToConferenceHall(resultSet));
                }
                return conferenceHalls;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new conference hall to the repository.
     *
     * @param conferenceHall the conference hall to add.
     */
    public void addConferenceHall(ConferenceHall conferenceHall) throws IOException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String getIdQuery = "SELECT nextval('coworking.conference_hall_seq')";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(getIdQuery);
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    conferenceHall.setId(generatedId);
                }
            }

            String insertQuery = "INSERT INTO coworking.conference_halls " +
                    "(id, name, is_available)" +
                    "VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, conferenceHall.getId());
                preparedStatement.setString(2, conferenceHall.getName());
                preparedStatement.setBoolean(3, conferenceHall.isAvailable());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes a conference hall from the repository by its ID.
     *
     * @param conferenceHallId the ID of the conference hall to remove.
     */
    public void removeConferenceHallById(int conferenceHallId) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM coworking.conference_halls WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, conferenceHallId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a conference hall by its ID.
     *
     * @param conferenceHallId the ID of the conference hall to find.
     * @return the conference hall with the specified ID, or null if not found.
     */
    public Optional<ConferenceHall> findConferenceById(int conferenceHallId) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM coworking.conference_halls WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, conferenceHallId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(ConferenceHallMapper.resultSetToConferenceHall(resultSet));
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /**
     * Updates an existing conference hall in the repository.
     *
     * @param conferenceHall the conference hall with updated information.
     */
    public void updateConferenceHall(ConferenceHall conferenceHall) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "UPDATE coworking.conference_halls SET name = ?, is_available = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, conferenceHall.getName());
                preparedStatement.setBoolean(2, conferenceHall.isAvailable());
                preparedStatement.setInt(3, conferenceHall.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all available conference halls.
     *
     * @return a collection of available conference halls.
     */
    public Collection<ConferenceHall> getAvailableConferenceHalls() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM coworking.conference_halls WHERE is_available = true";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                Collection<ConferenceHall> conferenceHalls = new ArrayList<>();
                while (resultSet.next()) {
                    conferenceHalls.add(ConferenceHallMapper.resultSetToConferenceHall(resultSet));
                }
                return conferenceHalls;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
