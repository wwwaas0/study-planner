package com.example.studyPlanner.board.service;

import com.example.studyPlanner.board.dto.GetBoardListRes;
import com.example.studyPlanner.board.entity.Board;
import com.example.studyPlanner.board.mapper.BoardMapper;
import com.example.studyPlanner.board.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    public List<GetBoardListRes> getBoardNames() {
        List<Board> boards = getBoards();

        List<GetBoardListRes> boardListRes = boards.stream()
                .map(board -> BoardMapper.INSTANCE.toDTO(board))
                .collect(Collectors.toList());
        return boardListRes;
    }

    public void createBoard(String name) {
        Board board = Board.builder()
                .name(name)
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(Long boardId, String newName) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(boardId + "번 게시판이 존재하지 않습니다."));
        board.setName(newName);

        boardRepository.save(board);
    }


    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(boardId + "번 게시판이 존재하지 않습니다."));

        boardRepository.delete(board);
    }
}
