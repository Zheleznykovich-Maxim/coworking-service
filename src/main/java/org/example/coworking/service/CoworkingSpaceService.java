package org.example.coworking.service;

import lombok.AllArgsConstructor;
import org.example.coworking.model.ConferenceHall;
import org.example.coworking.model.Workplace;
import org.example.coworking.repository.ConferenceHallRepository;
import org.example.coworking.repository.WorkplaceRepository;

import java.util.Collection;

/**
 * Service class for managing coworking space operations.
 */
@AllArgsConstructor
public class CoworkingSpaceService {
    private final ConferenceHallRepository conferenceHallRepository;
    private final WorkplaceRepository workplaceRepository;

    /**
     * Retrieves all workplaces.
     *
     * @return a collection of all workplaces.
     */
    public Collection<Workplace> getWorkplaces() {
        return workplaceRepository.getAllWorkplaces();
    }

    /**
     * Retrieves all conference halls.
     *
     * @return a collection of all conference halls.
     */
    public Collection<ConferenceHall> getConferenceHalls() {
        return conferenceHallRepository.getAllConferenceHalls();
    }

    /**
     * Adds a new workplace.
     *
     * @param workplace the workplace to add.
     */
    public void addWorkplace(Workplace workplace) {
        workplaceRepository.addWorkplace(workplace);
    }

    /**
     * Adds a new conference hall.
     *
     * @param conferenceHall the conference hall to add.
     */
    public void addConferenceHall(ConferenceHall conferenceHall) {
        conferenceHallRepository.addConferenceHall(conferenceHall);
    }

    /**
     * Retrieves all available workplaces.
     *
     * @return a collection of all available workplaces.
     */
    public Collection<Workplace> getAvailableWorkplaces() {
        return workplaceRepository.getAvailableWorkplaces();
    }

    /**
     * Retrieves all available conference halls.
     *
     * @return a collection of all available conference halls.
     */
    public Collection<ConferenceHall> getAvailableConferenceHalls() {
        return conferenceHallRepository.getAvailableConferenceHalls();
    }

    /**
     * Updates an existing conference hall.
     *
     * @param conferenceHall the updated conference hall.
     */
    public void updateConferenceHall(ConferenceHall conferenceHall) {
        conferenceHallRepository.updateConferenceHall(conferenceHall);
    }

    /**
     * Updates an existing workplace.
     *
     * @param workplace the updated workplace.
     */
    public void updateWorkplace(Workplace workplace) {
        workplaceRepository.updateWorkplace(workplace);
    }

    /**
     * Deletes a conference hall by its ID.
     *
     * @param id the ID of the conference hall to delete.
     */
    public void deleteConferenceHall(int id) {
        conferenceHallRepository.removeConferenceHallById(id);
    }

    /**
     * Deletes a workplace by its ID.
     *
     * @param id the ID of the workplace to delete.
     */
    public void deleteWorkplace(int id) {
        workplaceRepository.removeWorkplaceById(id);
    }

    /**
     * Finds a workplace by its ID.
     *
     * @param id the ID of the workplace to find.
     * @return the workplace with the specified ID, or null if not found.
     */
    public Workplace findWorkplaceById(int id) {
        return workplaceRepository.findWorkplaceById(id);
    }

    /**
     * Finds a conference hall by its ID.
     *
     * @param id the ID of the conference hall to find.
     * @return the conference hall with the specified ID, or null if not found.
     */
    public ConferenceHall findConferenceHallById(int id) {
        return conferenceHallRepository.findConferenceById(id);
    }
}
