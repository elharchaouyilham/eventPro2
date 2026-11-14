package com.example.eventpro.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
}
