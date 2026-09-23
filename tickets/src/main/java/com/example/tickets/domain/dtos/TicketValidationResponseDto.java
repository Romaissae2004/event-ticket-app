package com.example.tickets.domain.dtos;

import com.example.tickets.domain.entities.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TicketValidationResponseDto {
    private UUID ticketId; // this is different from the Request where it could be a qrCodeId or a ticketId. The Response is definitely the ticketId
    private TicketValidationStatusEnum status; // Validation has been success or failure ? is it Valid or InValid ?



}
