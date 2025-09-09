package com.nomow.analytics.match_analytics_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomow.analytics.match_analytics_lite.model.Match;



public interface MatchRepository extends JpaRepository<Match, Long> {
}