package com.hexticket.service;

import com.hexticket.dto.EventRequest;
import com.hexticket.dto.EventResponse;
import com.hexticket.model.Event;
import com.hexticket.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventResponse createEvent(EventRequest request) {
        Event event = new Event();
        event.setName(request.getName());
        event.setDateTime(request.getDateTime());
        Event savedEvent = eventRepository.save(event);
        return EventResponse.builder()
                .id(savedEvent.getId())
                .name(savedEvent.getName())
                .dateTime(savedEvent.getDateTime())
                .build();
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(e -> EventResponse.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .dateTime(e.getDateTime())
                        .build())
                .collect(Collectors.toList());
    }
}