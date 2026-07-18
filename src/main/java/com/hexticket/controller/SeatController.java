package com.hexticket.controller;

import com.hexticket.model.Seat;
import com.hexticket.model.SeatStatus;
import com.hexticket.repository.SeatRepository;
import com.hexticket.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;

    @PostMapping("/add")
    public String addSeat(@RequestParam UUID eventId, @RequestParam String seatNumber) {
        var event = eventRepository.findById(eventId).orElseThrow();
        Seat seat = Seat.builder()
                .seatNumber(seatNumber)
                .status(SeatStatus.AVAILABLE)
                .event(event)
                .build();
        seatRepository.save(seat);
        return "Seat added: " + seatNumber;
    }
}