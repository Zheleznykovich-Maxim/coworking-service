package org.example.coworking.repository;

import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.mapper.WorkplaceMapper;
import org.example.coworking.domain.model.Workplace;
import org.example.coworking.repository.query.WorkplaceQuery;
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
 * Repository class for managing workplaces.
 */
@Repository
public class WorkplaceRepository {
    private final WorkplaceMapper workplaceMapper;
    private final DatabaseConfig databaseConfig;

    @Autowired
    public WorkplaceRepository(WorkplaceMapper workplaceMapper, DatabaseConfig databaseConfig) {
        this.workplaceMapper = workplaceMapper;
        this.databaseConfig = databaseConfig;
    }

    /**
     * Retrieves all workplaces from the repository.
     *
     * @return A collection of all workplaces.
     */
    public Collection<Workplace> getAllWorkplaces() {
        try (Connection connection =  databaseConfig.getConnection()) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(WorkplaceQuery.GET_ALL_WORKPLACES)) {
                Collection<Workplace> workplaces = new ArrayList<>();
                while (resultSet.next()) {
                    workplaces.add(workplaceMapper.resultSetToWorkplace(resultSet));
                }
                return workplaces;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new workplace to the repository.
     *
     * @param workplace The workplace to add.
     */
    public void addWorkplace(Workplace workplace) {
        try (Connection connection = databaseConfig.getConnection()) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(WorkplaceQuery.GET_ID_NEXT_WORKPLACE)) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    workplace.setId(generatedId);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(WorkplaceQuery.ADD_WORKPLACE)) {
                preparedStatement.setInt(1, workplace.getId());
                preparedStatement.setString(2, workplace.getName());
                preparedStatement.setBoolean(3, workplace.isAvailable());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes a workplace from the repository based on its ID.
     *
     * @param workplaceId The ID of the workplace to remove.
     */
    public void removeWorkplaceById(int workplaceId) {
        try (Connection connection = databaseConfig.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(WorkplaceQuery.DELETE_WORKPLACE)) {
                preparedStatement.setInt(1, workplaceId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a workplace by its ID.
     *
     * @param workplaceId The ID of the workplace to find.
     * @return The workplace with the specified ID, or null if not found.
     */
    public Optional<Workplace> findWorkplaceById(int workplaceId) {
        try (Connection connection = databaseConfig.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(WorkplaceQuery.FIND_WORKPLACE_BY_ID)) {
                preparedStatement.setLong(1, workplaceId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(workplaceMapper.resultSetToWorkplace(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /**
     * Updates an existing workplace in the repository.
     *
     * @param workplace The updated workplace object.
     */
    public void updateWorkplace(Workplace workplace) {
        try (Connection connection = databaseConfig.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(WorkplaceQuery.UPDATE_WORKPLACE)) {
                preparedStatement.setString(1, workplace.getName());
                preparedStatement.setBoolean(2, workplace.isAvailable());
                preparedStatement.setInt(3, workplace.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all available workplaces from the repository.
     *
     * @return A collection of all available workplaces.
     */
    public Collection<Workplace> getAvailableWorkplaces() {
        try (Connection connection = databaseConfig.getConnection()) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(WorkplaceQuery.GET_ALL_AVAILABLE_WORKPLACES)) {
                Collection<Workplace> workplaces = new ArrayList<>();
                while (resultSet.next()) {
                    workplaces.add(workplaceMapper.resultSetToWorkplace(resultSet));
                }
                return workplaces;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
