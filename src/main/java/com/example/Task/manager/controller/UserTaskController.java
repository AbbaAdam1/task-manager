package com.example.Task.manager.controller;

import com.example.Task.manager.entity.UserTask;
import com.example.Task.manager.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-tasks")
public class UserTaskController {

    @Autowired
    private UserTaskService userTaskService;

    @GetMapping
    public List<UserTask> getAllUserTasks() {
        return userTaskService.getAllUserTasks();
    }

    @GetMapping("/user/{userId}")
    public List<UserTask> getUserTasksByUserId(@PathVariable Integer userId) {
        return userTaskService.getUserTasksByUserId(userId);
    }

    @GetMapping("/task/{taskId}")
    public List<UserTask> getUserTasksByTaskId(@PathVariable Integer taskId) {
        return userTaskService.getUserTasksByTaskId(taskId);
    }

    @PostMapping
    public UserTask assignTaskToUser(@RequestBody UserTask userTask) {
        return userTaskService.assignTaskToUser(userTask);
    }

    @DeleteMapping("/{id}")
    public void removeTaskFromUser(@PathVariable Integer id) {
        userTaskService.removeTaskFromUser(id);
    }
}
