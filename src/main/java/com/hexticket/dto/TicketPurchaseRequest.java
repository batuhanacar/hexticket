package com.hexticket.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class TicketPurchaseRequest {
    private UUID eventId;
    private String seatNumber;
}