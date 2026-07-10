package com.hexticket.repository;

import com.hexticket.model.Seat;
import com.hexticket.model.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findByEventIdAndStatus(UUID eventId, SeatStatus status);
}
