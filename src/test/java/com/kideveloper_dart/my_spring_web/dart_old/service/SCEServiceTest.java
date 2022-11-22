package com.kideveloper_dart.my_spring_web.dart_old.service;

import com.kideveloper_dart.my_spring_web.dart_old.dto.DartRequestDTO;
import com.kideveloper_dart.my_spring_web.dart_old.dto.doc.Column;
import com.kideveloper_dart.my_spring_web.dart_old.dto.doc.DocType;
import com.kideveloper_dart.my_spring_web.dart_old.dto.doc.Response;
import com.kideveloper_dart.my_spring_web.model.CorpCode;
import com.kideveloper_dart.my_spring_web.repository.CorpCodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class SCEServiceTest {
    private static final String URL = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";
    private static final String API_KEY = "19aaf3c6797c1147005d8ba9673c5250d975d111";

    @Autowired
    private CorpCodeRepository corpCodeRepository;



    @Test
    void dartService() {
        DartRequestDTO requestDTO = new DartRequestDTO(
                "00126380",
                "005930",
                "2021",
                "11011",
                "OFS"
        );
//        DartService dartService = new DartServiceImpl();
//        Response response = dartService.callApi(requestDTO);

    }

    @Test
    void AllCorpSameSCEColumns() {
        Set<String> columnNameSet = new HashSet<>();
        columnNameSet.add("자본");
        columnNameSet.add("자본금");
        columnNameSet.add("기타자본구성요소");
        columnNameSet.add("별도재무제표");
        columnNameSet.add("이익잉여금");
        columnNameSet.add("주식발행초과금");
        columnNameSet.add("자기주식");
        columnNameSet.add("자본잉여금");
        columnNameSet.add("기타포괄손익누계액");
        columnNameSet.add("비지배지분");
        columnNameSet.add("납입자본");
        columnNameSet.add("기타자본");
        columnNameSet.add("기타불입자본");
        columnNameSet.add("기타자본항목");
        columnNameSet.add("자본조정");
        columnNameSet.add("이익잉여금(결손금)");
        columnNameSet.add("기타자본조정");
        columnNameSet.add("비지배주주지분");
        columnNameSet.add("감자차익");
        DartRequestDTO requestDTO = new DartRequestDTO(
                "00126380",
                "005930",
                "2021",
                "11011",
                "OFS"
        );
        List<CorpCode> corpCodes = corpCodeRepository.findAll();
        int i = 0;
        for (CorpCode corpCode : corpCodes) {
            requestDTO.setCorpCode(corpCode.getCorpCode());
            DartService dartService = new DartServiceImpl();
            Response response;
            try{
                response = dartService.callApi(requestDTO);
            } catch (Exception e) {
                continue;
            }
            if(i++ == 900) break;

            List<Column> columns = response.get(DocType.SCE).getColumns();
            for (Column column : columns) {
                String columnName = column.getColumnName();
                if(!columnNameSet.contains(columnName)) System.out.println("columnName = " + columnName);
                Assertions.assertTrue(columnNameSet.contains(columnName));
            }

        }
    }
}