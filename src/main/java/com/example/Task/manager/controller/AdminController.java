package com.example.Task.manager.controller;

import com.example.Task.manager.entity.Task;
import com.example.Task.manager.entity.User;
import com.example.Task.manager.entity.UserTask;
import com.example.Task.manager.service.AdminService;
import com.example.Task.manager.service.UserService;
import com.example.Task.manager.service.UserTaskService;
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

    @Autowired
    private UserTaskService userTaskService;

    //sends user list to admin user page
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list"; // Ensure this view exists in your templates
    }

    // fetches user by id, retrieves their tasks, and redirects to task-list to display said tasks
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            // fetches user by id, retrieves their tasks, and redirects to task-list to display said tasks
            List<UserTask> userTasks = userTaskService.getUserTasksByUserId(id);
            boolean isAdmin = adminService.isAdmin(userDetails.getUsername());
            model.addAttribute("userTasks", userTasks);
            model.addAttribute("userId", id); // Add userId to the model
            model.addAttribute("isAdmin", isAdmin); // Add isAdmin to the model
            return "task-list"; // Ensure this view exists in your templates
        } else {
            // Handle case where user is not found
            return "redirect:/admin-tasks-view/users"; // Redirect to user list or handle appropriately
        }
    }

    //for creating a new task for an admin
    /*
    @GetMapping("/users/{id}/create")
    public String createTaskForm(@PathVariable Integer id, Model model) {
        Optional<User> user = adminService.getUserById(id);
        UserTask userTask = new UserTask(); // Create a new UserTask object
        userTask.setId(id); // Set the userId on the UserTask object
        model.addAttribute("userTask", userTask); // Add userTask to the model
        model.addAttribute("id", id); // Add the userId to the model
        return "admin-task-form"; // Ensure this view exists in your templates
    }
     */

    @PostMapping("/users/{id}/create")
    public String saveUserTask(@ModelAttribute("userTask") UserTask userTask, @RequestParam Integer id) {
        // Logic to save the userTask
        userTask.setId(id); // Ensure the userId is set
        userTaskService.saveUserTask(userTask); // Save the userTask using the service
        return "redirect:/admin-tasks-view/users/" + id; // Redirect to the user's task list
    }

    @GetMapping("/add-task/{id}")
    public String showTaskForm(@PathVariable  Integer id, Model model) {
        // Fetch user by userId and pass it to the task-form
        Optional<User> user = userService.getUserById(id);
        UserTask userTask = new UserTask(); // Create a new UserTask object
        userTask.setId(id); // Set the userId on the UserTask object
        model.addAttribute("user", user);
        model.addAttribute("userTask", userTask);
        model.addAttribute("task", new Task());
        return "admin-task-form";
    }
    ///

    @GetMapping("/users/{id}/tasks")
    public String getUserTasksById(@PathVariable Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        boolean isAdmin = adminService.isAdmin(username);
        List<UserTask> userTasks = userTaskService.getUserTasksByUserId(id);
        model.addAttribute("userTasks", userTasks);
        model.addAttribute("isAdmin", isAdmin);
        return "task-list"; // Ensure this view exists in your templates
    }

    //mapping for adding a new user
    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user"; // Ensure this view exists in your templates
    }

    //post mapping for adding a user
    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin-tasks-view/users"; // Redirect to the user list or another appropriate page
    }

    //update mapping
    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute User userDetails) {
        userService.updateUser(id, userDetails);
        return "redirect:/admin-tasks-view/users";
    }

    //delete mapping
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin-tasks-view/users";
    }
}
