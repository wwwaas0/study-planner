package com.example.studyPlanner.domain.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoaordRepository {
    private final JdbcTemplate jdbcTemplate;
}
