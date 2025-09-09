package com.nomow.analytics.match_analytics_lite.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomow.analytics.match_analytics_lite.service.MatchService;
import com.nomow.analytics.match_analytics_lite.util.ResponseHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/match")
    public ResponseEntity<?> getMatch() {
        Map<String, Object> data = matchService.getMatchData();

        if (data == null) {
            return ResponseEntity.status(404)
                    .body(ResponseHelper.error("No match found"));
        }

        return ResponseEntity.ok(ResponseHelper.success("data", data));
    }
}
