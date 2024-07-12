package org.example.coworking.controller;

import org.example.coworking.domain.dto.request.WorkplaceRequestDto;
import org.example.coworking.domain.dto.response.WorkplaceResponseDto;
import org.example.coworking.service.WorkplaceService;
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
@RequestMapping("/workplace")
public class WorkplaceController {
    private final WorkplaceService workplaceService;

    @Autowired
    public WorkplaceController(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<WorkplaceResponseDto>> getWorkplaces() {
        Collection<WorkplaceResponseDto> workplaceResponseDtos = this.workplaceService.getWorkplaces();
        return ResponseEntity.ok(workplaceResponseDtos);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkplaceResponseDto> getWorkplaceById(@PathVariable int id) {
        WorkplaceResponseDto workplaceResponseDto = workplaceService.findWorkplaceById(id);
        return ResponseEntity.ok(workplaceResponseDto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkplaceResponseDto> createWorkplace(@RequestBody WorkplaceRequestDto workplaceRequestDto) {
        WorkplaceResponseDto workplaceResponseDto = workplaceService.addWorkplace(workplaceRequestDto);
        return ResponseEntity.status(201).body(workplaceResponseDto);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkplaceResponseDto> updateWorkplace(@PathVariable int id,
                                                                @RequestBody WorkplaceRequestDto workplaceRequestDto) {
        WorkplaceResponseDto workplaceResponseDto = workplaceService.updateWorkplace(id, workplaceRequestDto);
        return ResponseEntity.ok(workplaceResponseDto);

    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkplaceResponseDto> deleteWorkplace(@PathVariable int id) {
        WorkplaceResponseDto workplaceResponseDto = workplaceService.deleteWorkplace(id);
        return ResponseEntity.ok(workplaceResponseDto);
    }
}
