package com.example.shift_scheduler.controller;


import com.example.shift_scheduler.entity.ShiftRequest;
import com.example.shift_scheduler.entity.User;
import com.example.shift_scheduler.service.ShiftRequestService;
import com.example.shift_scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/shift")
public class ShiftRequestController {

    @Autowired
    private ShiftRequestService shiftRequestService;

    @Autowired
    private UserService userService;

    @GetMapping("/request")
    public String requestPage(@RequestParam Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "request";
    }


    @PostMapping("/request")
    public String submitShiftRequest(@RequestParam Long userId, @RequestParam String date) {
        User user = userService.getUserById(userId);
        ShiftRequest shiftRequest = new ShiftRequest();
        shiftRequest.setUser(user);
        shiftRequest.setDate(LocalDate.parse(date));
        shiftRequestService.saveShiftRequest(shiftRequest);
        return "redirect:/shift/request/confirmation?userId=" + userId;
    }

    @GetMapping("/request/confirmation")
    public String confirmationPage(@RequestParam Long userId, Model model) {
        User user = userService.getUserById(userId);
        List<ShiftRequest> shiftRequests = shiftRequestService.getAllShiftRequestsByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("shiftRequests", shiftRequests);
        return "request_confirmation";
    }

    @GetMapping("/request/select-user")
    public String selectUserPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user_select";
    }

}
