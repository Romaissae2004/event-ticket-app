package com.example.tickets.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListEventTicketTypeResponseDto {
    private UUID id;
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;
    // should I include the event ? no, coz: we're already nesting this ticket type in a list event response dto so it doesn't make sense to include the birectional relationship to event
    // what about ticket no I don't think that we do,when we're listing out all of the events,we just want to know about the events and a stretch(disponibles) the ticket types,we don't want to know about the tickets the associated with the ticket types
    // I'll include created_at & updated_at coz are a useful metadata but following the principle of only returning the bare minium information I think we should exlude them I can also add them later

}
