package com.kideveloper.my_spring_web.dart.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kideveloper.my_spring_web.dart.dto.*;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

// TODO: 2022/10/29 Interface 분리
public class DartServiceOldImpl implements DartServiceOld {

    // TODO: 2022/10/29 - API KEY 파기 및 보안 코딩
    private static final String URL = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json";
    private static final String API_KEY = "19aaf3c6797c1147005d8ba9673c5250d975d111";


    // TODO: 2022/10/30 return type DartResponseDTO 로 변경
    public DartResponseDTO callApi(DartRequestDTO requestDTO) {

        UriComponents uriComponents = buildUri(requestDTO);

        ResponseEntity<Map> apiResult = getApiResult(uriComponents.toString());

        List<ApiResultRowMap> bodyList = getBodyList(apiResult);

        DocTermList docTermList = getTermList(bodyList);
        BasicFinancialState basicFinancialState = getRows(bodyList);

        DartResponseDTO dartResponseDTO = new DartResponseDTO();
        dartResponseDTO.setDocTermList(docTermList);
        dartResponseDTO.setBizState((BizState) basicFinancialState);

        return dartResponseDTO;


    }

    // TODO: 2022/10/30 데이터모델화
    private BizState getRows(List<ApiResultRowMap> bodyList) {
        BizState basicFinancialState = new BizState();
        for (Map<String, String> row : bodyList) {

            // TODO: 2022/10/30 SCE 를 제외한 나머지 문서들 담아주기
            if(row.get("sj_div").equals("BS")) {
                FSrow.FSrowBuilder builder = FSrow.builder();
                FSrow returnRow = builder.order(row.get("ord"))
                        .accountName(row.get("sj_nm"))
                        .thisTermAmount(row.get("thstrm_amount"))
                        .fromTermAmount(row.get("frmtrm_amount"))
                        .beforeTermAmount(row.get("bfefrmtrm_amount")).build();

                basicFinancialState.add(returnRow);
            }
        }

        return basicFinancialState;
    }


    public UriComponents buildUri(DartRequestDTO requestDTO) {
        return UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("crtfc_key", API_KEY)
                .queryParam("corp_code", requestDTO.getCorpCode())
                .queryParam("bsns_year", requestDTO.getBusinessYear())
                .queryParam("reprt_code", requestDTO.getReportCode())
                .queryParam("fs_div", requestDTO.getFsDiv())
                .build();
    }

    public ResponseEntity getApiResult(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);
        return resultMap;
    }


    @SneakyThrows
    public List getBodyList(ResponseEntity<Map> apiResult) {

        ObjectMapper mapper = new ObjectMapper();

        String resultString = mapper.writeValueAsString(apiResult.getBody().get("list"));

        List<ApiResultRowMap> rowList = mapper.readValue(resultString, new TypeReference<List<ApiResultRowMap>>() {});

        return rowList;
    }


    public DocTermList getTermList(List<ApiResultRowMap> bodyList) {
        DocTermList docTermList = new DocTermList();

        String thisTerm = bodyList.get(0).get("thstrm_nm");
        String fromTerm = bodyList.get(0).get("frmtrm_nm");
        String beforeTerm = bodyList.get(0).get("bfefrmtrm_nm");

        docTermList.add(thisTerm);
        docTermList.add(fromTerm);
        docTermList.add(beforeTerm);

        return docTermList;
    }

}
