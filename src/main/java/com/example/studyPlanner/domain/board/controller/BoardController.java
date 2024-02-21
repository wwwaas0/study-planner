package com.example.studyPlanner.domain.board.controller;

import com.example.studyPlanner.domain.board.dto.GetBoardListRes;
import com.example.studyPlanner.domain.board.entity.Board;
import com.example.studyPlanner.domain.board.mapper.BoardMapper;
import com.example.studyPlanner.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping()
    public List<GetBoardListRes> getBoards() {
        List<Board> boards = boardService.getBoards();

        List<GetBoardListRes> boardListRes = boards.stream()
                .map(board -> BoardMapper.INSTANCE.toDTO(board))
                .collect(Collectors.toList());

        return boardListRes;
    }

    @PostMapping()
    public Board createBoard(@RequestBody String name) {
        Board board = boardService.createBoard(name);
        return board;
    }

    @PatchMapping("/{boardId}")
    public Board updateBoard(@PathVariable("boardId") Long boardId, @RequestBody String name) {
        Board board = boardService.updateBoard(boardId, name);
        return board;
    }

    @DeleteMapping("{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {
        return boardService.deleteBoard(boardId);
    }
}
