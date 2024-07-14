package org.example.coworking.mapper;

import org.example.coworking.domain.dto.request.ConferenceHallRequestDto;
import org.example.coworking.domain.dto.response.ConferenceHallResponseDto;
import org.example.coworking.domain.model.ConferenceHall;
import org.mapstruct.Mapper;
import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ConferenceHallMapper {

    ConferenceHall conferenceHallRequestDtotoConferenceHall(ConferenceHallRequestDto conferenceHallRequestDto);

    ConferenceHallResponseDto conferenceHallToConferenceHallResponseDto(ConferenceHall conferenceHall);

    Collection<ConferenceHallResponseDto> conferenceHallsToConferenceHallResponseDtos(Collection<ConferenceHall> conferenceHalls);

}
