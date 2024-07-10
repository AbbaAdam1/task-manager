package com.example.Task.manager.service;

import com.example.Task.manager.entity.Task;
import com.example.Task.manager.entity.User;
import com.example.Task.manager.entity.UserTask;
import com.example.Task.manager.repository.UserRepository;
import com.example.Task.manager.repository.UserTaskRepository;
import com.example.Task.manager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    // Gets all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setRole(userDetails.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // Gets a user's tasks by their id
    public List<UserTask> getUserTasksByUserId(Integer userId) {
        return userTaskRepository.findByUserId(userId);
    }

    // Check if admin
    public boolean isAdmin(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> "ADMIN".equals(value.getRole())).orElse(false);
    }

    public void saveUserTask(UserTask userTask) {
        // Check if the Task is new and needs saving
        Task task = userTask.getTask();
        if (task != null && task.getId() == null) {
            taskRepository.save(task); // Save the Task
        }
        userTaskRepository.save(userTask); // Save the UserTask
    }
}
