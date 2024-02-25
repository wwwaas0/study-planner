package com.example.studyPlanner.task.service;

import com.example.studyPlanner.planner.entity.Planner;
import com.example.studyPlanner.planner.repository.PlannerRepository;
import com.example.studyPlanner.task.dto.GetTasksRes;
import com.example.studyPlanner.task.entity.Task;
import com.example.studyPlanner.task.entity.TaskStatus;
import com.example.studyPlanner.task.mapper.TaskMapper;
import com.example.studyPlanner.task.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final PlannerRepository plannerRepository;

    public List<GetTasksRes> getTasks(Long plannerId) {
        List<Task> tasks = taskRepository.findByPlannerId(plannerId);
        List<GetTasksRes> tasksRes = tasks.stream()
                .map(task -> TaskMapper.INSTANCE.toDTO(task))
                .collect(Collectors.toList());
        return tasksRes;
    }

    public Task createTask(Long plannerId, String content) {
        Optional<Planner> plannerOptional = plannerRepository.findById(plannerId);
        Task task = null;
        if (plannerOptional.isPresent()) {
            Planner planner = plannerOptional.get();
            task = new Task(content, planner);
            taskRepository.save(task);
        } else {
            throw new EntityNotFoundException(plannerId + "번 플래너가 존재하지 않습니다.");
        }
        return task;
    }

    @Transactional
    public Task updateTask(Long taskId, String newContent) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Task task = null;
        if (taskOptional.isPresent()) {
            task = taskOptional.get();
            task.setContent(newContent);
            taskRepository.save(task);
        } else {
            throw new EntityNotFoundException(taskId + "번 할 일이 존재하지 않습니다.");
        }
        return task;
    }

    public Task updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Task task = null;
        if (taskOptional.isPresent()) {
            task = taskOptional.get();
            task.setTaskStatus(newStatus);
            taskRepository.save(task);
        } else {
            throw new EntityNotFoundException(taskId + "번 할 일이 존재하지 않습니다.");
        }

        return task;
    }

    public String deleteTask(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            taskRepository.deleteById(taskId);
        } else {
            throw new EntityNotFoundException(taskId + "번 할 일이 존재하지 않습니다.");
        }
        return taskId + "번 할일이 정상적으로 삭제되었습니다.";
    }
}
