package com.kideveloper.my_spring_web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kideveloper.my_spring_web.model.FnlttSinglAcnt;
import com.kideveloper.my_spring_web.repository.CommonCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CallFnlttSinglAcntAllJsonService {
    @Autowired
    private CommonCodeRepository repository;

    public Map<String,Object> callFnlttSinglAcntAllJson(String corp_code,
                                                        String bsns_year,
                                                        String reprt_code,
                                                        String fs_div
                                                        ) throws JsonProcessingException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        String jsonInString = "";

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000); //타임아웃 설정 5초
        factory.setReadTimeout(5000);//타임아웃 설정 5초
        RestTemplate restTemplate = new RestTemplate(factory);

        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        String url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";

        String crtfc_key = repository.findFirstByComCd("crtfc_key_dart_api").getComCdVal();

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(
                url +"?crtfc_key="+crtfc_key
                    +"&corp_code="+corp_code
                    +"&bsns_year="+bsns_year
                    +"&reprt_code="+reprt_code
                    +"&fs_div="+fs_div
        ).build();

        //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
        ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);


        // resultMap -> HashMap<String, Object> result 의 body에 put
        result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
        result.put("header", resultMap.getHeaders()); //헤더 정보 확인
        result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

        //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(resultMap.getBody());
        Map<String,Object> jsonMap = mapper.readValue(jsonInString, new TypeReference<Map<String,Object>>(){});

        // Map<String,Map> 형태로 리턴해야함
        // {ord, {ord=n의 재무제표}}
        ArrayList<Object> list = (ArrayList<Object>) jsonMap.get("list");
        HashMap<String,Object> hm = new HashMap<String,Object>();
        String strOrd = "1";
        for(Object o : list) {
            hm.put(strOrd,o);
            int strOrdToInt = Integer.parseInt(strOrd);
            strOrdToInt++;
            strOrd = Integer.toString(strOrdToInt);
        }

        // body 구조: message, list, status

//        FnlttSinglAcnt deserializedFnlttSinglAcnt = mapper.readValue(jsonInString, FnlttSinglAcnt.class);


        return hm;

    }

}
