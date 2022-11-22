package com.kideveloper_dart.my_spring_web.dart.presentation;


import com.kideveloper_dart.my_spring_web.dart.application.DartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dart-docs")
@RequiredArgsConstructor
public class DartController {

    private final DartService dartService;

    public String getDartDocsTables() {

        return "";
    }
}
