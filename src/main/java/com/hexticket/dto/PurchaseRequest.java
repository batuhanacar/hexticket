package com.hexticket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Seat ID cannot be null")
    private Long seatId;
}
