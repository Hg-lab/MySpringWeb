package com.kideveloper.my_spring_web.dart.service;

import com.kideveloper.my_spring_web.dart.dto.doc.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

// request scope
public class ParsingDartImpl implements ParsingDart {

    private Doc BusinessStatement = new Doc(DocType.BS);
    private Doc IncomeStatement = new Doc(DocType.IS);
    private Doc CommonIncomeStatement = new Doc(DocType.CIS);
    private Doc CashFlow = new Doc(DocType.CF);

    private Doc StatementChangeInEquity = new Doc(DocType.SCE);

    private Response response = new Response();
    // write docs
    @Override
    public Response writeDocs(List<LinkedHashMap<String, String>> list) {

        initResponse();

        for (LinkedHashMap<String, String> dataMap : list) {
            DocType docType = DocType.valueOf(dataMap.get("sj_div"));

            if(docType == DocType.SCE)
                writeSCE(dataMap);
            else
                writeFinanceState(dataMap);
        }

        return response;
    }

    private void initResponse() {
        response.put(BusinessStatement.getDocType(), BusinessStatement);
        response.put(IncomeStatement.getDocType(), IncomeStatement);
        response.put(CommonIncomeStatement.getDocType(), CommonIncomeStatement);
        response.put(CashFlow.getDocType(), CashFlow);
        response.put(StatementChangeInEquity.getDocType(), StatementChangeInEquity);
    }

    private void writeFinanceState(LinkedHashMap<String, String> dataMap) {
        DocType docType = DocType.valueOf(dataMap.get("sj_div"));
            // TODO: 2022/10/30 set Columns

        // generating Columns
        Column thisTerm = Column.builder()
                    .columnName(dataMap.get("thstrm_nm"))
                    .build();

        Column fromTerm = Column.builder()
                .columnName(dataMap.get("frmtrm_nm"))
                .build();

        Column beforeFromTerm = Column.builder()
                .columnName(dataMap.get("bfefrmtrm_nm"))
                .build();

        if(response.get(docType).getColumns().isEmpty()) {
            response.get(docType).getColumns().add(thisTerm);
            response.get(docType).getColumns().add(fromTerm);
            response.get(docType).getColumns().add(beforeFromTerm);
        }

        // generating Row
        Row row = Row.builder()
                .docType(DocType.valueOf(dataMap.get("sj_div")))
                .order(Integer.valueOf(dataMap.get("ord")))
                .rowName(dataMap.get("account_nm")).build();

        response.get(docType).getRows().add(row);

        // generating Cell
        Cell thisCell = Cell.builder()
                .column(thisTerm)
                .row(row)
                .value(dataMap.get("thstrm_amount"))
                .build();
        Cell fromCell = Cell.builder()
                .column(fromTerm)
                .row(row)
                .value(dataMap.get("frmtrm_amount"))
                .build();
        Cell beforeFromCell = Cell.builder()
                .column(beforeFromTerm)
                .row(row)
                .value(dataMap.get("bfefrmtrm_amount"))
                .build();

        response.get(docType).getCells().add(thisCell);
        response.get(docType).getCells().add(fromCell);
        response.get(docType).getCells().add(beforeFromCell);

    }

    private void writeSCE(LinkedHashMap<String, String> dataMap) {


    }
}
