package com.nhade.api.task;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class Task implements Serializable {
    private int id;
    private String taskName;
    private int priority;
    private LocalDateTime deadline;
    private boolean isDone;

    public Task() {
    }

    public Task(String taskName, int priority, LocalDateTime deadline) {
        this.taskName = taskName;
        this.priority = priority;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadLine() {
        return deadline;
    }

    public void setDeadLine(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String toString() {
        return "Task{" + 
                "id=" + id + 
                ", taskName='" + taskName + '\'' + 
                ", priority=" + priority + 
                ", deadline='" + deadline + '\'' + 
                ", isDone=" + isDone +
                '}';
    }
}
