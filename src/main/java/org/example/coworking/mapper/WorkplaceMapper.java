package org.example.coworking.mapper;

import org.example.coworking.domain.dto.response.WorkplaceResponseDto;
import org.example.coworking.domain.model.Workplace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {
//    Workplace workplaceRequestDtotoWorkplace(WorkplaceRequestDto workplaceRequestDto);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "available", target = "isAvailable")
    WorkplaceResponseDto workplaceToWorkplaceResponseDto(Workplace workplace);

    Collection<WorkplaceResponseDto> workplacesToWorkplaceResponseDtos(Collection<Workplace> workplaces);

    @Named("resultSetToWorkplace")
    default Workplace resultSetToWorkplace(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        Workplace workplace = new Workplace();
        workplace.setId(resultSet.getInt("id"));
        workplace.setName(resultSet.getString("name"));
        workplace.setAvailable(resultSet.getBoolean("is_available"));
        return workplace;
    }
}
