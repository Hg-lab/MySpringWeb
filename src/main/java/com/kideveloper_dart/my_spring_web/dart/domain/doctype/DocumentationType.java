package com.kideveloper_dart.my_spring_web.dart.domain.doctype;

public enum DocumentationType {
    BS("재무상태표"),     // Business Statement, 재무상태표
    IS("손익계산서"),     // Income Statement, 손익계산서
    CIS("포괄손익계산서"),    // Common Income Statement, 포괄손익계산서
    CF("현금흐름표"),     // Cash Flow, 현금흐름표
    SCE("자본변동표")     // Statement Change in Equity, 자본 변동표
    ;

    DocumentationType(String docType) {
    }

}
