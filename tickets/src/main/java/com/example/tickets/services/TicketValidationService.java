package com.example.tickets.services;

import com.example.tickets.domain.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {
    // diff ways that we can validate a ticket
    TicketValidation validateTicketByQrCode(UUID qrCodeId);
    TicketValidation validateTicketManually(UUID ticketId);
}
