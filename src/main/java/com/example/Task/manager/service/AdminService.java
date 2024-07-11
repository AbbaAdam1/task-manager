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

    // Check if admin
    public boolean isAdmin(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> "ADMIN".equals(value.getRole())).orElse(false);
    }
}
