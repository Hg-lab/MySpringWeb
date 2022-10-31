package com.kideveloper.my_spring_web.dart.dto.doc;

public enum DocType {

    BS("BS"),     // Business Statement, 재무상태표
    IS("IS"),     // Income Statement, 손익계산서
    CIS("CIS"),    // Common Income Statement, 포괄손익계산서
    CF("CF"),     // Cash Flow, 현금흐름표
    SCE("SCE")     // Statement Change in Equity, 자본 변동표
    ;

    DocType(String docType) {
    }
}
