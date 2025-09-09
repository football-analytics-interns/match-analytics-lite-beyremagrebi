package com.nomow.analytics.match_analytics_lite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nomow.analytics.match_analytics_lite.dto.PlayerDto;
import com.nomow.analytics.match_analytics_lite.model.Event;
import com.nomow.analytics.match_analytics_lite.model.Player;
import com.nomow.analytics.match_analytics_lite.repository.EventRepository;
import com.nomow.analytics.match_analytics_lite.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final EventRepository eventRepository;

    public PlayerDto getPlayerSummary(Long playerId) {
        Player player = playerRepository.findById(playerId).orElse(null);
        if (player == null) return null;

        List<Event> events = eventRepository.findByPlayerId(playerId);

        int goals = (int) events.stream().filter(e -> "GOAL".equalsIgnoreCase(e.getType())).count();
        int assists = (int) events.stream()
                .filter(e -> e.getMeta() != null && e.getMeta().get("assistId") != null).count();

        double formRating = goals * 4 + assists * 3;

        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .team(player.getTeam())
                .position(player.getPosition())
                .goals(goals)
                .assists(assists)
                .formRating(formRating)
                .build();
    }
}
