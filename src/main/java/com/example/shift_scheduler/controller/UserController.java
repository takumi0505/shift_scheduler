package com.example.shift_scheduler.controller;


import com.example.shift_scheduler.entity.User;
import com.example.shift_scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String userPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user";
    }

    @GetMapping("/user/add")
    public String addUserPage() {
        return "user_add";
    }

    @PostMapping("/user/add")
    public String addUser(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        userService.saveUser(user);
        return "redirect:/user";
    }

    @GetMapping("/user/delete")
    public String deleteUserPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user_delete";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/user";
    }
}
