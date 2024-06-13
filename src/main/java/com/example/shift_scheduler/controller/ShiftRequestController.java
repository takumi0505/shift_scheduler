package com.example.shift_scheduler.controller;

import com.example.shift_scheduler.entity.ShiftRequest;
import com.example.shift_scheduler.entity.User;
import com.example.shift_scheduler.service.ShiftRequestService;
import com.example.shift_scheduler.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<ShiftRequest> shiftRequests = shiftRequestService.getAllShiftRequestsByUser(user);

        List<Map<String, Object>> events = new ArrayList<>();
        for (ShiftRequest request : shiftRequests) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", user.getName());
            event.put("start", request.getDate().toString());
            event.put("color", user.getColor());  // ユーザーの色を追加
            events.add(event);
        }

        String eventsJson = new Gson().toJson(events);
        model.addAttribute("user", user);
        model.addAttribute("eventsJson", eventsJson);
        return "request";
    }

    @PostMapping
    public String submitShiftRequest(@RequestParam Long userId, @RequestParam String dates, Model model) {
        User user = userService.getUserById(userId);

        // シフトが選択されていない場合の処理
        if (dates.isEmpty()) {
            return "redirect:/shift/request/confirmation?userId=" + userId + "&noShifts=true";
        }

        String[] dateArray = dates.split(",");
        for (String dateStr : dateArray) {
            LocalDate date = LocalDate.parse(dateStr);
            if (!shiftRequestService.isShiftRequestExist(user, date)) {
                ShiftRequest shiftRequest = new ShiftRequest();
                shiftRequest.setUser(user);
                shiftRequest.setDate(date);
                shiftRequestService.saveShiftRequest(shiftRequest);
            }
        }

        return "redirect:/shift/request/confirmation?userId=" + userId;
    }

    @PostMapping("/delete")
    public @ResponseBody Map<String, Object> deleteShiftRequest(@RequestBody Map<String, String> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long userId = Long.parseLong(payload.get("userId"));
            String date = payload.get("date");

            System.out.println("Received delete request for userId: " + userId + " and date: " + date);

            User user = userService.getUserById(userId);
            LocalDate localDate = LocalDate.parse(date);
            ShiftRequest shiftRequest = shiftRequestService.getShiftRequestByUserAndDate(user, localDate);
            if (shiftRequest != null) {
                shiftRequestService.deleteShiftRequest(shiftRequest);
            }
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @GetMapping("/confirmation")
    public String confirmationPage(@RequestParam Long userId, @RequestParam(required = false) boolean noShifts, Model model) {
        User user = userService.getUserById(userId);
        List<ShiftRequest> shiftRequests = shiftRequestService.getAllShiftRequestsByUser(user);

        List<Map<String, Object>> events = new ArrayList<>();
        for (ShiftRequest request : shiftRequests) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", user.getName());
            event.put("start", request.getDate().toString());
            event.put("color", user.getColor());
            events.add(event);
        }

        String eventsJson = new Gson().toJson(events);

        model.addAttribute("user", user);
        model.addAttribute("shiftRequests", shiftRequests);
        model.addAttribute("noShifts", noShifts);
        model.addAttribute("eventsJson", eventsJson);

        return "requestConfirmation";
    }

    @GetMapping("/userSelect")
    public String selectUserPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "userSelect";
    }

    @PostMapping("/deleteAll")
    public @ResponseBody Map<String, Object> deleteAllShiftRequests() {
        Map<String, Object> response = new HashMap<>();
        try {
            shiftRequestService.deleteAllShiftRequests();
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
}
