package com.example.studyPlanner.planner.controller;

import com.example.studyPlanner.board.service.BoardService;
import com.example.studyPlanner.planner.PlannerMapper;
import com.example.studyPlanner.planner.dto.GetPlannerHomeRes;
import com.example.studyPlanner.planner.entity.Planner;
import com.example.studyPlanner.planner.service.PlannerService;
import com.example.studyPlanner.post.dto.CreatePostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/planners")
public class PlannerController {
    private final PlannerService plannerService;
    private final BoardService boardService;
    @PostMapping("/study-start/{plannerId}")
    public String studyStart(@PathVariable("plannerId") Long plannerId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String startTime = plannerService.studyStart(plannerId, currentDateTime);
        return startTime;
    }

    @PostMapping("/study-stop/{plannerId}")
    public String studyStop(@PathVariable("plannerId") Long plannerId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String stopTime = plannerService.studyStop(plannerId, currentDateTime);
        return stopTime;
    }

    @GetMapping("/home/{userId}")
    public String getHome(@PathVariable("userId") Long userId, Model model) {
        Planner planner = plannerService.getHome(userId);
        GetPlannerHomeRes getPlannerHomeRes = PlannerMapper.INSTANCE.toDTO(planner);
        String studyTime = plannerService.formatStudyTime(getPlannerHomeRes.getStudyTime());

        model.addAttribute("createdAt", getPlannerHomeRes.getCreatedAt());
        model.addAttribute("studyTime", studyTime);
        model.addAttribute("tasks", getPlannerHomeRes.getTasks());

        CreatePostReq createPostReq = new CreatePostReq();
        model.addAttribute("createPostReq", createPostReq);
        model.addAttribute("boardNames", boardService.getBoardNames());

        return "planner/main";
    }

    //특정 플래너 조회
    @GetMapping("/{plannerId}")
    public String getPlanner(@PathVariable("plannerId") Long plannerId, Model model) {
        Planner planner = plannerService.getPlanner(plannerId);

        GetPlannerHomeRes getPlannerHomeRes = PlannerMapper.INSTANCE.toDTO(planner);
        String studyTime = plannerService.formatStudyTime(getPlannerHomeRes.getStudyTime());

        model.addAttribute("createdAt", getPlannerHomeRes.getCreatedAt());
        model.addAttribute("studyTime", studyTime);
        model.addAttribute("tasks", getPlannerHomeRes.getTasks());

        CreatePostReq createPostReq = new CreatePostReq();
        model.addAttribute("createPostReq", createPostReq);
        model.addAttribute("boardNames", boardService.getBoardNames());

        return "planner/main";
    }
}
