package com.example.studyPlanner.board.service;

import com.example.studyPlanner.board.entity.Board;
import com.example.studyPlanner.board.repository.BoaordRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoaordRepository boardRepository;

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    public Board createBoard(String name) {
        Board board = Board.builder()
                .name(name)
                .build();

        boardRepository.save(board);

        return board;
    }

    @Transactional
    public Board updateBoard(Long boardId, String newName) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        Board board = null;
        if (boardOptional.isPresent()) {
            board = boardOptional.get();
            board.setName(newName);
            boardRepository.save(board);
        } else {
            throw new EntityNotFoundException(boardId + "번 게시판이 존재하지 않습니다.");
        }
        return board;
    }


    public String deleteBoard(Long boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        Board board = null;
        if (boardOptional.isPresent()) {
            board = boardOptional.get();
            boardRepository.delete(board);
        } else {
            throw new EntityNotFoundException(boardId + "번 게시판이 존재하지 않습니다.");
        }
        return boardId + "번 게시판이 정상적으로 삭제되었습니다.";
    }
}
