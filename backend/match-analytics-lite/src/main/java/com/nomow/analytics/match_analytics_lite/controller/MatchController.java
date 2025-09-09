package com.nomow.analytics.match_analytics_lite.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomow.analytics.match_analytics_lite.model.Event;
import com.nomow.analytics.match_analytics_lite.model.Match;
import com.nomow.analytics.match_analytics_lite.model.Player;
import com.nomow.analytics.match_analytics_lite.repository.EventRepository;
import com.nomow.analytics.match_analytics_lite.repository.MatchRepository;
import com.nomow.analytics.match_analytics_lite.repository.PlayerRepository;
import com.nomow.analytics.match_analytics_lite.util.ResponseHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatchController {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final EventRepository eventRepository;

@GetMapping("/match")
public ResponseEntity<?> getMatch() {

    Match match = matchRepository.findAll()
            .stream()
            .findFirst()
            .orElse(null);

    if (match == null) {
        return ResponseEntity.status(404)
                .body(ResponseHelper.error("No match found"));
    }

    List<Player> players = playerRepository.findAll();
    List<Event> events = eventRepository.findAll();

    Map<String, Object> data = new LinkedHashMap<>(); 
    data.put("match", match);   
    data.put("players", players); 
    data.put("events", events);  

    return ResponseEntity.ok(ResponseHelper.success("data", data));
}

}
