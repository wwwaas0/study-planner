package com.example.studyPlanner.task.controller;

import com.example.studyPlanner.task.dto.CreateTaskRequest;
import com.example.studyPlanner.task.dto.GetTasksRes;
import com.example.studyPlanner.task.dto.UpdateTaskContentRequest;
import com.example.studyPlanner.task.entity.Task;
import com.example.studyPlanner.task.entity.TaskStatus;
import com.example.studyPlanner.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{plannerId}")
    public List<GetTasksRes> getTasks(@PathVariable("plannerId") Long id) {
        List<GetTasksRes> getTasksRes = taskService.getTasks(id);
        return getTasksRes;
    }

    @PostMapping()
    public String createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        Task task = taskService.createTask(createTaskRequest.getPostId(), createTaskRequest.getContent());
        return createTaskRequest.getPostId() + "번 플래너에 할 일 생성 완료!\n할일 번호: " + task.getId();
    }

    @PostMapping("/content/{taskId}")
    public Task updateTask(@PathVariable("taskId") Long id, @RequestBody UpdateTaskContentRequest updateTaskContentRequest) {
        Task task = taskService.updateTask(id, updateTaskContentRequest.getContent());
        return task;
    }

    @PostMapping("/status/{taskId}")
    public Task updateTaskStatus(@PathVariable("taskId") Long id, @RequestParam("status") String status) {
        Task task = taskService.updateTaskStatus(id, TaskStatus.StringToTaskStatus(status));
        return task;
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long id) {
        return taskService.deleteTask(id);
    }
}
