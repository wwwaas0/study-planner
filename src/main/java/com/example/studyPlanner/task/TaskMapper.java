package com.example.studyPlanner.task;

import com.example.studyPlanner.task.dto.GetTasksRes;
import com.example.studyPlanner.task.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
    public GetTasksRes toDTO(Task task);
}
