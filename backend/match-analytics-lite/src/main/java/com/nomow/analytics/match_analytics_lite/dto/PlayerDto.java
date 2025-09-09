package com.nomow.analytics.match_analytics_lite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDto {
    private Long id;
    private String name;
    private String team;
    private String position;
    private int goals;
    private int assists;
    private double formRating;
}
