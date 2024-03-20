package com.nhade.api.task;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {

    private final TaskService taskService;
    private final LocalDateTimeConverter localDateTimeConverter;

    @Autowired
    public TaskController(TaskService taskService, LocalDateTimeConverter localDateTimeConverter) {
        this.taskService = taskService;
        this.localDateTimeConverter = localDateTimeConverter;
    }

    @GetMapping
    public Map<Integer, Task> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping
    public ResponseEntity<String> addTasks(@RequestBody JSONObject json) {
        try {
            String taskName = json.getAsString("taskName");
            int priority = json.getAsNumber("priority").intValue();
            LocalDateTime deadline = localDateTimeConverter.convert(json.getAsString("deadline"));
            Task newTask = new Task(taskName, priority, deadline);
            taskService.addTask(newTask);
            return ResponseEntity.status(201).body(newTask.toString());
        } catch (NullPointerException e) {
            return ResponseEntity.status(422).body("Unable to parse request");
        }

    }

    @PutMapping
    public ResponseEntity<String> updateTask(@RequestBody JSONObject json) {
        try {
            int id = json.getAsNumber("id").intValue();
            Task taskToEdit = taskService.getTaskById(id);
            if (json.containsKey("taskName"))
                taskToEdit.setTaskName(json.getAsString("taskName"));
            if (json.containsKey("priority"))
                taskToEdit.setPriority(json.getAsNumber("priority").intValue());
            if (json.containsKey("deadline"))
                taskToEdit.setDeadLine(localDateTimeConverter.convert(json.getAsString("deadline")));
            if (json.containsKey("isDone"))
                taskToEdit.setIsDone(Boolean.parseBoolean(json.getAsString("isDone")));
            taskService.updateTask(id, taskToEdit.getTaskName(), taskToEdit.getPriority(), taskToEdit.getDeadLine(),
                    taskToEdit.getIsDone());
            return ResponseEntity.status(200).body(String.format("Task updated successfully (ID: %d)\n", id) + taskToEdit.toString());
        } catch (Exception e) {
            return ResponseEntity.status(422).body("ID is malformed or does not exist");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTask(@RequestBody JSONObject json) {
        try {
            int id = json.getAsNumber("id").intValue();
            taskService.deleteTask(id);
            return ResponseEntity.status(200).body(String.format("Task deleted successfully (ID: %d)", id));
        } catch (Exception e) {
            return ResponseEntity.status(422).body("ID is malformed or does not exist");
        }
    }
}
