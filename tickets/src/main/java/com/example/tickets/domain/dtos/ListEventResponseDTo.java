package com.example.tickets.domain.dtos;

import com.example.tickets.domain.entities.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Decide what i wanna to put into this List Event Response DTO
//Now I want to follow the principle of including the bare minimum information
//The less information that we're transferring less of a chance of any sensitive information leaking out
public class ListEventResponseDTo {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    //do we want to include the organizer information in our list event response dto I don't think that we do coz the user interface isn't going to need this .so, I'll not include it , the same fot attendees and staff we don't need information as we're not displaying it anywhere
    //What about Ticket Types ? I'll display ticket type information coz it's an important concept when it comes to an event .so, I should include it ,so we have a list of ticket in the entity Side so we should create a DTO for this ticket type following the same naming convention that we've used on the entity
    //ListEventTicketTypeResponseDto so we've prefixed list event because that's a particualar action , TicketTyoe is the particular entity that we're dealing with it and ResponseDTO tells us this is used on the response path in DTO use
    private List<ListEventTicketTypeResponseDto> ticketTypes  = new ArrayList<>(){};



}
