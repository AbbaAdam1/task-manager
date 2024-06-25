package com.example.Task.manager.service;

import com.example.Task.manager.entity.UserTask;
import com.example.Task.manager.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;

    public List<UserTask> getAllUserTasks() {
        return userTaskRepository.findAll();
    }

    public List<UserTask> getUserTasksByUserId(Integer userId) {
        return userTaskRepository.findByUserId(userId);
    }

    public List<UserTask> getUserTasksByTaskId(Integer taskId) {
        return userTaskRepository.findByTaskId(taskId);
    }

    public UserTask assignTaskToUser(UserTask userTask) {
        return userTaskRepository.save(userTask);
    }

    public void removeTaskFromUser(Integer id) {
        userTaskRepository.deleteById(id);
    }
}
