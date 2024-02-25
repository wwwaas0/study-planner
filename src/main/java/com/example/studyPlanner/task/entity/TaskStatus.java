package com.example.studyPlanner.task.entity;

public enum TaskStatus {
    DONE, IN_PROGRESS, NOT_STARTED;

    public static TaskStatus StringToTaskStatus(String status) {
        if (status.equals(TaskStatus.DONE.name())) {
            return TaskStatus.DONE;
        } else if (status.equals(TaskStatus.IN_PROGRESS.name())) {
            return TaskStatus.IN_PROGRESS;
        } else {
            return TaskStatus.NOT_STARTED;
        }
    }
}
