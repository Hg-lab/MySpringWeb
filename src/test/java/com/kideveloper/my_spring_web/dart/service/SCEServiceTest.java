package com.kideveloper.my_spring_web.dart.service;

import com.kideveloper.my_spring_web.dart.dto.DartRequestDTO;
import com.kideveloper.my_spring_web.dart.dto.doc.DocType;
import com.kideveloper.my_spring_web.dart.dto.doc.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;

import java.util.LinkedHashMap;
import java.util.List;

class SCEServiceTest {
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

        DartService dartService = new DartServiceImpl();
        Response response = dartService.callApi(requestDTO);
        for (DocType key : response.keySet()) {
            if(key == DocType.BS) {

                for (int i = 0; i < response.get(key).getCells().size(); i++) {
                    String columnName = response.get(key).getCells().get(i).getColumn().getColumnName();
//                    String rowName = response.get(key).getCells().get(i).getRowName();
                    String value = response.get(key).getCells().get(i).getValue();

//                    System.out.println("columnName = " + columnName + " / " + " rowName = " + rowName + "  " + value);
                }
            }
        }
        

    }

}