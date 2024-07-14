package org.example.coworking.mapper;

import org.example.coworking.domain.dto.request.WorkplaceRequestDto;
import org.example.coworking.domain.dto.response.WorkplaceResponseDto;
import org.example.coworking.domain.model.Workplace;
import org.mapstruct.Mapper;
import java.util.Collection;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {

    Workplace workplaceRequestDtotoWorkplace(WorkplaceRequestDto workplaceRequestDto);

    WorkplaceResponseDto workplaceToWorkplaceResponseDto(Workplace workplace);

    Collection<WorkplaceResponseDto> workplacesToWorkplaceResponseDtos(Collection<Workplace> workplaces);

}
