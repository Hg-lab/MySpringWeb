package com.kideveloper.my_spring_web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Array;
import java.util.*;

@Controller
@RequestMapping("/callapi/")
public class CallAPIController {
    @GetMapping("/")
    public String redirectCallApi() {
        return "callapi/identity";
    }

    @GetMapping("/identity")
    public String identity(Model model, @RequestParam(required = false) String corp_code) throws JsonProcessingException {

        HashMap<String, Object> result = new HashMap<String, Object>();

        String jsonInString = "";

        try {

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000); //타임아웃 설정 5초
            factory.setReadTimeout(5000);//타임아웃 설정 5초
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "https://opendart.fss.or.kr/api/company.json";
            String crtfc_key = "19aaf3c6797c1147005d8ba9673c5250d975d111";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(
                    url+"?crtfc_key="+crtfc_key+"&corp_code="+corp_code).build();

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

            // model에는 map을 통채로 attribute로 넣으면 되어서 이부분 삭제
//            Set<String> keySet = jsonMap.keySet();
//            for(String key : keySet) {
//                model.addAttribute(key, jsonMap.get(key));
//            }

            model.addAttribute("jsonMap", jsonMap);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println("dfdfdfdf");
            System.out.println(e.toString());

        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            System.out.println(e.toString());
        }

        return "callapi/identity";
    }

    @PostMapping(value = "/identity", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String identityPostJson(@RequestBody Model model) {
        System.out.println("***********************Got Post API Json !! : " + model.getAttribute("test"));
        return "callapi/identity";
    }

    @PostMapping(value = "/identity", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String identityPost(HttpServletRequest httpServletRequest, Model model) {
        System.out.println("***********************Got Post API !! : " + httpServletRequest.getParameter("test"));
        model.addAttribute("response",httpServletRequest.getParameter("test"));
        return "callapi/identity";
    }

}

