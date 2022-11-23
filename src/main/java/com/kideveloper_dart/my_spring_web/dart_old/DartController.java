package com.kideveloper_dart.my_spring_web.dart_old;


import com.kideveloper_dart.my_spring_web.dart_old.dto.DartRequestDTO;
import com.kideveloper_dart.my_spring_web.dart_old.dto.doc.DocType;
import com.kideveloper_dart.my_spring_web.dart_old.dto.doc.Response;
import com.kideveloper_dart.my_spring_web.dart_old.service.DartService;
import com.kideveloper_dart.my_spring_web.dart_old.service.DartServiceImpl;
import com.kideveloper_dart.my_spring_web.repository.CorpCodeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

//@Controller
@RequestMapping("/dart_old")
public class DartController {

    // TODO: 2022/10/29 Thread-safe 를 위한 final 설정
    // TODO: 2022/10/29 DTO scope 설정
    private CorpCodeRepository corpCodeRepository;
    private DartRequestDTO dartRequestDTO;
    private DartService dartService = new DartServiceImpl();

    @GetMapping("/docs")
    public String getDart(Model model,
                          @RequestParam Map<String, String> paramMap) {

        String corpCode = paramMap.get("corp_code");
        String stockCode = paramMap.get("stock_code");
        String businessYear = paramMap.get("bsns_year");
        String reportCode = paramMap.get("reprt_code");
        String fsDiv = paramMap.get("fs_div");

        dartRequestDTO = new DartRequestDTO(corpCode, stockCode, businessYear, reportCode, fsDiv);
        Response response = dartService.callApi(dartRequestDTO);

        model.addAttribute("BS", response.get(DocType.BS));
        model.addAttribute("IS", response.get(DocType.IS));
        model.addAttribute("CIS", response.get(DocType.CIS));
        model.addAttribute("CF", response.get(DocType.CF));
        model.addAttribute("SCE", response.get(DocType.SCE));



        return "dart/docs";
    }

}
