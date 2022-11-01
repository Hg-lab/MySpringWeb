package com.kideveloper.my_spring_web.dart.dto.doc;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Row {

    private DocType docType;
    private Integer order = 0;
    private String rowName;

    private final List<Cell> cells = new ArrayList<>();
}
