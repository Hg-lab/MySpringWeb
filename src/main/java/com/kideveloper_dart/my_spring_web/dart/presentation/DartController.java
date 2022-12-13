package com.kideveloper_dart.my_spring_web.dart.presentation;

import com.kideveloper_dart.my_spring_web.dart.application.DartService;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.DartDocsRequestDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.doctype.DocumentationType;
import com.kideveloper_dart.my_spring_web.dart.presentation.dto.request.DartDocsRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import static com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO.*;

@Controller
@RequestMapping("/dart")
@RequiredArgsConstructor
public class DartController {

    private final DartService dartService;

    @GetMapping
    public String showDocumentsTables(Model model, DartDocsRequest dartDocsRequest){
        try {

        DartDocsRequestDTO dartDocsRequestDTO = DartDocsRequestDTO.builder()
                .stockCode(dartDocsRequest.getStock_code())
                .corpCode(dartDocsRequest.getCorp_code())
                .businessYear(dartDocsRequest.getBsns_year())
                .financialStatDiv(dartDocsRequest.getFs_div())
                .documentationType(DocumentationType.valueOf(dartDocsRequest.getDoc_type()))
                .build();
            DartDocsResponseDTO docsResponseDTO = dartService.getDocs(dartDocsRequestDTO);
            PriorityQueue<ColumnHeadDTO> columnHeadDTOList = docsResponseDTO.getColumnHeadDTOList();

            Map<RowHeadDTO, Map<ColumnHeadDTO, CellDTO>> rowColumnCellDTOMap = docsResponseDTO.getRowColumnCellDTOMap();
            model.addAttribute("columns", columnHeadDTOList);
            model.addAttribute("data", rowColumnCellDTOMap);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("status", "fail");
            model.addAttribute("message", "조회할 수 없는 종목코드입니다.");
            return "dart/dart";
        }

        model.addAttribute("status", "ok");
        model.addAttribute("message", "정상적으로 조회하였습니다.");
        return "dart/dart";
    }
}
