package com.kideveloper_dart.my_spring_web.dart.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kideveloper_dart.my_spring_web.dart.application.DartService;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.DartDocsRequestDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO;
import com.kideveloper_dart.my_spring_web.dart.presentation.dto.request.DartDocsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dart")
@RequiredArgsConstructor
public class DartController {

    private final DartService dartService;

    @GetMapping
    public String showDocumentsTables(DartDocsRequest dartDocsRequest){

        DartDocsRequestDTO dartDocsRequestDTO = DartDocsRequestDTO.builder()
                .stockCode(dartDocsRequest.getStock_code())
                .corpCode(dartDocsRequest.getCorp_code())
                .businessYear(dartDocsRequest.getBsns_year())
                .financialStatDiv(dartDocsRequest.getFs_div())
                .build();
        DartDocsResponseDTO docsResponseDTO = dartService.getDocs(dartDocsRequestDTO);

        return "dart/dart";
    }
}
