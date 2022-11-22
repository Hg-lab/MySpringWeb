package com.kideveloper_dart.my_spring_web.cinema;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/example")
public class CinemaExampleController {
    
    private static final String CINEMA_FILE_PATH = "/Users/hanhyunkyu/Downloads/개발/my_spring_web/src/main/java/com/kideveloper/my_spring_web/cinema/2d-table-cinema-example.json";

    @GetMapping("/cinema")
    public String getTable(Model model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = Paths.get(CINEMA_FILE_PATH).toFile();
        List<Country> countries = mapper.readValue(file, List.class);

        model.addAttribute("countries", countries);


        return "table/cinema-2d-table";
    }
}
