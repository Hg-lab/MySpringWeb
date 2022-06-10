package com.kideveloper.my_spring_web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kideveloper.my_spring_web.repository.CommonCodeRepository;
import com.kideveloper.my_spring_web.repository.CorpCodeRepository;
import com.kideveloper.my_spring_web.service.CallCompanyJsonService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CorpCodeRepository corpCodeRepository;

    @Autowired
    private CallCompanyJsonService callCompanyJsonService;

    @GetMapping("/")
    public String redirectCallApi() {
        return "callapi/identity";
    }

    @GetMapping("/identity")
    public String identity(Model model,
                           @RequestParam(required = false) String corp_code,
                           @RequestParam(required = false) String stock_code) throws JsonProcessingException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            result = new HashMap<String, Object>(callCompanyJsonService.callCompanyJson(corp_code));

            if(stock_code != null) {
                String corp_code1 = corpCodeRepository.findByStockCode(stock_code).getCorpCode();
                System.out.println("***********************Got stock_code !! : " + stock_code);
                System.out.println("***********************Got stock_code !! : " + corp_code1);
            }


            model.addAttribute("jsonMap", result);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
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

