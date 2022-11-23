package com.kideveloper_dart.my_spring_web.dart.infrastructure;

import com.kideveloper_dart.my_spring_web.dart.application.dto.request.CompanyDTO;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.response.APIFinStatsDTO;
import org.springframework.stereotype.Component;

@Component
public class DartAPI {

    private static final String SINGLE_CORP_ALL_DOCS_URL = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";
    private static final String DART_API_KEY = "19aaf3c6797c1147005d8ba9673c5250d975d111";

    public APIFinStatsDTO callAPI(CompanyDTO companyDTO) {

        APIFinStatsDTO apiFinStatsDTO = new APIFinStatsDTO();
        return apiFinStatsDTO;
    }
}
