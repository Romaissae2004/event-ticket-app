package com.example.tickets.services.Impl;

import com.example.tickets.domain.CreateEventRequest;
import com.example.tickets.domain.UpdateEventRequest;
import com.example.tickets.domain.UpdateTicketTypeRequest;
import com.example.tickets.domain.entities.Event;
import com.example.tickets.domain.entities.EventStatusEnum;
import com.example.tickets.domain.entities.TicketType;
import com.example.tickets.domain.entities.User;
import com.example.tickets.exceptions.EventNotFoundException;
import com.example.tickets.exceptions.EventUpdateException;
import com.example.tickets.exceptions.TicketTypeNotFoundException;
import com.example.tickets.exceptions.UserNotFoundException;
import com.example.tickets.repositories.UserRepository;
import com.example.tickets.repositories.EventRepository;
import com.example.tickets.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    //Créer un Event avec ses TicketTypes, puis l'enregistrer dans la base de données.
    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        // Chercher l'organisateur
        User organizer = userRepository.findById(organizerId).orElseThrow(() -> new UserNotFoundException(String.format("User with ID '%s' not found", organizerId)));
        // Créer un Event vide
        Event eventToCreate = new Event();
        // Créer les TicketTypes we list our ticket type to create
        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    ticketTypeToCreate.setEvent(eventToCreate);
                    return ticketTypeToCreate;
                }).toList();
        // Remplir l'Event
        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        // Associer l'organisateur
        eventToCreate.setOrganizer(organizer);
        // Associer les tickets
        eventToCreate.setTicketTypes(ticketTypesToCreate);
        // Sauvegarder en base de données
        return eventRepository.save(eventToCreate);

    }

    @Override
    public Page<Event> listEventsForOrgaizers(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> GetEventForOrganizer(UUID organzierId, UUID id) {
        return eventRepository.findByIdAndOrganizerId(id, organzierId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event) {
        //the first thing that we should check is whether or not the event has an ID
        if (null == event.getId()) {
            throw new EventUpdateException("Event Id cannot be null");
        }
        //now we know that the event has an ID = id on the event Let's now make sure that matches to this id = id argument (l'id qui est argument dans la méthode) coz if not,the user is trying to update the ID of the event
        if (!id.equals(event.getId())) {
            throw new EventUpdateException("Cannot update the ID of an event");
        }
        //now we know that the event has an ID and that it matches this value of the ID argument up here
        //now let's go about getting the existing event from the Database
        Event existingEvent = eventRepository
                .findByIdAndOrganizerId(id, organizerId)
                .orElseThrow(() -> new EventNotFoundException(String.format("Event with ID '%s' does not exist", id))
                );

        //now we've an existingEvent , starting updating the existing event with information from the update event request
        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setStatus(event.getStatus());
        // we're not going to update the organizers or the attendees or the staff
        //implementing the TicketType update

        // Is a set of all the Ids of a ticket types that we want to create excluding any nulls
        Set<UUID> requestTicketTypeIds = event.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull) //Filter where we've some nulls ID
                .collect(Collectors.toSet()); // we collect to a set

        //let's remove any tickettypes from the existingevent where the id isn't in this set
        existingEvent.getTicketTypes().removeIf(existingTicketType ->
                !requestTicketTypeIds.contains(existingTicketType.getId())
        );

        Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        for (UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
            if (null == ticketType.getId()) {
                // Create
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);

            } else if (existingTicketTypesIndex.containsKey(ticketType.getId())) {
                // Update
                TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId()); //reference to the existing ticket type
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
            } else {
                throw new TicketTypeNotFoundException(String.format(
                        "Ticket type with ID '%s' does not exist", ticketType.getId()
                ));
            }
        }

        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {
        GetEventForOrganizer(organizerId,id).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query,pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID id) {
        return eventRepository.findByIdAndStatus(id,EventStatusEnum.PUBLISHED);
    }


}
