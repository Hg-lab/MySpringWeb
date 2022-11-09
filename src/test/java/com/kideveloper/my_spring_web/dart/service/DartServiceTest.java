package com.kideveloper.my_spring_web.dart.service;

import com.kideveloper.my_spring_web.dart.dto.DartRequestDTO;
import com.kideveloper.my_spring_web.dart.dto.doc.DocType;
import com.kideveloper.my_spring_web.dart.dto.doc.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DartServiceTest {
    private static final String URL = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";
    private static final String API_KEY = "19aaf3c6797c1147005d8ba9673c5250d975d111";

    @Test
    void dartService() {
        DartRequestDTO requestDTO = new DartRequestDTO(
                "00126380",
                "005930",
                "2021",
                "11011",
                "OFS"
        );

        

    }

    @Test
    void test() {
        DartRequestDTO requestDTO = new DartRequestDTO(
                "00126380",
                "005930",
                "2021",
                "11012",
                "CFS"
        );



    }

}