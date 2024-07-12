package org.example.coworking.service.impl;

import org.example.coworking.domain.dto.request.ConferenceHallRequestDto;
import org.example.coworking.domain.dto.response.ConferenceHallResponseDto;
import org.example.coworking.domain.model.ConferenceHall;
import org.example.coworking.exception.EntityNotFoundException;
import org.example.coworking.mapper.ConferenceHallMapper;
import org.example.coworking.repository.ConferenceHallRepository;
import org.example.coworking.service.ConferenceHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ConferenceHallServiceImpl implements ConferenceHallService {
    private final ConferenceHallRepository conferenceHallRepository;
    private final ConferenceHallMapper conferenceHallMapper;

    @Autowired
    public ConferenceHallServiceImpl(ConferenceHallRepository conferenceHallRepository, ConferenceHallMapper conferenceHallMapper) {
        this.conferenceHallRepository = conferenceHallRepository;
        this.conferenceHallMapper = conferenceHallMapper;
    }

    @Override
    public Collection<ConferenceHallResponseDto> getConferenceHalls() {
        Collection<ConferenceHall> conferenceHalls = conferenceHallRepository.getAllConferenceHalls();
        return conferenceHallMapper.conferenceHallsToConferenceHallResponseDtos(conferenceHalls);
    }

    @Override
    public ConferenceHallResponseDto addConferenceHall(ConferenceHallRequestDto conferenceHallRequestDto) {
        ConferenceHall conferenceHall = conferenceHallMapper.conferenceHallRequestDtotoConferenceHall(conferenceHallRequestDto);
        conferenceHallRepository.addConferenceHall(conferenceHall);
        return conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall);
    }

    @Override
    public ConferenceHallResponseDto updateConferenceHall(int id, ConferenceHallRequestDto conferenceHallRequestDto) {
        ConferenceHall conferenceHall = conferenceHallRepository.findConferenceById(id)
                .orElseThrow(() -> new EntityNotFoundException("conferenceHall", id));
        ConferenceHall updatedConferenceHall = conferenceHallMapper.conferenceHallRequestDtotoConferenceHall(conferenceHallRequestDto);
        updatedConferenceHall.setId(conferenceHall.getId());
        conferenceHallRepository.updateConferenceHall(updatedConferenceHall);
        return conferenceHallMapper.conferenceHallToConferenceHallResponseDto(updatedConferenceHall);
    }

    @Override
    public ConferenceHallResponseDto findConferenceHallById(int id) {
        ConferenceHall conferenceHall = conferenceHallRepository.findConferenceById(id)
                .orElseThrow(() -> new EntityNotFoundException("conferenceHall", id));
        return conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall);
    }

    @Override
    public ConferenceHallResponseDto deleteConferenceHall(int id) {
        ConferenceHall conferenceHall = conferenceHallRepository.findConferenceById(id)
                .orElseThrow(() -> new EntityNotFoundException("workplace", id));
        conferenceHallRepository.removeConferenceHallById(id);
        return conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall);
    }
}
