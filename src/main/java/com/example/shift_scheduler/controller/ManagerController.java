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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ShiftRequestService shiftRequestService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String managerPage(Model model) {
        List<ShiftRequest> shiftRequests = shiftRequestService.getAllShiftRequests();
        if (shiftRequests == null || shiftRequests.isEmpty()) {
            System.out.println("No shift requests found.");
        } else {
            System.out.println("Shift requests found: " + shiftRequests.size());
        }

        List<Map<String, Object>> events = new ArrayList<>();

        for (ShiftRequest request : shiftRequests) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", request.getUser().getName());
            event.put("start", request.getDate().toString());
            event.put("color", request.getUser().getColor());
            event.put("userId", request.getUser().getId());  // ユーザーIDを追加
            events.add(event);
        }

        String eventsJson = new Gson().toJson(events);
        System.out.println("Generated JSON: " + eventsJson);  // デバッグ用
        model.addAttribute("eventsJson", eventsJson);

        List<User> users = userService.getAllUsers();  // 全ユーザーを取得
        model.addAttribute("users", users);  // ユーザーリストをモデルに追加

        return "manager";
    }
}
