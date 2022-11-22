package com.kideveloper_dart.my_spring_web.dart_old.service;

import com.kideveloper_dart.my_spring_web.dart_old.dto.DartRequestDTO;
import org.junit.jupiter.api.Test;

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