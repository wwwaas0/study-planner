package com.example.studyPlanner.domain.board.service;

import com.example.studyPlanner.domain.board.repository.BoaordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoaordRepository boardRepository;
}
