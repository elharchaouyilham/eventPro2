package com.example.eventpro.controller;

import com.example.eventpro.dto.OrganizerRequestDto;
import com.example.eventpro.dto.OrganizerResponseDto;
import com.example.eventpro.service.OrganizerService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @PostMapping
    public ResponseEntity<OrganizerResponseDto> createOrganizer(
            @Valid @RequestBody OrganizerRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(organizerService.createOrganizer(request));
    }

    @GetMapping
    public List<OrganizerResponseDto> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }

    @GetMapping("/{id}")
    public OrganizerResponseDto getOrganizerById(@PathVariable Long id) {
        return organizerService.getOrganizerById(id);
    }
}
