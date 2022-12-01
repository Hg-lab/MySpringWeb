package com.kideveloper_dart.my_spring_web.dart.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.request.OpenDartRequestDTO;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.response.APIFinStatsDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Map;

@Component
public class DartAPI {

    private static final String SINGLE_CORP_ALL_DOCS_URL = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";
    private static final String DART_API_KEY = "19aaf3c6797c1147005d8ba9673c5250d975d111";
    private static final String REQUEST_CODE_FOR_A_YEAR = "11011";

    public List<APIFinStatsDTO> callAPI(OpenDartRequestDTO openDartRequestDTO){

        UriComponents uriComponents = buildUri(openDartRequestDTO);
        return getApiResponseList(uriComponents);

    }

    private UriComponents buildUri(OpenDartRequestDTO openDartRequestDTO) {
        return UriComponentsBuilder.fromHttpUrl(SINGLE_CORP_ALL_DOCS_URL)
                .queryParam("crtfc_key", DART_API_KEY)
                .queryParam("corp_code", openDartRequestDTO.getCorpCode())
                .queryParam("bsns_year", openDartRequestDTO.getBsnsYear())
                .queryParam("reprt_code", REQUEST_CODE_FOR_A_YEAR)
                .queryParam("fs_div", openDartRequestDTO.getFsDiv())
                .build();
    }

    private List getApiResponseList(UriComponents uriComponents){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<>(new HttpHeaders());

        Map apiResponseBody = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, Map.class)
                .getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        List<APIFinStatsDTO> apiFinStatsDTOS;
        try {
            String apiResponse = objectMapper.writeValueAsString(apiResponseBody.get("list"));
            apiFinStatsDTOS = objectMapper.readValue(apiResponse, new TypeReference<List<APIFinStatsDTO>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return apiFinStatsDTOS;
    }
}
