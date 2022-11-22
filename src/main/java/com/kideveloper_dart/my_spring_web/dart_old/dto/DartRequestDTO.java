package com.kideveloper_dart.my_spring_web.dart_old.dto;

import lombok.Data;

@Data
public class DartRequestDTO {

    private String corpCode;
    private final String stockCode;
    private final String businessYear;
    private final String reportCode;
    private final String fsDiv;

    public DartRequestDTO(String corpCode, String stockCode, String businessYear, String reportCode, String fsDiv) {
        this.corpCode = corpCode;
        this.stockCode = stockCode;
        this.businessYear = businessYear;
        this.reportCode = reportCode;
        this.fsDiv = fsDiv;
    }
}
