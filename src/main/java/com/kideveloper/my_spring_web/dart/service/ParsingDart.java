package com.kideveloper.my_spring_web.dart.service;

import com.kideveloper.my_spring_web.dart.dto.doc.Response;

import java.util.LinkedHashMap;
import java.util.List;

public interface ParsingDart {

    // write docs
    abstract Response writeDocs(List<LinkedHashMap<String, String>> list);

}
