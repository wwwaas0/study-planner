package com.example.studyPlanner.comment.mapper;

import com.example.studyPlanner.comment.dto.GetCommentListRes;
import com.example.studyPlanner.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "id", target = "commentId")
    public GetCommentListRes toDTO(Comment comment);
}
