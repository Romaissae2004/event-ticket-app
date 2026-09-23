package com.example.tickets.mappers;

import com.example.tickets.domain.dtos.TicketValidationRequestDto;
import com.example.tickets.domain.dtos.TicketValidationResponseDto;
import com.example.tickets.domain.entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface TicketValidationMapper {

    //Because of the Ticket Validation Entity doesn't Have concept of a Ticket with an id (ticketId) so we need to add a mapping where the target is ticketId and source = "ticketValidation.ticket.id"

    @Mapping(target="ticketId" , source = "ticket.id")
    TicketValidationResponseDto toTicketValidationResponseDto(TicketValidation ticketValidation);
}
