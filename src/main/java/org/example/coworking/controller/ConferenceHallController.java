package org.example.coworking.controller;

import org.example.coworking.domain.dto.request.ConferenceHallRequestDto;
import org.example.coworking.domain.dto.response.ConferenceHallResponseDto;
import org.example.coworking.service.ConferenceHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/conferenceHall")
public class ConferenceHallController {
    private final ConferenceHallService conferenceHallService;

    @Autowired
    public ConferenceHallController(ConferenceHallService conferenceHallService) {
        this.conferenceHallService = conferenceHallService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ConferenceHallResponseDto>> getWorkplaces() {
        Collection<ConferenceHallResponseDto> workplaceResponseDtos = this.conferenceHallService.getConferenceHalls();
        return ResponseEntity.ok(workplaceResponseDtos);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConferenceHallResponseDto> getWorkplaceById(@PathVariable int id) {
        ConferenceHallResponseDto conferenceHallResponseDto = conferenceHallService.findConferenceHallById(id);
        return ResponseEntity.ok(conferenceHallResponseDto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConferenceHallResponseDto> createWorkplace(@RequestBody ConferenceHallRequestDto workplaceRequestDto) {
        ConferenceHallResponseDto workplaceResponseDto = conferenceHallService.addConferenceHall(workplaceRequestDto);
        return ResponseEntity.status(201).body(workplaceResponseDto);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConferenceHallResponseDto> updateWorkplace(@PathVariable int id,
                                                                @RequestBody ConferenceHallRequestDto workplaceRequestDto) {
        ConferenceHallResponseDto workplaceResponseDto = conferenceHallService.updateConferenceHall(id, workplaceRequestDto);
        return ResponseEntity.ok(workplaceResponseDto);

    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConferenceHallResponseDto> deleteWorkplace(@PathVariable int id) {
        ConferenceHallResponseDto workplaceResponseDto = conferenceHallService.deleteConferenceHall(id);
        return ResponseEntity.ok(workplaceResponseDto);
    }
}
