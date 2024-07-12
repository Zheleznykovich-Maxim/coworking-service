package org.example.coworking.mapper;

import org.example.coworking.domain.dto.request.ConferenceHallRequestDto;
import org.example.coworking.domain.dto.response.ConferenceHallResponseDto;
import org.example.coworking.domain.model.ConferenceHall;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Mapper
public interface ConferenceHallMapper {

    ConferenceHall conferenceHallRequestDtotoConferenceHall(ConferenceHallRequestDto conferenceHallRequestDto);

    ConferenceHallResponseDto conferenceHallToConferenceHallResponseDto(ConferenceHall conferenceHall);

    Collection<ConferenceHallResponseDto> conferenceHallsToConferenceHallResponseDtos(Collection<ConferenceHall> conferenceHalls);

    @Named("resultSetToConferenceHall")
    default ConferenceHall resultSetToConferenceHall(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        ConferenceHall conferenceHall = new ConferenceHall();
        conferenceHall.setId(resultSet.getInt("id"));
        conferenceHall.setName(resultSet.getString("name"));
        conferenceHall.setAvailable(resultSet.getBoolean("is_available"));
        return conferenceHall;
    }
}
