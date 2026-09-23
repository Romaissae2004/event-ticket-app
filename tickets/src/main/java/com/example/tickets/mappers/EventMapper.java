package com.example.tickets.mappers;

import com.example.tickets.domain.CreateEventRequest;
import com.example.tickets.domain.CreateTicketTypeRequest;
import com.example.tickets.domain.UpdateEventRequest;
import com.example.tickets.domain.UpdateTicketTypeRequest;
import com.example.tickets.domain.dtos.*;
import com.example.tickets.domain.entities.Event;
import com.example.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    //They're the Response DTO's for our list event endpoint , we also have the mappers to take entities and convert them to these DTOs

    ListEventTicketTypeResponseDto toDto(TicketType ticketType);

    //ListEventResponseDTo toDTo(Event event); c'est la meme que ListEventResponseDTo toListEventResponseDTo(Event event); je la change par cela parce que j'ai déja fait toDto(Event event);
    ListEventResponseDTo toListEventResponseDTo(Event event);

    // we've introduced two new DTOs in order to control what we return from our get event endpoint also we've introduced two mapper functions to take the entites and convert them into those DTOs
    GetEventDetailsTicketTypesResponseDto toGetEventTicketTypesResponseDto(TicketType ticketType);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    //that return our PublishedEvent DTO
    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);

    GetPublishedEventDetailsTicketTypesResponseDto toGetPublishedEventDetailsTicketTypesResponseDto(TicketType ticketType);

    GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event event);

}
