//package com.example.studyPlanner.controller;
//
//import com.example.studyPlanner.board.controller.BoardController;
//import com.example.studyPlanner.board.entity.Board;
//import com.example.studyPlanner.board.service.BoardService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.hasItems;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@MockBean(JpaMetamodelMappingContext.class)
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(BoardController.class)
//public class BoardControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private BoardService boardService;
//
//    @DisplayName("[POST] 새로운 게시판 생성")
//    @Test
//    void createBoard() throws Exception {
//        //given
//        Board board = Board.builder()
//                .name("test")
//                .build();
//
//        Board savedBoard = new Board();
//        savedBoard.setId(1L);
//        savedBoard.setName(board.getName());
//
//        given(boardService.createBoard(board.getName())).willReturn(savedBoard); //mocking
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                post("/boards")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\": \"test\"}")
//                        .accept(MediaType.APPLICATION_JSON));
//
//
//        // then
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("test"));
//    }
//
//    @DisplayName("[Get] 게시판 이름 목록 조회")
//    @Test
//    void getBoards() throws Exception {
//        //given
//        Board board1 = new Board();
//        board1.setId(1L);
//        board1.setName("고등학생");
//
//        Board board2 = new Board();
//        board2.setId(2L);
//        board2.setName("취준생");
//
//        List<Board> boards = new ArrayList<>();
//        boards.add(board1);
//        boards.add(board2);
//
//        given(boardService.getBoards()).willReturn(boards);
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                get("/boards")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$[*].name", hasItems("고등학생", "취준생")));
//    }
//
//    @DisplayName("[PATCH] 게시판 이름 수정")
//    @Test
//    void updateBoard() throws Exception {
//        //given
//        Board board = new Board();
//        board.setId(1L);
//        board.setName("고등학생");
//
//        String newName = "고3";
//
//        Board modifyedBoard = new Board();
//        modifyedBoard.setId(board.getId());
//        modifyedBoard.setName(newName);
//
//        given(boardService.updateBoard(anyLong(), anyString())).willReturn(modifyedBoard); //mocking
//
//        // when
//        Long boardId = 1L;
//        ResultActions actions = mockMvc.perform(
//                patch("/boards/{boardId}", boardId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\": \"고3\"}")
//                        .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(boardId))
//                .andExpect(jsonPath("$.name").value(newName));
//    }
//
//    @DisplayName("[DELETE] 게시판 삭제")
//    @Test
//    void deleteBoard() throws Exception {
//        //given
//        Board board = new Board();
//        board.setId(1L);
//        board.setName("고등학생");
//
//        String deleteMsg = board.getId() + "번 게시판이 정상적으로 삭제되었습니다.";
//
////        given(boardService.deleteBoard(board.getId()));; //mocking
//
//        // when
//        Long boardId = 1L;
//        ResultActions actions = mockMvc.perform(
//                delete("/boards/{boardId}", boardId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        actions.andExpect(status().isOk())
//                .andExpect(content().string(containsString("1")));
//    }
//}
