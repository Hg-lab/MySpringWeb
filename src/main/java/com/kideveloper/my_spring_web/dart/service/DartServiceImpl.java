package com.kideveloper.my_spring_web.dart.service;

import com.kideveloper.my_spring_web.dart.dto.DartRequestDTO;
import com.kideveloper.my_spring_web.dart.dto.doc.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

public class DartServiceImpl implements DartService{

    // TODO: 2022/10/29 - API KEY 파기 및 보안 코딩
    private static final String URL = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";
    private static final String API_KEY = "19aaf3c6797c1147005d8ba9673c5250d975d111";

    @Override
    public Response callApi(DartRequestDTO dartRequestDTO) {
        UriComponents uriComponents = buildUri(dartRequestDTO);

        List list = getApiResponseList(uriComponents);

        ParsingDart parsingDart = new ParsingDartImpl();

        return parsingDart.writeDocs(list);
    }

    @Override
    public UriComponents buildUri(DartRequestDTO dartRequestDTO) {
        return UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("crtfc_key", API_KEY)
                .queryParam("corp_code", dartRequestDTO.getCorpCode())
                .queryParam("bsns_year", dartRequestDTO.getBusinessYear())
                .queryParam("reprt_code", dartRequestDTO.getReportCode())
                .queryParam("fs_div", dartRequestDTO.getFsDiv())
                .build();
    }

    @Override
    public List getApiResponseList(UriComponents uriComponents) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<>(new HttpHeaders());
        List list = (List) restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, Map.class).getBody().get("list");

        return list;
    }

}
