package com.example.Task.manager.controller;

import com.example.Task.manager.entity.User;
import com.example.Task.manager.repository.UserRepository;
import com.example.Task.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Show add user form
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user"; // Ensure this view exists in your templates
    }

    // Process add user form
    @PostMapping("/sign-up")
    public String addUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/"; // Redirect to the user list or another appropriate page
    }

    // Get all users
    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user-list"; // Ensure this view exists in your templates
    }

    // Create a user (for RESTful API, if needed)
    @PostMapping
    @ResponseBody
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}


