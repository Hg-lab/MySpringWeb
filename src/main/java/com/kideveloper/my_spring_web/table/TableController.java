package com.kideveloper.my_spring_web.table;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/table")
public class TableController {

    @GetMapping
    private String getTable(Model model) {

        List<String> cols= new ArrayList<>();
        cols.addAll(Arrays.asList("username", "age", "job"));


        List<Map<String, String>> rows= new ArrayList<>();
        Map<String, String> row = new HashMap<>();
        row.put("username", "Hank");
        row.put("age", "29");
        row.put("job", "engineer");
        rows.add(row);

        row = new HashMap<>();
        row.put("username", "Bob");
        row.put("age", "31");
        row.put("job", "architect");
        rows.add(row);

        row = new HashMap<>();
        row.put("username", "Loy");
        row.put("age", "26");
        row.put("job", "student");
        rows.add(row);

        model.addAttribute("cols", cols);
        model.addAttribute("rows", rows);

        return "table/table";
    }
}
