package com.example.tickets.controllers;

import com.example.tickets.domain.dtos.TicketValidationRequestDto;
import com.example.tickets.domain.dtos.TicketValidationResponseDto;
import com.example.tickets.domain.entities.TicketValidation;
import com.example.tickets.domain.entities.TicketValidationMethod;
import com.example.tickets.mappers.TicketValidationMapper;
import com.example.tickets.services.TicketValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/v1/ticket-validations")
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(
            @Valid @RequestBody TicketValidationRequestDto ticketValidationRequestDto){

        TicketValidationMethod method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;
        if(TicketValidationMethod.MANUAL.equals(method)) {
            ticketValidation = ticketValidationService.validateTicketManually(
                    ticketValidationRequestDto.getId());
        } else {
            ticketValidation = ticketValidationService.validateTicketByQrCode(
                    ticketValidationRequestDto.getId()
            );
        }
        return ResponseEntity.ok(
                ticketValidationMapper.toTicketValidationResponseDto(ticketValidation)
        );
    }


}
