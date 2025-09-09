package com.nomow.analytics.match_analytics_lite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomow.analytics.match_analytics_lite.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByPlayerId(Long playerId);
}