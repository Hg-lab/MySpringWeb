package com.kideveloper.my_spring_web.dart.dto.doc;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Column {

    private final String id = UUID.randomUUID().toString();
    private String columnName;
    private Integer order;
    private Column rootColumn;
    private Column parentColumn;
    private List<Column> childColumns = new ArrayList<>();
    private Integer depth;
}
