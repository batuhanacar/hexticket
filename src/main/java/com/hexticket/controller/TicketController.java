package com.hexticket.controller;

import com.hexticket.dto.PurchaseRequest;
import com.hexticket.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}