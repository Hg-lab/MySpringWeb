package com.kideveloper_dart.my_spring_web.dart_old.service;

import com.kideveloper_dart.my_spring_web.dart_old.dto.DartRequestDTO;
import com.kideveloper_dart.my_spring_web.dart_old.dto.doc.Response;
import org.springframework.web.util.UriComponents;

import java.util.List;

public interface DartService {

    Response callApi(DartRequestDTO dartRequestDTO);

    UriComponents buildUri(DartRequestDTO dartRequestDTO);

    List getApiResponseList(UriComponents uriComponents);


}
