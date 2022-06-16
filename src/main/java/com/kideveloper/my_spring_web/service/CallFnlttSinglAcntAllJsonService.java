package com.kideveloper.my_spring_web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kideveloper.my_spring_web.model.FnlttSinglAcnt;
import com.kideveloper.my_spring_web.repository.CommonCodeRepository;
import lombok.Data;
import lombok.Getter;
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

import java.util.*;

@Service
public class CallFnlttSinglAcntAllJsonService {

    @Autowired
    private CommonCodeRepository repository;

    // Front에서 rowspan을 위한 재무제표명별 갯수 static 변수
    @Getter
    static Map<String,Integer> numOfSjDiv;

    public Map<String, FnlttSinglAcnt> callFnlttSinglAcntAllJson(String corp_code,
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

        //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(resultMap.getBody());
        Map<String,Object> jsonMap = mapper.readValue(jsonInString, new TypeReference<Map<String,Object>>(){});

        String jsonInStringOnlyList = mapper.writeValueAsString(resultMap.getBody().get("list"));
        List<FnlttSinglAcnt> fnlttSinglAcntList = mapper.readValue(jsonInStringOnlyList, new TypeReference<List<FnlttSinglAcnt>>() {});
        numOfSjDiv = getCountSjNm(fnlttSinglAcntList);
        Map<String,FnlttSinglAcnt> hm1 = new LinkedHashMap<String,FnlttSinglAcnt>();
        String strOrd1 = "1";
        for(FnlttSinglAcnt f : fnlttSinglAcntList) {
            hm1.put(strOrd1,f);
            int strOrdToInt = Integer.parseInt(strOrd1);
            strOrdToInt++;
            strOrd1 = Integer.toString(strOrdToInt);
        }


        // Map<String,Map> 형태로 리턴해야함
        // body 구조: message, list, status
        ArrayList<Object> list = (ArrayList<Object>) jsonMap.get("list");
        Map<String,Object> hm = new LinkedHashMap<String,Object>();
        String strOrd = "1";
        for(Object o : list) {
            hm.put(strOrd,o);
//            System.out.println("key = " + strOrd + " / " + "object = " + o.toString());
            int strOrdToInt = Integer.parseInt(strOrd);
            strOrdToInt++;
            strOrd = Integer.toString(strOrdToInt);

//            String individualRowJsonToString = o.toString();
//            FnlttSinglAcnt fnlttSinglAcnt = mapper.readValue(individualRowJsonToString, FnlttSinglAcnt.class);
//            System.out.println(fnlttSinglAcnt.toString());
        }
        return hm1;
    }

    // BS : 재무상태표 IS : 손익계산서 CIS : 포괄손익계산서 CF : 현금흐름표 SCE : 자본변동표
    public Map<String,Integer> getCountSjNm(List<FnlttSinglAcnt> fnlttSinglAcntList) {
        Map<String,Integer> countMap = new LinkedHashMap<String,Integer>();
        for(FnlttSinglAcnt fnlttSinglAcnt : fnlttSinglAcntList) {
            String sj_div = fnlttSinglAcnt.getSj_div();
            countMap.put(sj_div, countMap.getOrDefault(sj_div,1)+1);
        }
        return countMap;
    }
}
