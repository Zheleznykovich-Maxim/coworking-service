package org.example.coworking.service;

import org.example.coworking.domain.dto.request.ConferenceHallRequestDto;
import org.example.coworking.domain.dto.response.ConferenceHallResponseDto;
import java.util.Collection;

public interface ConferenceHallService {

    Collection<ConferenceHallResponseDto> getConferenceHalls();

    ConferenceHallResponseDto addConferenceHall(ConferenceHallRequestDto conferenceHallRequestDto);

    ConferenceHallResponseDto updateConferenceHall(int id, ConferenceHallRequestDto conferenceHallRequestDto);

    ConferenceHallResponseDto findConferenceHallById(int id);

    ConferenceHallResponseDto deleteConferenceHall(int id);
}
