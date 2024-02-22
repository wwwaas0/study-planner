package com.example.studyPlanner.post.mapper;

import com.example.studyPlanner.planner.entity.Planner;
import com.example.studyPlanner.post.dto.GetPostListRes;
import com.example.studyPlanner.post.dto.GetPostRes;
import com.example.studyPlanner.post.entity.Post;
import com.example.studyPlanner.task.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "planner", target = "topThreeTask", qualifiedByName = "mapTopThreeTasks")
    public GetPostListRes toListDTO(Post post);

    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "post.createdAt", target = "postCreatedAt")
    @Mapping(source = "planner.createdAt", target = "plannerCreatedAt")
    @Mapping(source = "planner.studyTime", target = "studyTime")
    @Mapping(source = "planner", target = "tasks", qualifiedByName = "tasks")
    public GetPostRes toDTO(Post post);

    @Named("mapTopThreeTasks")
    default List<Task> mapTopThreeTasks(Planner planner) {
        return planner.getTasks().stream()
                .limit(3)
                .collect(Collectors.toList());
    }

    @Named("tasks")
    default List<Task> tasks(Planner planner) {
        return planner.getTasks();
    }
}
