package org.example.coworking.service;

import org.example.coworking.domain.dto.request.WorkplaceRequestDto;
import org.example.coworking.domain.dto.response.WorkplaceResponseDto;
import java.util.Collection;

public interface WorkplaceService {

    Collection<WorkplaceResponseDto> getWorkplaces();

    WorkplaceResponseDto addWorkplace(WorkplaceRequestDto workplaceRequestDto);

    WorkplaceResponseDto updateWorkplace(int id, WorkplaceRequestDto workplaceRequestDto);

    WorkplaceResponseDto findWorkplaceById(int id);

    WorkplaceResponseDto deleteWorkplace(int id);
}
