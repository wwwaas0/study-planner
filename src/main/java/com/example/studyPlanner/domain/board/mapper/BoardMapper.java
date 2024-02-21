package com.example.studyPlanner.domain.board.mapper;

import com.example.studyPlanner.domain.board.dto.GetBoardListRes;
import com.example.studyPlanner.domain.board.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    public GetBoardListRes toDTO(Board board);
}
