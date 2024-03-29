package com.kideveloper_dart.my_spring_web.controller;

import com.kideveloper_dart.my_spring_web.model.User;
import com.kideveloper_dart.my_spring_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user) {
        try {
            userService.save(user);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "redirect:/account/register";
        }
    }
}

