package com.kideveloper.my_spring_web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kideveloper.my_spring_web.model.FnlttSinglAcnt;
import com.kideveloper.my_spring_web.repository.CorpCodeRepository;
import com.kideveloper.my_spring_web.service.CallCompanyJsonService;
import com.kideveloper.my_spring_web.service.CallFnlttSinglAcntAllJsonService;
import com.kideveloper.my_spring_web.service.ParseSCEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/callapi/")
public class CallAPIController {

    @Autowired
    private CorpCodeRepository corpCodeRepository;

    @Autowired
    private CallCompanyJsonService callCompanyJsonService;

    @Autowired
    private CallFnlttSinglAcntAllJsonService callFnlttSinglAcntAllJson;

    @Autowired
    private ParseSCEService parseSCEService;

    @GetMapping("/")
    public String redirectCallApi() {
        return "callapi/identity";
    }

    @GetMapping("/identity")
    public String identity(Model model,
                           @RequestParam(required = false) String corp_code,
                           @RequestParam(required = false) String stock_code,
                           @RequestParam(required = false) String bsns_year,
                           @RequestParam(required = false) String reprt_code,
                           @RequestParam(required = false) String fs_div
                           ) throws JsonProcessingException {
        HashMap<String,Object> result = new HashMap<String, Object>();
        HashMap<String, FnlttSinglAcnt> result2 = new HashMap<String,FnlttSinglAcnt>();
        FnlttSinglAcnt deserializedFnlttSinglAcnt = new FnlttSinglAcnt();
        Object parsedReslut = null;
        Object columnsOfSCE = null;

        try {

            if(stock_code != null) {
                corp_code = corpCodeRepository.findByStockCode(stock_code).getCorpCode();
            }

            if(bsns_year !=null
            && reprt_code!=null
            && fs_div    !=null) {
                result2 = new LinkedHashMap<String,FnlttSinglAcnt>(callFnlttSinglAcntAllJson.callFnlttSinglAcntAllJson(corp_code,bsns_year,reprt_code,fs_div));
                Map<String,Integer> numOfSjDiv = callFnlttSinglAcntAllJson.getNumOfSjDiv();
                model.addAttribute("numOfSjDiv", numOfSjDiv);

                parsedReslut = parseSCEService.parseSCE(result2,numOfSjDiv);
                columnsOfSCE = parseSCEService.parseSCEAllColumns(result2);
            }

            result = new HashMap<String, Object>(callCompanyJsonService.callCompanyJson(corp_code));

            if(corp_code != null) {
                model.addAttribute("jsonMap", result);
            }

            System.out.println(parsedReslut);
            model.addAttribute("jsonMap2", result2);
            model.addAttribute("parsedResult",parsedReslut);
            model.addAttribute("columnsOfSCE",columnsOfSCE);

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

