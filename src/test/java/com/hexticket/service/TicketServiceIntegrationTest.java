package com.hexticket.service;

import com.hexticket.dto.PurchaseRequest;
import com.hexticket.model.Event;
import com.hexticket.model.Seat;
import com.hexticket.model.SeatStatus;
import com.hexticket.repository.EventRepository;
import com.hexticket.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class TicketServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    void shouldPurchaseTicketSuccessfully() {
        Event event = new Event();
        event.setName("Techno Night");
        event.setDateTime(LocalDateTime.now());
        event = eventRepository.save(event);

        Seat seat = new Seat();
        seat.setEvent(event);
        seat.setSeatNumber("A1");
        seat.setStatus(SeatStatus.AVAILABLE);
        seat = seatRepository.save(seat);

        PurchaseRequest request = new PurchaseRequest();
        request.setUserId(UUID.randomUUID());
        request.setSeatId(seat.getId());

        ticketService.purchaseTicket(request);

        Seat updatedSeat = seatRepository.findById(seat.getId()).orElseThrow();
        assertThat(updatedSeat.getStatus()).isEqualTo(SeatStatus.SOLD);
        assertThat(updatedSeat.getUserId()).isEqualTo(request.getUserId());
    }
}