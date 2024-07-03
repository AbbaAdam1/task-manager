package com.example.Task.manager.controller;

import com.example.Task.manager.entity.User;
import com.example.Task.manager.entity.UserTask;
import com.example.Task.manager.entity.Task;
import com.example.Task.manager.service.TaskService;
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
@RequestMapping("/tasks-view")
public class UserTaskController {

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    //@GetMapping("/user")
    @GetMapping
    public String getUserTasksByLoggedInUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<UserTask> userTasks = userTaskService.getUserTasksByUsername(username);
        model.addAttribute("userTasks", userTasks);
        return "task-list"; // Ensure this view exists in your templates
    }

    /*
    @GetMapping
    public String getAllUserTasks(Model model) {
        List<UserTask> userTasks = userTaskService.getAllUserTasks();
        model.addAttribute("userTasks", userTasks);
        return "task-list"; // Ensure this view exists in your templates
    }
     */

    @GetMapping("/new")
    public String createUserTaskForm(Model model) {
        model.addAttribute("userTask", new UserTask());
        return "task-form"; // Ensure this view exists in your templates
    }


    @PostMapping("/create")
    public String createUserTask(@ModelAttribute UserTask userTask, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        // Ensure task is saved before assigning it to userTask
        Task task = userTask.getTask();
        if (task.getId() == null) { // Check if task is already saved
            taskService.createTask(task); // Save task if not already saved
        }

        userTaskService.assignTaskToCurrentUser(userTask, username);
        return "redirect:/tasks-view";
    }


    /*
    @PostMapping("/create")
    public String createUserTask(@ModelAttribute UserTask userTask,
                                 @RequestParam(value = "userId", required = false) Integer userId,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        if (userId != null) {
            // Admin is adding a task for a specific user
            Optional<User> user = userService.getUserById(userId);

            if (user.isPresent()) {
                // Assign the task to the selected user
                userTask.setUser(user.get());
            } else {
                // Handle case where selected user is not found
                return "redirect:/admin-tasks-view/users"; // Redirect or handle appropriately
            }
        } else {
            // Regular user is adding their own task
            userTaskService.assignTaskToCurrentUser(userTask, username);
        }

        // Ensure task is saved before assigning it to userTask
        Task task = userTask.getTask();
        if (task.getId() == null) { // Check if task is already saved
            taskService.createTask(task); // Save task if not already saved
        }

        // Save the UserTask
        userTaskService.assignTaskToCurrentUser(userTask, username);

        return "redirect:/tasks-view";
    }

     */





    @GetMapping("/edit/{id}")
    public String editUserTaskForm(@PathVariable Integer id, Model model) {
        UserTask userTask = userTaskService.getUserTaskByIdWithTask(id);
        if (userTask != null) {
            model.addAttribute("userTask", userTask);
            return "edit-task-form"; // Ensure this view exists in your templates
        } else {
            return "redirect:/tasks-view";
        }
    }

    @PostMapping("/update/{id}")
    public String updateUserTask(@PathVariable Integer id, @ModelAttribute UserTask userTaskDetails) {
        userTaskService.updateUserTask(id, userTaskDetails);
        return "redirect:/tasks-view";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserTask(@PathVariable Integer id) {
        userTaskService.removeTaskFromUser(id);
        return "redirect:/tasks-view";
    }
}
