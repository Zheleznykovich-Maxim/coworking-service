package org.example.coworking.repository;

import lombok.AllArgsConstructor;
import org.example.coworking.model.Workplace;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repository class for managing workplaces.
 */
@AllArgsConstructor
public class WorkplaceRepository {
    private final Map<Integer, Workplace> workplaceMap;

    /**
     * Retrieves all workplaces from the repository.
     *
     * @return A collection of all workplaces.
     */
    public Collection<Workplace> getAllWorkplaces() {
        return workplaceMap.values();
    }

    /**
     * Adds a new workplace to the repository.
     *
     * @param workplace The workplace to add.
     */
    public void addWorkplace(Workplace workplace) {
        workplaceMap.put(workplace.getId(), workplace);
    }

    /**
     * Removes a workplace from the repository based on its ID.
     *
     * @param workplaceId The ID of the workplace to remove.
     */
    public void removeWorkplaceById(int workplaceId) {
        workplaceMap.remove(workplaceId);
    }

    /**
     * Finds a workplace by its ID.
     *
     * @param workplaceId The ID of the workplace to find.
     * @return The workplace with the specified ID, or null if not found.
     */
    public Workplace findWorkplaceById(int workplaceId) {
        return workplaceMap.get(workplaceId);
    }

    /**
     * Updates an existing workplace in the repository.
     *
     * @param workplace The updated workplace object.
     */
    public void updateWorkplace(Workplace workplace) {
        workplaceMap.put(workplace.getId(), workplace);
    }

    /**
     * Retrieves all available workplaces from the repository.
     *
     * @return A collection of all available workplaces.
     */
    public Collection<Workplace> getAvailableWorkplaces() {
        return workplaceMap.values().stream()
                .filter(Workplace::isAvailable)
                .collect(Collectors.toList());
    }
}
