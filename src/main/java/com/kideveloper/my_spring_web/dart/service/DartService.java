package com.kideveloper.my_spring_web.dart.service;

import com.kideveloper.my_spring_web.dart.dto.DartRequestDTO;
import com.kideveloper.my_spring_web.dart.dto.doc.Response;
import org.springframework.web.util.UriComponents;

import java.util.List;

public interface DartService {

    Response callApi(DartRequestDTO dartRequestDTO);

    UriComponents buildUri(DartRequestDTO dartRequestDTO);

    List getApiResponseList(UriComponents uriComponents);


}
