package com.example.eventpro.service;

import com.example.eventpro.dto.OrganizerRequestDto;
import com.example.eventpro.dto.OrganizerResponseDto;
import com.example.eventpro.entity.Organizer;
import com.example.eventpro.exception.ResourceNotFoundException;
import com.example.eventpro.repository.OrganizerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class OrganizerService {

    private final OrganizerRepository organizerRepository;

    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public OrganizerResponseDto createOrganizer(
            OrganizerRequestDto request) {

        String email = normalizeEmail(request.getEmail());

        if (organizerRepository.existsByEmail(email)) {
            throw new IllegalStateException(
                    "Un organisateur avec cet email existe déjà.");
        }

        Organizer organizer = new Organizer();
        organizer.setName(request.getName().trim());
        organizer.setEmail(email);
        organizer.setOrganisationName(
                request.getOrganisationName().trim());

        return toResponse(organizerRepository.save(organizer));
    }

    @Transactional(readOnly = true)
    public List<OrganizerResponseDto> getAllOrganizers() {
        return organizerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrganizerResponseDto getOrganizerById(Long id) {

        Organizer organizer = organizerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Organisateur introuvable : " + id));

        return toResponse(organizer);
    }

    private OrganizerResponseDto toResponse(Organizer organizer) {
        return OrganizerResponseDto.builder()
                .id(organizer.getId())
                .name(organizer.getName())
                .email(organizer.getEmail())
                .organisationName(organizer.getOrganisationName())
                .build();
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
