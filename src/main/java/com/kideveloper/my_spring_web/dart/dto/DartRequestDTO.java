package com.kideveloper.my_spring_web.dart.dto;

import lombok.Data;

@Data
public class DartRequestDTO {

    private final String corpCode;
    private final String stockCode;
    private final String businessYear;
    private final String reportCode;
    private final String fsDiv;
}
