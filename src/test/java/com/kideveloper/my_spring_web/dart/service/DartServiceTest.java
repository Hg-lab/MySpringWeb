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

        DartService dartService = new DartServiceImpl();
        Response response = dartService.callApi(requestDTO);
        for (DocType key : response.keySet()) {
            if(key == DocType.BS) {

                for (int i = 0; i < response.get(key).getCells().size(); i++) {
                    String columnName = response.get(key).getCells().get(i).getColumn().getColumnName();
                    String rowName = response.get(key).getCells().get(i).getRow().getRowName();
                    String value = response.get(key).getCells().get(i).getValue();

                    System.out.println("columnName = " + columnName + " / " + " rowName = " + rowName + "  " + value);
                }
            }
        }
        

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

        DartService dartService = new DartServiceImpl();
        UriComponents uriComponents = dartService.buildUri(requestDTO);


        List list = dartService.getApiResponseList(uriComponents);
        for (Object o : list) {
            LinkedHashMap<String,String> map = (LinkedHashMap<String, String>) o;

            // 1을을 제외한 분기, 반기는 add_amount도 값이 있다.
//            String amount = map.get("thstrm_amount");
//            String add_amount = map.get("thstrm_add_amount");
//            if (add_amount != null && add_amount.equals(add_amount)) {
//                System.out.println("amount = " + amount + " add_amount = " + add_amount);
//            }

            // 1을 제외한 분기, 반기는 from term amount 만 비어있다.
//            String amount = map.get("frmtrm_amount");
//            String q_amount = map.get("frmtrm_q_amount");
//            if (q_amount != null && q_amount.equals(q_amount)) {
//                System.out.println("amount = " + amount + " q_amount = " + q_amount);
//            }

            // 분기, 반기에는 둘 금액이 다르다
//            String q_amount = map.get("frmtrm_q_amount");
//            String add_amount = map.get("frmtrm_add_amount");
//            if (q_amount != null && !q_amount.equals(add_amount)) {
//                System.out.println("q_amount = " + q_amount + " add_amount = " + add_amount);
//            }
        }


    }

}