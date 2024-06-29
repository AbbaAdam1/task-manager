package com.example.Task.manager.service;

import com.example.Task.manager.entity.User;
import com.example.Task.manager.entity.UserTask;
import com.example.Task.manager.repository.UserRepository;
import com.example.Task.manager.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private UserRepository userRepository;

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

    public List<UserTask> getUserTasksByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return userTaskRepository.findByUserId(user.getId());
        }
        return List.of();  // Return an empty list if user is not found
    }

    public UserTask getUserTaskById(Integer id) {
        Optional<UserTask> userTaskOptional = userTaskRepository.findById(id);
        return userTaskOptional.orElse(null);
    }
}
