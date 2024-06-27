package org.example.coworking.repository;

import lombok.AllArgsConstructor;
import org.example.coworking.model.ConferenceHall;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repository class for managing ConferenceHall entities.
 */
@AllArgsConstructor
public class ConferenceHallRepository {
    private final Map<Integer, ConferenceHall> conferenceHallMap;

    /**
     * Retrieves all conference halls.
     *
     * @return a collection of all conference halls.
     */
    public Collection<ConferenceHall> getAllConferenceHalls() {
        return conferenceHallMap.values();
    }

    /**
     * Adds a new conference hall to the repository.
     *
     * @param conferenceHall the conference hall to add.
     */
    public void addConferenceHall(ConferenceHall conferenceHall) {
        conferenceHallMap.put(conferenceHall.getId(), conferenceHall);
    }

    /**
     * Removes a conference hall from the repository by its ID.
     *
     * @param conferenceHallId the ID of the conference hall to remove.
     */
    public void removeConferenceHallById(int conferenceHallId) {
        conferenceHallMap.remove(conferenceHallId);
    }

    /**
     * Finds a conference hall by its ID.
     *
     * @param conferenceHallId the ID of the conference hall to find.
     * @return the conference hall with the specified ID, or null if not found.
     */
    public ConferenceHall findConferenceById(int conferenceHallId) {
        return conferenceHallMap.get(conferenceHallId);
    }

    /**
     * Updates an existing conference hall in the repository.
     *
     * @param conferenceHall the conference hall with updated information.
     */
    public void updateConferenceHall(ConferenceHall conferenceHall) {
        conferenceHallMap.put(conferenceHall.getId(), conferenceHall);
    }

    /**
     * Retrieves all available conference halls.
     *
     * @return a collection of available conference halls.
     */
    public Collection<ConferenceHall> getAvailableConferenceHalls() {
        return conferenceHallMap.entrySet().stream()
                .filter(entry -> entry.getValue().isAvailable())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }
}
