package com.kideveloper.my_spring_web.dart.dto.doc;

import lombok.Data;

import java.util.HashMap;

// DartService -> DartController
@Data
public class Response extends HashMap<DocType, Doc> {
}
