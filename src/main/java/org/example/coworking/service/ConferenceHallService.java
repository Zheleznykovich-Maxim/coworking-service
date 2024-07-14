package org.example.coworking.service;

import org.example.coworking.domain.dto.request.ConferenceHallRequestDto;
import org.example.coworking.domain.dto.response.ConferenceHallResponseDto;
import java.util.Collection;

/**
 * Service interface for managing conference halls in the coworking system.
 */
public interface ConferenceHallService {

    /**
     * Retrieves all conference halls.
     *
     * @return a collection of {@link ConferenceHallResponseDto} representing all conference halls.
     */
    Collection<ConferenceHallResponseDto> getConferenceHalls();

    /**
     * Adds a new conference hall based on the provided request data.
     *
     * @param conferenceHallRequestDto the data for the new conference hall.
     * @return a {@link ConferenceHallResponseDto} representing the added conference hall.
     */
    ConferenceHallResponseDto addConferenceHall(ConferenceHallRequestDto conferenceHallRequestDto);

    /**
     * Updates an existing conference hall with the specified ID based on the provided request data.
     *
     * @param id the ID of the conference hall to be updated.
     * @param conferenceHallRequestDto the data for the conference hall update.
     * @return a {@link ConferenceHallResponseDto} representing the updated conference hall.
     */
    ConferenceHallResponseDto updateConferenceHall(int id, ConferenceHallRequestDto conferenceHallRequestDto);

    /**
     * Finds a conference hall by its ID.
     *
     * @param id the ID of the conference hall to be found.
     * @return a {@link ConferenceHallResponseDto} representing the found conference hall.
     */
    ConferenceHallResponseDto findConferenceHallById(int id);

    /**
     * Deletes a conference hall by its ID.
     *
     * @param id the ID of the conference hall to be deleted.
     * @return a {@link ConferenceHallResponseDto} representing the deleted conference hall.
     */
    ConferenceHallResponseDto deleteConferenceHall(int id);
}
