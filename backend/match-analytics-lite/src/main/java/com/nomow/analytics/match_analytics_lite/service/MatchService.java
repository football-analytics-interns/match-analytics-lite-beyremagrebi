package com.nomow.analytics.match_analytics_lite.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nomow.analytics.match_analytics_lite.model.Event;
import com.nomow.analytics.match_analytics_lite.model.Match;
import com.nomow.analytics.match_analytics_lite.model.Player;
import com.nomow.analytics.match_analytics_lite.repository.EventRepository;
import com.nomow.analytics.match_analytics_lite.repository.MatchRepository;
import com.nomow.analytics.match_analytics_lite.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final EventRepository eventRepository;

    public Map<String, Object> getMatchData() {
        Match match = matchRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        if (match == null) {
            return null;
        }

        List<Player> players = playerRepository.findAll();
        List<Event> events = eventRepository.findAll();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("match", match);
        data.put("players", players);
        data.put("events", events);

        return data;
    }
}
