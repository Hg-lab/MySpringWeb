package com.kideveloper.my_spring_web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tables")
public class TableController {

    @GetMapping
    public String tables() {
        return "tables/tables";
    }

    @GetMapping("/financial-statements")
    public String financialStatement() {
        return "tables/financial-statements";
    }

}
