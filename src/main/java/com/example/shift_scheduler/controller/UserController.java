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
        return "userAdd";
    }

    @PostMapping("/user/add")
    public String addUser(@RequestParam String name, Model model) {
        User user = new User();
        user.setName(name);
        userService.saveUser(user);
        model.addAttribute("user", user);  // ユーザー情報をモデルに追加
        return "userRegistered";  // 完了ページにリダイレクト
    }

    @GetMapping("/user/delete")
    public String deleteUserPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "userDelete";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam Long id, Model model) {
        User user = userService.getUserById(id);  // 削除されるユーザー情報を取得
        userService.deleteUser(id);
        model.addAttribute("user", user);  // ユーザー情報をモデルに追加
        return "userDeleted";  // 完了ページにリダイレクト
    }
}
