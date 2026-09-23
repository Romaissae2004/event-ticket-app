package com.example.tickets.controllers;

import com.example.tickets.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.example.tickets.domain.dtos.ListPublishedEventResponseDto;
import com.example.tickets.domain.entities.Event;
import com.example.tickets.mappers.EventMapper;
import com.example.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor //which allows to create constructor easily
public class PublishedEventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    //it's a public endpoint
    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvent(
            @RequestParam(required= false) String q,
            Pageable pageable
    ) {
        Page<Event>events;
        if(null != q && !q.trim().isEmpty()) {
            events = eventService.searchPublishedEvents(q,pageable);
        }else{
            events=eventService.listPublishedEvents(pageable);
        }
        return ResponseEntity.ok(
                events.map(eventMapper::toListPublishedEventResponseDto)
                );
    }

    // it's a public endpoint
    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventDetails(
            @PathVariable UUID eventId
    ) {
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}
