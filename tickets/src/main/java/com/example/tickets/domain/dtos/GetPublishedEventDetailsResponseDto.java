package com.example.tickets.domain.dtos;

import com.example.tickets.domain.entities.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetPublishedEventDetailsResponseDto {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    //don't need status coz all the event are PUBLISHED
    private List<GetPublishedEventDetailsTicketTypesResponseDto> ticketTypes  = new ArrayList<>(){};
}
