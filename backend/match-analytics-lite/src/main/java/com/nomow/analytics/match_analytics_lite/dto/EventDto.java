package com.nomow.analytics.match_analytics_lite.dto;

import java.util.Map;

import lombok.Data;

@Data
public class EventDto {
    private int minute;
    private String type;
    private Long playerId;
    private Map<String, Object> meta;
}
