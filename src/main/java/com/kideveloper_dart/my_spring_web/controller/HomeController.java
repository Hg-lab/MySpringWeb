package com.kideveloper_dart.my_spring_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/index2")
    public String index1() {
        return "index2";
    }
}
