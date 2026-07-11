package com.hexticket.exception;

public class SeatAlreadySoldException extends RuntimeException {
    public SeatAlreadySoldException(String message) {
        super(message);
    }
}
