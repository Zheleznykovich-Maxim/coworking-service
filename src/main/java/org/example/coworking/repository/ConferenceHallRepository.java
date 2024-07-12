package org.example.coworking.repository;

import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.mapper.ConferenceHallMapper;
import org.example.coworking.domain.model.ConferenceHall;
import org.example.coworking.repository.query.ConferenceHallQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
@Repository
public class ConferenceHallRepository {
    private final ConferenceHallMapper conferenceHallMapper;
    private final DatabaseConfig databaseConfig;

    @Autowired
    public ConferenceHallRepository(ConferenceHallMapper conferenceHallMapper, DatabaseConfig databaseConfig) {
        this.conferenceHallMapper = conferenceHallMapper;
        this.databaseConfig = databaseConfig;
    }

    /**
     * Retrieves all conference halls.
     *
     * @return a collection of all conference halls.
     */
    public Collection<ConferenceHall> getAllConferenceHalls() {
        try (Connection connection = databaseConfig.getConnection()) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(ConferenceHallQuery.GET_ALL_CONFERENCE_HALLS)) {
                Collection<ConferenceHall> conferenceHalls = new ArrayList<>();
                while (resultSet.next()) {
                    conferenceHalls.add(conferenceHallMapper.resultSetToConferenceHall(resultSet));
                }
                return conferenceHalls;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new conference hall to the repository.
     *
     * @param conferenceHall the conference hall to add.
     */
    public void addConferenceHall(ConferenceHall conferenceHall) {
        try (Connection connection = databaseConfig.getConnection()) {

            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(ConferenceHallQuery.GET_ID_NEXT_CONFERENCE_HALL)) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    conferenceHall.setId(generatedId);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(ConferenceHallQuery.ADD_CONFERENCE_HALL)) {
                preparedStatement.setInt(1, conferenceHall.getId());
                preparedStatement.setString(2, conferenceHall.getName());
                preparedStatement.setBoolean(3, conferenceHall.isAvailable());
                preparedStatement.executeUpdate();
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
        try (Connection connection = databaseConfig.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(ConferenceHallQuery.DELETE_CONFERENCE_HALL)) {
                preparedStatement.setInt(1, conferenceHallId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
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
        try (Connection connection = databaseConfig.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(ConferenceHallQuery.FIND_CONFERENCE_HALL_BY_ID)) {
                preparedStatement.setInt(1, conferenceHallId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(conferenceHallMapper.resultSetToConferenceHall(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
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
        try (Connection connection = databaseConfig.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(ConferenceHallQuery.UPDATE_CONFERENCE_HALL)) {
                preparedStatement.setString(1, conferenceHall.getName());
                preparedStatement.setBoolean(2, conferenceHall.isAvailable());
                preparedStatement.setInt(3, conferenceHall.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all available conference halls.
     *
     * @return a collection of available conference halls.
     */
    public Collection<ConferenceHall> getAvailableConferenceHalls() {
        try (Connection connection = databaseConfig.getConnection()) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(ConferenceHallQuery.GET_ALL_AVAILABLE_CONFERENCE_HALLS)) {
                Collection<ConferenceHall> conferenceHalls = new ArrayList<>();
                while (resultSet.next()) {
                    conferenceHalls.add(conferenceHallMapper.resultSetToConferenceHall(resultSet));
                }
                return conferenceHalls;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
