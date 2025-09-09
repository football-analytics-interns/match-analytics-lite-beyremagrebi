package com.nomow.analytics.match_analytics_lite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomow.analytics.match_analytics_lite.dto.EventDto;
import com.nomow.analytics.match_analytics_lite.model.Event;
import com.nomow.analytics.match_analytics_lite.service.EventService;
import com.nomow.analytics.match_analytics_lite.util.ResponseHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/event")
    public ResponseEntity<?> addEvent(@RequestBody EventDto eventRequest) {
        try {
            Event event = eventService.addEvent(eventRequest);
            return ResponseEntity.ok(ResponseHelper.success("event", event));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.error(e.getMessage()));
        }
    }
}
