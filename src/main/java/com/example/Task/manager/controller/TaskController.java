package com.example.Task.manager.controller;

import com.example.Task.manager.entity.Task;
import com.example.Task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/{taskId}/assign/{userId}")
    public void assignTaskToUser(@PathVariable Integer taskId, @PathVariable Integer userId) {
        taskService.assignTaskToUser(taskId, userId);
    }
}
