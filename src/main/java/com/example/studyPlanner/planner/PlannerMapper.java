package com.example.studyPlanner.planner;

import com.example.studyPlanner.planner.dto.GetPlannerHomeRes;
import com.example.studyPlanner.planner.entity.Planner;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlannerMapper {
    PlannerMapper INSTANCE = Mappers.getMapper(PlannerMapper.class);
    public GetPlannerHomeRes toDTO(Planner planner);
}

