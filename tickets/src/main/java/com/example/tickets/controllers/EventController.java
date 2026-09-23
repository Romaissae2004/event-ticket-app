package com.example.tickets.controllers;

import com.example.tickets.domain.CreateEventRequest;
import com.example.tickets.domain.UpdateEventRequest;
import com.example.tickets.domain.dtos.*;
import com.example.tickets.domain.entities.Event;
import com.example.tickets.mappers.EventMapper;
import com.example.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.tickets.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path ="/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    //Inject so that we can have access to EventMapper and EventService
    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId =  parseUserId(jwt);

        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDTo>> ListEvents(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable) {
        UUID userId =  parseUserId(jwt);
        // A page of events : returns a page of event entity
        Page<Event> events = eventService.listEventsForOrgaizers(userId, pageable);
        //we want now a page of list event response DTO , ok return an http 200
        return ResponseEntity.ok(events.map(event -> eventMapper.toListEventResponseDTo(event)));
    }

    @GetMapping(path ="/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
            ) {
        UUID userId =  parseUserId(jwt);
        // eventId taking it from the path
        return eventService.GetEventForOrganizer(userId, eventId)
                .map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        //we want now a page of list event response DTO , ok return an http 200
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto) {
        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);
        UUID userId = parseUserId(jwt);

        Event updatedEvent = eventService.updateEventForOrganizer(
                userId, eventId, updateEventRequest
        );

        UpdateEventResponseDto updateEventResponseDto = eventMapper.toUpdateEventResponseDto(
                updatedEvent);

        return ResponseEntity.ok(updateEventResponseDto);
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId){
        UUID userId = parseUserId(jwt);
        eventService.deleteEventForOrganizer(userId,eventId);
        return ResponseEntity.noContent().build();
    }

    //méthode that returns a UUID
    /*private UUID parseUserId(Jwt jwt){
        return UUID.fromString(jwt.getSubject());
    }*/
}
