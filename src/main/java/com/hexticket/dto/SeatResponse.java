package com.hexticket.dto;

import com.hexticket.model.SeatStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SeatResponse {
    private Long id;
    private String seatNumber;
    private SeatStatus status;
}
