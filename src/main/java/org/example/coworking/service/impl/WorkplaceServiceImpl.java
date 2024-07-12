package org.example.coworking.service.impl;

import org.example.coworking.domain.dto.request.WorkplaceRequestDto;
import org.example.coworking.domain.dto.response.WorkplaceResponseDto;
import org.example.coworking.domain.model.Workplace;
import org.example.coworking.exception.EntityNotFoundException;
import org.example.coworking.mapper.WorkplaceMapper;
import org.example.coworking.repository.WorkplaceRepository;
import org.example.coworking.service.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class WorkplaceServiceImpl implements WorkplaceService {
    private final WorkplaceRepository workplaceRepository;
    private final WorkplaceMapper workplaceMapper;

    @Autowired
    public WorkplaceServiceImpl(WorkplaceRepository workplaceRepository, WorkplaceMapper workplaceMapper) {
        this.workplaceRepository = workplaceRepository;
        this.workplaceMapper = workplaceMapper;
    }

    @Override
    public Collection<WorkplaceResponseDto> getWorkplaces() {
        Collection<Workplace> workplaces = workplaceRepository.getAllWorkplaces();
        return workplaceMapper.workplacesToWorkplaceResponseDtos(workplaces);
    }

    @Override
    public WorkplaceResponseDto addWorkplace(WorkplaceRequestDto workplaceRequestDto) {
        Workplace workplace = workplaceMapper.workplaceRequestDtotoWorkplace(workplaceRequestDto);
        workplaceRepository.addWorkplace(workplace);
        return workplaceMapper.workplaceToWorkplaceResponseDto(workplace);
    }

    @Override
    public WorkplaceResponseDto updateWorkplace(int id, WorkplaceRequestDto workplaceRequestDto) {
        Workplace workplace = workplaceRepository.findWorkplaceById(id)
                .orElseThrow(() -> new EntityNotFoundException("workplace", id));
        Workplace updatedWorkplace = workplaceMapper.workplaceRequestDtotoWorkplace(workplaceRequestDto);
        updatedWorkplace.setId(workplace.getId());
        workplaceRepository.updateWorkplace(updatedWorkplace);
        return workplaceMapper.workplaceToWorkplaceResponseDto(updatedWorkplace);
    }

    @Override
    public WorkplaceResponseDto findWorkplaceById(int id) {
        Workplace workplace = workplaceRepository.findWorkplaceById(id)
                .orElseThrow(() -> new EntityNotFoundException("workplace", id));
        return workplaceMapper.workplaceToWorkplaceResponseDto(workplace);
    }

    @Override
    public WorkplaceResponseDto deleteWorkplace(int id) {
        Workplace workplace = workplaceRepository.findWorkplaceById(id)
                .orElseThrow(() -> new EntityNotFoundException("workplace", id));
        workplaceRepository.removeWorkplaceById(id);
        return workplaceMapper.workplaceToWorkplaceResponseDto(workplace);
    }
}
