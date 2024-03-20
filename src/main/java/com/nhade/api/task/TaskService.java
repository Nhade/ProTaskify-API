package com.nhade.api.task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final FileManager fileManager;
    private Map<Integer, Task> tasks = new HashMap<>();

    @Autowired
    public TaskService(FileManager fileManager) {
        this.fileManager = fileManager;
        tasks = fileManager.loadTasks("tasks.ser");
    }

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Task getTaskById(int id){
        return tasks.get(id);
    }

    public void addTask(Task newTask) {
        int nextId = getNextId();
        newTask.setId(nextId);
        tasks.put(nextId, newTask);
        fileManager.saveTasks(tasks, "tasks.ser");
    }

    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            fileManager.saveTasks(tasks, "tasks.ser");
        }
    }

    public void updateTask(int id, String taskName, int priority, LocalDateTime deadline, boolean isDone) {
        if (tasks.containsKey(id)) {
            Task taskToUpdate = tasks.get(id);
            taskToUpdate.setTaskName(taskName);
            taskToUpdate.setPriority(priority);
            taskToUpdate.setDeadLine(deadline);
            taskToUpdate.setIsDone(isDone);
        }
    }

    private int getNextId() {
        int nextId = 1;
        while (tasks.containsKey(nextId)) {
            nextId++;
        }
        return nextId;
    }
}
