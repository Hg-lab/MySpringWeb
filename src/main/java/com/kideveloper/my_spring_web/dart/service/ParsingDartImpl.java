package com.kideveloper.my_spring_web.dart.service;

import com.kideveloper.my_spring_web.dart.dto.doc.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

// request scope
public class ParsingDartImpl implements ParsingDart {

    // TODO: 2022/11/02 Doc 객체들 Factory method 로 의존성 관리
    private Doc BusinessStatement = new Doc(DocType.BS);
    private Doc IncomeStatement = new Doc(DocType.IS);
    private Doc CommonIncomeStatement = new Doc(DocType.CIS);
    private Doc CashFlow = new Doc(DocType.CF);
    private Doc StatementChangeInEquity = new Doc(DocType.SCE);
    private Response response = new Response();
    private Set<Column> sceColumSet = new LinkedHashSet<>();
    private Set<Row> sceRowSet = new LinkedHashSet<>();
    private List<Cell> sceAllCellList = new LinkedList<>();
    
    // write docs
    @Override
    public Response writeDocs(List<LinkedHashMap<String, String>> list) {

        initResponse();

        for (LinkedHashMap<String, String> dataMap : list) {
            DocType docType = DocType.valueOf(dataMap.get("sj_div"));

            if(docType == DocType.SCE) {
                writeSCE(dataMap);
            }

            else {
                writeFinanceState(dataMap);
            }

        }

        putSCEColumns();
        putSCERows();

        return response;
    }

    private void initResponse() {
        response.put(BusinessStatement.getDocType(), BusinessStatement);
        response.put(IncomeStatement.getDocType(), IncomeStatement);
        response.put(CommonIncomeStatement.getDocType(), CommonIncomeStatement);
        response.put(CashFlow.getDocType(), CashFlow);
        response.put(StatementChangeInEquity.getDocType(), StatementChangeInEquity);
    }


    // TODO: 2022/11/01 refactoring
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

        row.getCells().add(thisCell);
        row.getCells().add(fromCell);
        row.getCells().add(beforeFromCell);

    }

    private void writeSCE(LinkedHashMap<String, String> dataMap) {
        DocType docType = DocType.valueOf(dataMap.get("sj_div"));

        // TODO: 2022/11/02 정규표현식
        String accountDetail = dataMap.get("account_detail");
        accountDetail = accountDetail.replaceAll("[\\[\\w\\]]", "");
        accountDetail = accountDetail.replaceAll("\\s\\|", "\\|");
        List<String> columnNameList = Arrays.asList(accountDetail.split("\\|"));

        String parentColumnName;
        String rootColumnName;

        // Build Column
        for (int i = 0; i < columnNameList.size(); i++) {
            int depth;
            if(columnNameList.size() == 1) depth = 0;
            else depth = i + 1;
            String columnName = columnNameList.get(i).trim();
            Column column = Column.builder()
                    .columnName(columnName)
                    .order(Integer.valueOf(dataMap.get("ord")))
//                .rootColumn(null)
//                .parentColumn(null)
                    .depth(depth)
                    .build();
            if(!sceColumSet.contains(column)) sceColumSet.add(column);

        }


//        String check = dataMap.get("account_id") + " / " +
//                        dataMap.get("account_nm") + " / " +
//                        accountDetail + " / " +
//                        dataMap.get("thstrm_amount") + " / " +
//                        dataMap.get("frmtrm_amount") + " / " +
//                        dataMap.get("bfefrmtrm_amount") + " / " +
//                        dataMap.get("ord");

//        System.out.println(check);

        // Build Row
        Row row = Row.builder()
                .docType(DocType.valueOf(dataMap.get("sj_div")))
                .order(Integer.valueOf(dataMap.get("ord")))
                .rowAccountId(dataMap.get("account_id"))
                .rowName(dataMap.get("account_nm"))
                .build();
        sceRowSet.add(row);
        
//        Cell cell = Cell.builder().column(column).row(row).value(dataMap.get("bfefrmtrm_amount")).build();
//        sceAllCellList.add(cell);
        
//        response.get(docType).getRows().add(row);
//        System.out.println("dataMap = " + dataMap);

    }

    private void putSCEColumns() {
        for (Column column : sceColumSet) {
            response.get(DocType.SCE).getColumns().add(column);
        }
    }

    private void putSCERows() {
        for (int i = 0; i < 3; i++) {
            for (Row row : sceRowSet) {
                Row newRow = row.deepCopy();
                if(row.getRowAccountId().equals("dart_EquityAtBeginningOfPeriod"))
                    newRow.setRowName(row.getRowName() + " (" + Integer.toString(i) + "기)");
                response.get(DocType.SCE).getRows().add(newRow);
            }
        }
    }

}
