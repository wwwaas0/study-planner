package com.example.studyPlanner.task.controller;

import com.example.studyPlanner.task.mapper.TaskMapper;
import com.example.studyPlanner.task.dto.GetTasksRes;
import com.example.studyPlanner.task.entity.Task;
import com.example.studyPlanner.task.entity.TaskStatus;
import com.example.studyPlanner.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{plannerId}")
    public List<GetTasksRes> getTasks(@PathVariable("plannerId") Long id) {
        List<Task> tasks = taskService.getTasks(id);

        List<GetTasksRes> tasksRes = tasks.stream()
                .map(task -> TaskMapper.INSTANCE.toDTO(task))
                .collect(Collectors.toList());

        return tasksRes;
    }

    //plannerId를 pathVariable에 받는게 나을까 body로 받는게 나을지 물어보기
    @PostMapping("/{plannerId}")
    public String createTask(@PathVariable("plannerId") Long id, @RequestBody String content) {
        Task task = taskService.createTask(id, content);
        return id+"번 플래너에 할 일 생성 완료!\n할일 번호: "+task.getId();
    }

    @PatchMapping("/content/{taskId}")
    public Task updateTask(@PathVariable("taskId") Long id, @RequestBody String content) {
        Task task = taskService.updateTask(id, content);
        return task;
    }

    //이거 파라미터를 enum으로 해도 되는지 물어보기
    @PatchMapping("/status/{taskId}")
    public Task updateTaskStatus(@PathVariable("taskId") Long id, @RequestParam("status") String status) {
        TaskStatus taskStatus = null;
        if (status.equals(TaskStatus.DONE.name())) {
            taskStatus = TaskStatus.DONE;
        } else if (status.equals(TaskStatus.IN_PROGRESS.name())) {
            taskStatus = TaskStatus.IN_PROGRESS;
        } else if (status.equals(TaskStatus.NOT_STARTED.name())) {
            taskStatus = TaskStatus.NOT_STARTED;
        }
        Task task = taskService.updateTaskStatus(id, taskStatus);
        return task;
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long id) {
        return taskService.deleteTask(id);
    }
}
