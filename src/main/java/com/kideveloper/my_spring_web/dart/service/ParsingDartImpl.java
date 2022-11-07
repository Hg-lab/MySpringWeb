package com.kideveloper.my_spring_web.dart.service;

import com.kideveloper.my_spring_web.dart.dto.doc.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

// request scope
public class ParsingDartImpl implements ParsingDart {

    // TODO: 2022/11/02 Doc 객체들 Factory method 로 의존성 관리

    private Response response = new Response();
    private Map<String, Set<String>> sceParentColumnNamesMap = new HashMap<>();
    private Map<String, Column> sceColumnMap = new HashMap<>();
    private Set<Row> sceRowSet = new LinkedHashSet<>();
    //    private Set<Row> sceRowSet = new LinkedHashSet<>();
    private Map<String, List<Cell>> sceColumNameCells = new HashMap<>();
    private Map<Row, List<Cell>> sceRowCells = new HashMap<>();
    private Map<Integer, List<Cell>> sceTermCells = new HashMap<>();

    // write docs
    @Override
    public Response writeDocs(List<LinkedHashMap<String, String>> list) {
        initResponse();

        for (LinkedHashMap<String, String> dataMap : list) {
            System.out.println("dataMap = " + dataMap);
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
        putSCECells();
        return response;
    }

    private void initResponse() {
        Doc BusinessStatement = new Doc(DocType.BS);
        Doc IncomeStatement = new Doc(DocType.IS);
        Doc CommonIncomeStatement = new Doc(DocType.CIS);
        Doc CashFlow = new Doc(DocType.CF);
        Doc StatementChangeInEquity = new Doc(DocType.SCE);

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
//                .row(row)
                .value(dataMap.get("thstrm_amount"))
                .build();
        Cell fromCell = Cell.builder()
                .column(fromTerm)
//                .row(row)
                .value(dataMap.get("frmtrm_amount"))
                .build();
        Cell beforeFromCell = Cell.builder()
                .column(beforeFromTerm)
//                .row(row)
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
        accountDetail = accountDetail.replaceAll("\\s", "");
        List<String> columnNameList = Arrays.asList(accountDetail.split("\\|"));

        String rootColumnName;

        // Build Column
        for (int i = 0; i < columnNameList.size(); i++) {
            int depth = i+1;
            boolean isRootColumn = false;
            if(columnNameList.size() == 1) {
                isRootColumn = true;
                depth = 0;
            }
            String columnName = columnNameList.get(i).trim();
            Column column = Column.builder()
                    .columnName(columnName)
                    .order(Integer.valueOf(dataMap.get("ord")))
                    .isRootColumn(isRootColumn)
//                .parentColumn(null)
                    .depth(depth)
                    .build();
            sceColumnMap.put(columnName, column);
        }
        String parentColumnName = columnNameList.get(0).trim();
        if(sceParentColumnNamesMap.get(parentColumnName) == null) sceParentColumnNamesMap.put(parentColumnName, new HashSet<>());
        sceParentColumnNamesMap.get(parentColumnName).addAll(columnNameList.subList(1, columnNameList.size()));


//        String check = dataMap.get("account_id") + " / " +
//                        dataMap.get("account_nm") + " / " +
//                        accountDetail + " / " +
//                        dataMap.get("thstrm_amount") + " / " +
//                        dataMap.get("frmtrm_amount") + " / " +
//                        dataMap.get("bfefrmtrm_amount") + " / " +
//                        dataMap.get("ord");
//
//        System.out.println(check);

        // Build Row
        Row row = Row.builder()
                .docType(DocType.valueOf(dataMap.get("sj_div")))
                .order(Integer.valueOf(dataMap.get("ord")))
                .rowAccountId(dataMap.get("account_id"))
                .rowName(dataMap.get("account_nm"))
                .build();
        sceRowSet.add(row);

        Cell cell1 = Cell.builder().value(dataMap.get("bfefrmtrm_amount")).build();
        Cell cell2 = Cell.builder().value(dataMap.get("frmtrm_amount")).build();
        Cell cell3 = Cell.builder().value(dataMap.get("thstrm_amount")).build();
        String thisColumnName = columnNameList.get(columnNameList.size()-1).trim();
        if (sceColumNameCells.get(thisColumnName) == null) sceColumNameCells.put(thisColumnName, new ArrayList<>());
        sceColumNameCells.get(thisColumnName).add(cell1);
        sceColumNameCells.get(thisColumnName).add(cell2);
        sceColumNameCells.get(thisColumnName).add(cell3);

        if (sceRowCells.get(row) == null) sceRowCells.put(row, new ArrayList<>());
        sceRowCells.get(row).add(cell1);
        sceRowCells.get(row).add(cell2);
        sceRowCells.get(row).add(cell3);

        if (sceTermCells.get(1) == null) sceTermCells.put(1, new ArrayList<>());
        if (sceTermCells.get(2) == null) sceTermCells.put(2, new ArrayList<>());
        if (sceTermCells.get(3) == null) sceTermCells.put(3, new ArrayList<>());

        sceTermCells.get(1).add(cell1);
        sceTermCells.get(2).add(cell2);
        sceTermCells.get(3).add(cell3);


//        response.get(docType).getRows().add(row);
//        System.out.println("dataMap = " + dataMap);

    }

    private void putSCEColumns() {
        for (String parentColumnName : sceParentColumnNamesMap.keySet()) {
            for (String childColumnName : sceParentColumnNamesMap.get(parentColumnName)) {
                Column parentColumn = sceColumnMap.get(parentColumnName);
                Column childColumn = sceColumnMap.get(childColumnName);
                sceColumnMap.get(childColumnName).setParentColumn(parentColumn);
                sceColumnMap.get(parentColumnName).getChildColumns().add(childColumn);
            }
        }
        for (Column column : sceColumnMap.values()) {
            response.get(DocType.SCE).getColumns().add(column);
        }
    }

    private void putSCERows() {
        for (int i = 0; i < 3; i++) {
            for (Row row : sceRowSet) {
                int term = i+1;
                row.setTerm(term);

                List<Cell> cells = sceTermCells.get(row.getTerm());
                row.getCells().addAll(cells);

                Row newRow = row.deepCopy();
                if(row.getRowAccountId().equals("dart_EquityAtBeginningOfPeriod"))
                    newRow.setRowName(row.getRowName() + " (" + Integer.toString(i) + "기)");
                response.get(DocType.SCE).getRows().add(newRow);
//                System.out.println(row);
            }
        }
    }


    private void putSCECells() {

        for (String columnName: sceColumNameCells.keySet()) {
            for (Cell cell : sceColumNameCells.get(columnName)) {
                cell.setColumn(sceColumnMap.get(columnName));
                response.get(DocType.SCE).getCells().add(cell);
            }
        }
        System.out.println("sceTermCells = " + sceTermCells);
    }
}
