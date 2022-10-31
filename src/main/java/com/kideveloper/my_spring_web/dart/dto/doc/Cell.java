package com.kideveloper.my_spring_web.dart.dto.doc;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cell {

    private Column column;
    private Row row;
    private String value;
}
