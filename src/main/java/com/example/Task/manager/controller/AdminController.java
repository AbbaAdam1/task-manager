package com.example.Task.manager.controller;

import com.example.Task.manager.entity.User;
import com.example.Task.manager.entity.UserTask;
import com.example.Task.manager.service.AdminService;
import com.example.Task.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin-tasks-view")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list"; // Ensure this view exists in your templates
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Integer id, Model model) {
        Optional<User> user = adminService.getUserById(id);
        if (user.isPresent()) {
            // fetches user by id, retrieves their tasks, and redirects to task-list to display said tasks
            List<UserTask> userTasks = adminService.getUserTasksByUserId(id);
            model.addAttribute("userTasks", userTasks);
            return "task-list"; // Ensure this view exists in your templates
        } else {
            // Handle case where user is not found
            return "redirect:/admin-tasks-view/users"; // Redirect to user list or handle appropriately
        }
    }

    @GetMapping("/users/{id}/tasks")
    public String getUserTasksById(@PathVariable Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        boolean isAdmin = adminService.isAdmin(username);
        List<UserTask> userTasks = adminService.getUserTasksByUserId(id);
        model.addAttribute("userTasks", userTasks);
        model.addAttribute("isAdmin", isAdmin);
        return "task-list"; // Ensure this view exists in your templates
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user"; // Ensure this view exists in your templates
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin-tasks-view/users"; // Redirect to the user list or another appropriate page
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute User userDetails) {
        adminService.updateUser(id, userDetails);
        return "redirect:/admin-tasks-view/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Integer id) {
        adminService.deleteUser(id);
        return "redirect:/admin-tasks-view/users";
    }
}
