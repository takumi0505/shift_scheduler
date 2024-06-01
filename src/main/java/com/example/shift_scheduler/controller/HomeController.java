package com.example.shift_scheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/user")
    public String userPage(Model model) {
        return "user";
    }

    @GetMapping("/request")
    public String requestPage(Model model) {
        return "request";
    }

    @GetMapping("/manager")
    public String managerPage(Model model) {
        return "manager";
    }
}
