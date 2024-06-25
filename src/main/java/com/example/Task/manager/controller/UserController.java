package com.example.Task.manager.controller;

import com.example.Task.manager.entity.User;
import com.example.Task.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//maps
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //get users from database
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //post user to database
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

}
