package com.nomow.analytics.match_analytics_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomow.analytics.match_analytics_lite.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
