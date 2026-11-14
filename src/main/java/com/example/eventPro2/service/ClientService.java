package com.example.eventpro.service;

import com.example.eventpro.dto.ClientRequestDto;
import com.example.eventpro.dto.ClientResponseDto;
import com.example.eventpro.entity.Client;
import com.example.eventpro.exception.ResourceNotFoundException;
import com.example.eventpro.repository.ClientRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponseDto createClient(ClientRequestDto request) {

        String email = normalizeEmail(request.getEmail());

        if (clientRepository.existsByEmail(email)) {
            throw new IllegalStateException(
                    "Un client avec cet email existe déjà.");
        }

        Client client = new Client();
        client.setName(request.getName().trim());
        client.setEmail(email);
        client.setPhone(request.getPhone().trim());

        return toResponse(clientRepository.save(client));
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDto> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClientResponseDto getClientById(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Client introuvable : " + id));

        return toResponse(client);
    }

    private ClientResponseDto toResponse(Client client) {
        return ClientResponseDto.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .build();
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
