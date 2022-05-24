package com.kideveloper.my_spring_web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public String identity(Model model) throws JsonProcessingException {

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

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"
                    +"crtfc_key=19aaf3c6797c1147005d8ba9673c5250d975d111&corp_code=00126380").build();

            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            //resultMap = <200,{status=000, message=정상, corp_code=00126380, corp_name=삼성전자(주), corp_name_eng=SAMSUNG ELECTRONICS CO,.LTD, stock_name=삼성전자, stock_code=005930, ceo_nm=한종희, 경계현, corp_cls=Y, jurir_no=1301110006246, bizr_no=1248100998, adres=경기도 수원시 영통구  삼성로 129 (매탄동), hm_url=www.sec.co.kr, ir_url=, phn_no=031-200-1114, fax_no=031-200-7538, induty_code=264, est_dt=19690113, acc_mt=12},[Cache-Control:"no-cache", "no-store", Connection:"keep-alive", Set-Cookie:"WMONID=RrS8doMUsvE; Expires=Wed, 24-May-2023 23:6:47 GMT; Path=/", Pragma:"no-cache", Expires:"Thu, 01 Jan 1970 00:00:00 GMT", Date:"Tue, 24 May 2022 14:06:47 GMT", Content-Type:"application/json;charset=UTF-8", Transfer-Encoding:"chunked"]>


            // resultMap -> HashMap<String, Object> result 의 body에 put
            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

            //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writeValueAsString(resultMap.getBody());

            // 단건 field명으로 파싱
            JsonNode jsonNode = mapper.readTree(jsonInString);
            System.out.println(jsonNode.get("stock_name"));


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
        model.addAttribute("jsonInString", jsonInString);

        HashMap<String, String> rtnModelMap = new HashMap<>();

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

