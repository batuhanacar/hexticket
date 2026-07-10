package com.hexticket.controller;

import com.hexticket.dto.PurchaseRequest;
import com.hexticket.dto.SeatResponse;
import com.hexticket.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/purchase")
    public ResponseEntity<String> purchase(@Valid @RequestBody PurchaseRequest request) {
        ticketService.purchaseTicket(request);
        return ResponseEntity.ok("Ticket purchased successfully");
    }

    @GetMapping("/events/{eventId}/seats")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(@PathVariable UUID eventId) {
        return ResponseEntity.ok(ticketService.getAvailableSeats(eventId));
    }
}