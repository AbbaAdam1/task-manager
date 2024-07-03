package com.example.Task.manager.service;

import com.example.Task.manager.entity.Task;
import com.example.Task.manager.entity.User;
import com.example.Task.manager.entity.UserTask;
import com.example.Task.manager.repository.TaskRepository;
import com.example.Task.manager.repository.UserRepository;
import com.example.Task.manager.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

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
        return Collections.emptyList();  // Return an empty list if user is not found
    }

    public UserTask getUserTaskById(Integer id) {
        return userTaskRepository.findById(id).orElse(null);
    }

    public UserTask getUserTaskByIdWithTask(Integer id) {
        Optional<UserTask> userTaskOptional = userTaskRepository.findById(id);
        if (userTaskOptional.isPresent()) {
            UserTask userTask = userTaskOptional.get();
            Task task = taskRepository.findById(userTask.getTask().getId()).orElse(null);
            if (task != null) {
                userTask.setTask(task);
            }
            return userTask;
        }
        return null;
    }

    public UserTask updateUserTask(Integer id, UserTask userTaskDetails) {
        Optional<UserTask> optionalUserTask = userTaskRepository.findById(id);
        if (optionalUserTask.isPresent()) {
            UserTask existingUserTask = optionalUserTask.get();
            // Update relevant fields of existingUserTask with userTaskDetails
            Task task = existingUserTask.getTask();
            if (task != null) {
                task.setName(userTaskDetails.getTask().getName());
                task.setDescription(userTaskDetails.getTask().getDescription());
                // Set other fields as needed
            }
            return userTaskRepository.save(existingUserTask);
        }
        return null;  // Or throw an exception if task is not found
    }

    public UserTask assignTaskToCurrentUser(UserTask userTask, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            userTask.setUser(user);
            return userTaskRepository.save(userTask);
        }
        return null;  // Or throw an exception if user is not found
    }
}

