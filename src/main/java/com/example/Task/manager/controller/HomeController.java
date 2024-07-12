package com.example.Task.manager.controller;

import com.example.Task.manager.entity.User;
import com.example.Task.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    //get mapping to send user data to home page to check for admin
    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> currentUserOptional = userService.getUserByUsername(userDetails.getUsername());
        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            model.addAttribute("currentUser", currentUser);
        }
        return "home"; // Ensure this view exists in your templates
    }
}
