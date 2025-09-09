package com.nomow.analytics.match_analytics_lite.service;

import org.springframework.stereotype.Service;

import com.nomow.analytics.match_analytics_lite.dto.EventDto;
import com.nomow.analytics.match_analytics_lite.model.Event;
import com.nomow.analytics.match_analytics_lite.model.Player;
import com.nomow.analytics.match_analytics_lite.repository.EventRepository;
import com.nomow.analytics.match_analytics_lite.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final PlayerRepository playerRepository;

    public Event addEvent(EventDto eventRequest) {
        Player player = playerRepository.findById(eventRequest.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Player not found with ID: " + eventRequest.getPlayerId()));

        Event event = Event.builder()
                .minute(eventRequest.getMinute())
                .type(eventRequest.getType())
                .player(player)
                .meta(eventRequest.getMeta())
                .build();

        return eventRepository.save(event);
    }
}
