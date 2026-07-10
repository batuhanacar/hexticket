package com.hexticket.service;

import com.hexticket.dto.TicketPurchasedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketConsumer {

    @KafkaListener(topics = "ticket-purchases", groupId = "hexticket-group")
    public void listen(TicketPurchasedEvent event) {
        log.info("Processing ticket for user: {} and seat: {}", event.getUserId(), event.getSeatId());
    }
}