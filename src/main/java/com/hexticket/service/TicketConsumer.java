package com.hexticket.service;

import com.hexticket.dto.TicketPurchasedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketConsumer {

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000)
    )
    @KafkaListener(topics = "ticket-purchases", groupId = "hexticket-group")
    public void listen(TicketPurchasedEvent event) {
        log.info("Processing ticket for user: {} and seat: {}", event.getUserId(), event.getSeatId());
        log.info("Ticket successfully processed for user: {}", event.getUserId());
    }
}