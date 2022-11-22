package com.kideveloper_dart.my_spring_web.dart_old.dto.doc;

import lombok.Data;

import java.util.HashMap;

// DartService -> DartController
@Data
public class Response extends HashMap<DocType, Doc> {
    public Response() {
        Doc BusinessStatement = new Doc(DocType.BS);
        Doc IncomeStatement = new Doc(DocType.IS);
        Doc CommonIncomeStatement = new Doc(DocType.CIS);
        Doc CashFlow = new Doc(DocType.CF);
        Doc StatementChangeInEquity = new Doc(DocType.SCE);

        this.put(BusinessStatement.getDocType(), BusinessStatement);
        this.put(IncomeStatement.getDocType(), IncomeStatement);
        this.put(CommonIncomeStatement.getDocType(), CommonIncomeStatement);
        this.put(CashFlow.getDocType(), CashFlow);
        this.put(StatementChangeInEquity.getDocType(), StatementChangeInEquity);
    }
}
