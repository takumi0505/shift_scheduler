package com.example.shift_scheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    //  /userはUserControllerで定義されているのでなくていい

    @GetMapping("/request")
    public String requestPage(Model model) {
        return "request";
    }

    //ManagerControllerで定義したので消した
}
