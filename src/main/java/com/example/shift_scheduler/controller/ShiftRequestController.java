package com.example.shift_scheduler.controller;


import com.example.shift_scheduler.entity.ShiftRequest;
import com.example.shift_scheduler.entity.User;
import com.example.shift_scheduler.service.ShiftRequestService;
import com.example.shift_scheduler.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shift/request")
public class ShiftRequestController {

    @Autowired
    private ShiftRequestService shiftRequestService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String requestPage(@RequestParam Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "request";
    }


    @PostMapping
    public String submitShiftRequest(@RequestParam Long userId, @RequestParam String dates) {
        User user = userService.getUserById(userId);
        String[] dateArray = dates.split(",");
        for (String dateStr : dateArray) {
            ShiftRequest shiftRequest = new ShiftRequest();
            shiftRequest.setUser(user);
            shiftRequest.setDate(LocalDate.parse(dateStr));
            shiftRequestService.saveShiftRequest(shiftRequest);
            System.out.println("Shift request saved for user: " + user.getName() + " on date: " + dateStr); // デバッグ用
        }
        return "redirect:/shift/request/confirmation?userId=" + userId;
    }

    @GetMapping("/confirmation")
    public String confirmationPage(@RequestParam Long userId, Model model) {
        User user = userService.getUserById(userId);
        List<ShiftRequest> shiftRequests = shiftRequestService.getAllShiftRequestsByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("shiftRequests", shiftRequests);
        return "request_confirmation";
    }

    @GetMapping("/user_select")
    public String selectUserPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user_select";
    }




}
