package com.hexticket.service;

import com.hexticket.dto.PurchaseRequest;
import com.hexticket.dto.SeatResponse;
import com.hexticket.model.Seat;
import com.hexticket.model.SeatStatus;
import com.hexticket.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final SeatRepository seatRepository;
    private final RedissonClient redissonClient;

    public List<SeatResponse> getAvailableSeats(UUID eventId) {
        return seatRepository.findByEventIdAndStatus(eventId, SeatStatus.AVAILABLE)
                .stream()
                .map(seat -> new SeatResponse(seat.getId(), seat.getSeatNumber(), seat.getStatus()))
                .toList();
    }

    @Transactional
    public void purchaseTicket(PurchaseRequest request) {
        String lockKey = "lock:seat:" + request.getSeatId();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean isLocked = lock.tryLock(0, 10, TimeUnit.SECONDS);

            if (!isLocked) {
                log.warn("Seat {} is currently being locked by another transaction", request.getSeatId());
                throw new RuntimeException("Seat is currently locked by another user. Please try again.");
            }

            Seat seat = seatRepository.findById(request.getSeatId())
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new RuntimeException("Seat is already sold or reserved");
            }

            seat.setStatus(SeatStatus.SOLD);
            seat.setUserId(request.getUserId());

            seatRepository.save(seat);

            log.info("Seat {} successfully purchased by User {}", request.getSeatId(), request.getUserId());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while acquiring lock", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}