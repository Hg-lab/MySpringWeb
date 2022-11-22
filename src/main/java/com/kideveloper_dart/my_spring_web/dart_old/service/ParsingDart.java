package com.kideveloper_dart.my_spring_web.dart_old.service;

import com.kideveloper_dart.my_spring_web.dart_old.dto.doc.Response;

import java.util.LinkedHashMap;
import java.util.List;

public interface ParsingDart {

    // write docs
    abstract Response writeDocs(List<LinkedHashMap<String, String>> list);

}
