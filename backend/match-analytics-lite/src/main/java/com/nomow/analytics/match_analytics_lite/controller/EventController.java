package com.nomow.analytics.match_analytics_lite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomow.analytics.match_analytics_lite.dto.EventDto;
import com.nomow.analytics.match_analytics_lite.model.Event;
import com.nomow.analytics.match_analytics_lite.model.Player;
import com.nomow.analytics.match_analytics_lite.repository.EventRepository;
import com.nomow.analytics.match_analytics_lite.repository.PlayerRepository;
import com.nomow.analytics.match_analytics_lite.util.ResponseHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final PlayerRepository playerRepository;

    @PostMapping("/event")
    public ResponseEntity<?> addEvent(@RequestBody EventDto eventRequest) {

        Player player = playerRepository.findById(eventRequest.getPlayerId())
                .orElse(null);
        if (player == null) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.error("Player not found with ID: " + eventRequest.getPlayerId()));
        }

        Event event = Event.builder()
                .minute(eventRequest.getMinute())
                .type(eventRequest.getType()) 
                .player(player)
                .meta(eventRequest.getMeta())
                .build();

        eventRepository.save(event);

        return ResponseEntity.ok(ResponseHelper.success("event", event));
    }
}
