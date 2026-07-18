package com.hexticket.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TicketResponse {
    private UUID id;
    private UUID eventId;
    private String seatNumber;
    private LocalDateTime purchaseDate;
}