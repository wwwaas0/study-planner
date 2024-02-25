package com.example.studyPlanner.board.controller;

import com.example.studyPlanner.board.dto.CreateBoardReq;
import com.example.studyPlanner.board.dto.GetBoardListRes;
import com.example.studyPlanner.board.dto.ModifyBoard;
import com.example.studyPlanner.board.entity.Board;
import com.example.studyPlanner.board.service.BoardService;
import com.example.studyPlanner.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getBoards(Model model) {
        List<Board> boards = boardService.getBoards();

        List<GetBoardListRes> boardListRes = boards.stream()
                .map(board -> BoardMapper.INSTANCE.toDTO(board))
                .collect(Collectors.toList());
        model.addAttribute("boradNames", boardListRes);

        //게시판 생성 모달 - 입력 폼
        CreateBoardReq createBoardReq = new CreateBoardReq();
        model.addAttribute("createBoardReq", createBoardReq);
        return "board/name-list";
    }

    @PostMapping()
    public String createBoard(@ModelAttribute CreateBoardReq createBoardReq) {
        System.out.println(createBoardReq.getNewName());
        boardService.createBoard(createBoardReq.getNewName());
        return "redirect:/boards";
    }

    @GetMapping("/modify-delete")
    public String getModifyDeleteBoard(Model model){
        List<Board> boards = boardService.getBoards();

        model.addAttribute("boards", boards);

        //게시판 이름 수정 모달
        ModifyBoard modifyBoard = new ModifyBoard();
        model.addAttribute("modifyBoard", modifyBoard);

        return "board/modify-delete";
    }

    @PostMapping("/{boardId}")
    public String updateBoard(@PathVariable("boardId") Long boardId, @ModelAttribute ModifyBoard modifyBoard, Model model) {
        boardService.updateBoard(boardId, modifyBoard.getNewName());

        return "redirect:/boards";
    }

    @GetMapping("/delete/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId, Model model) {
        boardService.deleteBoard(boardId);
        return "redirect:/boards";
    }

}
