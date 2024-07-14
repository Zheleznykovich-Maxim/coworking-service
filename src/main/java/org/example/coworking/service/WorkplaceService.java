package org.example.coworking.service;

import org.example.coworking.domain.dto.request.WorkplaceRequestDto;
import org.example.coworking.domain.dto.response.WorkplaceResponseDto;
import java.util.Collection;

/**
 * Service interface for managing workplaces in the coworking system.
 */
public interface WorkplaceService {

    /**
     * Retrieves all workplaces.
     *
     * @return a collection of {@link WorkplaceResponseDto} representing all workplaces.
     */
    Collection<WorkplaceResponseDto> getWorkplaces();

    /**
     * Adds a new workplace based on the provided request data.
     *
     * @param workplaceRequestDto the data for the new workplace.
     * @return a {@link WorkplaceResponseDto} representing the added workplace.
     */
    WorkplaceResponseDto addWorkplace(WorkplaceRequestDto workplaceRequestDto);

    /**
     * Updates an existing workplace with the specified ID based on the provided request data.
     *
     * @param id the ID of the workplace to be updated.
     * @param workplaceRequestDto the data for the workplace update.
     * @return a {@link WorkplaceResponseDto} representing the updated workplace.
     */
    WorkplaceResponseDto updateWorkplace(int id, WorkplaceRequestDto workplaceRequestDto);

    /**
     * Finds a workplace by its ID.
     *
     * @param id the ID of the workplace to be found.
     * @return a {@link WorkplaceResponseDto} representing the found workplace.
     */
    WorkplaceResponseDto findWorkplaceById(int id);

    /**
     * Deletes a workplace by its ID.
     *
     * @param id the ID of the workplace to be deleted.
     * @return a {@link WorkplaceResponseDto} representing the deleted workplace.
     */
    WorkplaceResponseDto deleteWorkplace(int id);
}
