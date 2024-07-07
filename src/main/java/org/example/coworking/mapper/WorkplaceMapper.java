package org.example.coworking.mapper;

import org.example.coworking.dto.request.WorkplaceRequestDto;
import org.example.coworking.dto.response.WorkplaceResponseDto;
import org.example.coworking.model.Workplace;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Mapper
public interface WorkplaceMapper {
    Workplace workplaceRequestDtotoWorkplace(WorkplaceRequestDto workplaceRequestDto);
    WorkplaceResponseDto workplaceToWorkplaceResponseDto(Workplace workplace);
    Collection<WorkplaceResponseDto> worplacesToWorkplaceResponseDtos(Collection<Workplace> workplaces);

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
