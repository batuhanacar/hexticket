package com.hexticket.dto;

import com.hexticket.model.SeatStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class SeatResponse {
    private UUID id;
    private String seatNumber;
    private SeatStatus status;
}
