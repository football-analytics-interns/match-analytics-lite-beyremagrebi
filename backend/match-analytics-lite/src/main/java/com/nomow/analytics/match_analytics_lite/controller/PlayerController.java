package com.nomow.analytics.match_analytics_lite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nomow.analytics.match_analytics_lite.dto.PlayerDto;
import com.nomow.analytics.match_analytics_lite.service.PlayerService;
import com.nomow.analytics.match_analytics_lite.util.ResponseHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/api/player/{id}")
    public ResponseEntity<?> getPlayerSummary(@PathVariable Long id) {
        PlayerDto summary = playerService.getPlayerSummary(id);

        if (summary == null) {
            return ResponseEntity.badRequest()
                    .body(ResponseHelper.error("Player not found with ID: " + id));
        }

        return ResponseEntity.ok(ResponseHelper.success("player", summary));
    }
}
