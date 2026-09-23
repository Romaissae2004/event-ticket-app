package com.example.tickets.domain.dtos;

import com.example.tickets.domain.entities.TicketValidationMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketValidationRequestDto {
    private UUID id; //this is could be represented the qrCodeId or the ticketId depending on which validation method we're using
    private TicketValidationMethod method ;
}
