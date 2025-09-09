package com.nomow.analytics.match_analytics_lite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nomow.analytics.match_analytics_lite.dto.PlayerDto;
import com.nomow.analytics.match_analytics_lite.model.Event;
import com.nomow.analytics.match_analytics_lite.model.Player;
import com.nomow.analytics.match_analytics_lite.repository.EventRepository;
import com.nomow.analytics.match_analytics_lite.repository.PlayerRepository;
import com.nomow.analytics.match_analytics_lite.util.ResponseHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final EventRepository eventRepository;

    @GetMapping("/api/player/{id}")
    public ResponseEntity<?> getPlayerSummary(@PathVariable Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player == null) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.error("Player not found with ID: " + id));
        }


        List<Event> events = eventRepository.findByPlayerId(id);

        int goals = (int) events.stream().filter(e -> "GOAL".equalsIgnoreCase(e.getType())).count();
        int assists = (int) events.stream()
                .filter(e -> e.getMeta() != null && e.getMeta().get("assistId") != null).count();


        double formRating = goals * 4 + assists * 3;

        PlayerDto summary = PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .team(player.getTeam())
                .position(player.getPosition())
                .goals(goals)
                .assists(assists)
                .formRating(formRating)
                .build();

        return ResponseEntity.ok(ResponseHelper.success("player", summary));
    }
}
