package com.nomow.analytics.match_analytics_lite.seed;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nomow.analytics.match_analytics_lite.model.Event;
import com.nomow.analytics.match_analytics_lite.model.Match;
import com.nomow.analytics.match_analytics_lite.model.Player;
import com.nomow.analytics.match_analytics_lite.repository.EventRepository;
import com.nomow.analytics.match_analytics_lite.repository.MatchRepository;
import com.nomow.analytics.match_analytics_lite.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final EventRepository eventRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {

        if (matchRepository.count() > 0) {
            System.out.println("Database already seeded, skipping...");
            return;
        }

        Path jsonPath = Path.of("../seed/match.json");
        String jsonContent = Files.readString(jsonPath);
        JsonNode rootNode = objectMapper.readTree(jsonContent);

        JsonNode matchNode = rootNode.get("match");
        Match match = Match.builder()
                .homeTeam(matchNode.get("homeTeam").asText())
                .awayTeam(matchNode.get("awayTeam").asText())
                .homeScore(matchNode.get("homeScore").asInt())
                .awayScore(matchNode.get("awayScore").asInt())
                .date(ZonedDateTime.parse(matchNode.get("date").asText()))
                .build();
        matchRepository.save(match);

        Map<Long, Player> playerMap = new HashMap<>();

        for (JsonNode playerNode : rootNode.get("players")) {
            Player player = Player.builder()
                    .name(playerNode.get("name").asText())
                    .team(playerNode.get("team").asText())
                    .position(playerNode.get("position").asText())
                    .build();
            playerRepository.save(player);
            playerMap.put(playerNode.get("id").asLong(), player); // map JSON ID to saved Player
        }

        List<Event> events = new ArrayList<>();
        for (JsonNode eventNode : rootNode.get("events")) {

            Long playerId = eventNode.get("playerId").asLong();
            Player player = playerMap.get(playerId); // get the saved Player

            Event event = Event.builder()
                    .minute(eventNode.get("minute").asInt())
                    .type(eventNode.get("type").asText()) // directly use string
                    .player(player)
                    .meta(objectMapper.convertValue(eventNode.get("meta"), Map.class))
                    .build();

            events.add(event);
        }
        eventRepository.saveAll(events);

        System.out.println("Database seeded successfully!");
    }

}
