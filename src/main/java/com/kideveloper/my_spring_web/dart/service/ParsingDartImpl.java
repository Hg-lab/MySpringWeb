package com.kideveloper.my_spring_web.dart.service;

import com.kideveloper.my_spring_web.dart.dto.doc.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

// request scope
public class ParsingDartImpl implements ParsingDart {

    // TODO: 2022/11/02 Doc 객체들 Factory method 로 의존성 관리

    private Response response = new Response();

    private Map<String, Set<String>> sceParentChildColumnNameSetMap = new HashMap<>();
    private Map<String, Column> sceColumnNameColumnMap = new HashMap<>();
    private Set<Row> sceRowSet = new LinkedHashSet<>();

    //매핑
    private Map<String, List<Cell>> sceColumNameCellListMap = new HashMap<>();
    private Map<String, List<Cell>> sceRowAccountIdCellListMap = new HashMap<>();
    private Map<Integer, List<Cell>> sceTermCellListMap = new HashMap<>();

    private Integer sceMaxColumnOrder = 0;
    private Integer sceMaxRowOrder = 0;
    private Integer sceMaxColumnDepth = 0;

    // write docs
    @Override
    public Response writeDocs(List<LinkedHashMap<String, String>> list) {
        initResponse();

        for (LinkedHashMap<String, String> dataMap : list) {

            DocType docType = DocType.valueOf(dataMap.get("sj_div"));

            if(docType == DocType.SCE) {
                writeSCE(dataMap);
                response.get(DocType.SCE).setMaxDepth(sceMaxColumnDepth);
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
        Column thisTermColumn = Column.builder()
                    .columnName(dataMap.get("thstrm_nm"))
                    .order(0)
                    .build();

        Column fromTermColumn = Column.builder()
                .columnName(dataMap.get("frmtrm_nm"))
                .order(1)
                .build();

        Column beforeFromTermColumn = Column.builder()
                .columnName(dataMap.get("bfefrmtrm_nm"))
                .order(2)
                .build();

        if(response.get(docType).getColumns().isEmpty()) {
            response.get(docType).getColumns().add(thisTermColumn);
            response.get(docType).getColumns().add(fromTermColumn);
            response.get(docType).getColumns().add(beforeFromTermColumn);
        }

        // generating Row
        Row row = Row.builder()
                .docType(DocType.valueOf(dataMap.get("sj_div")))
                .order(Integer.valueOf(dataMap.get("ord")))
                .rowName(dataMap.get("account_nm")).build();

        response.get(docType).getRows().add(row);

        // generating Cell
        Cell thisCell = Cell.builder()
                .value(dataMap.get("thstrm_amount"))
                .column(thisTermColumn)
                .build();
        Cell fromCell = Cell.builder()
                .value(dataMap.get("frmtrm_amount"))
                .column(fromTermColumn)
                .build();
        Cell beforeFromCell = Cell.builder()
                .value(dataMap.get("bfefrmtrm_amount"))
                .column(beforeFromTermColumn)
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

        sceMaxColumnDepth = Math.max(sceMaxColumnDepth, columnNameList.size());
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
                    .isRootColumn(isRootColumn)
//                .parentColumn(null)
                    .depth(depth)
                    .build();
            sceColumnNameColumnMap.put(columnName, column);
        }
        String parentColumnName = columnNameList.get(0).trim();
        if(sceParentChildColumnNameSetMap.get(parentColumnName) == null) sceParentChildColumnNameSetMap.put(parentColumnName, new HashSet<>());
        sceParentChildColumnNameSetMap.get(parentColumnName).addAll(columnNameList.subList(1, columnNameList.size()));

        System.out.println("dataMap = " + dataMap);
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
        sceMaxRowOrder = Math.max(sceMaxRowOrder, row.getOrder());
        Cell beforeFromTermCell = Cell.builder().term(0).value(dataMap.get("bfefrmtrm_amount")).build();
        Cell fromTermCell = Cell.builder().term(1).value(dataMap.get("frmtrm_amount")).build();
        Cell thisTermCell = Cell.builder().term(2).value(dataMap.get("thstrm_amount")).build();
        String thisColumnName = columnNameList.get(columnNameList.size()-1).trim();

        // Cell -> Column
        if (sceColumNameCellListMap.get(thisColumnName) == null) sceColumNameCellListMap.put(thisColumnName, new ArrayList<>());
        sceColumNameCellListMap.get(thisColumnName).add(beforeFromTermCell);
        sceColumNameCellListMap.get(thisColumnName).add(fromTermCell);
        sceColumNameCellListMap.get(thisColumnName).add(thisTermCell);

        // Cell -> Row
        if(sceRowAccountIdCellListMap.get(row.getRowAccountId()) == null)
            sceRowAccountIdCellListMap.put(row.getRowAccountId(), new ArrayList<>());
        sceRowAccountIdCellListMap.get(row.getRowAccountId()).add(beforeFromTermCell);
        sceRowAccountIdCellListMap.get(row.getRowAccountId()).add(fromTermCell);
        sceRowAccountIdCellListMap.get(row.getRowAccountId()).add(thisTermCell);

        if (sceTermCellListMap.get(0) == null) sceTermCellListMap.put(0, new ArrayList<>());
        if (sceTermCellListMap.get(1) == null) sceTermCellListMap.put(1, new ArrayList<>());
        if (sceTermCellListMap.get(2) == null) sceTermCellListMap.put(2, new ArrayList<>());
        sceTermCellListMap.get(0).add(beforeFromTermCell);
        sceTermCellListMap.get(1).add(fromTermCell);
        sceTermCellListMap.get(2).add(thisTermCell);
    }

    private void putSCEColumns() {

        for (String parentColumnName : sceParentChildColumnNameSetMap.keySet()) {
            Column parentColumn = sceColumnNameColumnMap.get(parentColumnName);
            for (String childColumnName : sceParentChildColumnNameSetMap.get(parentColumnName)) {
                Column childColumn = sceColumnNameColumnMap.get(childColumnName);
                parentColumn.getChildColumns().add(childColumn);
            }
        }

        int order = 0;
        List<Column> columns = new ArrayList<>();
        for (Column column: sceColumnNameColumnMap.values()) {
            if(0 < column.getDepth() && column.getDepth() < sceMaxColumnDepth){
                column.setOrder(0);
                columns.add(column);
                continue;
            }

            if (column.getDepth() == 0) { // root column 합계 항목
                column.setDepth(sceMaxColumnDepth);
                column.setOrder(sceColumnNameColumnMap.size()-2); // child column의 수
            } else if(column.getDepth() == sceMaxColumnDepth)
                column.setOrder(order++);

            List<Cell> cells = sceColumNameCellListMap.get(column.getColumnName());
            for (Cell cell : cells) cell.setColumn(column);
            columns.add(column);

            sceMaxColumnOrder = Math.max(sceMaxColumnOrder, column.getOrder());
        }

        Collections.sort(columns);
        response.get(DocType.SCE).getColumns().addAll(columns);
    }

    private void putSCERows() {
        for (int i = 0; i < 3; i++) {
            for (Row row : sceRowSet) {
                int term = i;
                row.setTerm(term);
                Row newRow = row.deepCopy();
                List<Cell> cellsInThisRowId = sceRowAccountIdCellListMap.get(newRow.getRowAccountId());
                for (Cell cell : cellsInThisRowId) {
                    if(cell.getTerm() == newRow.getTerm())
                        newRow.getCells().add(cell);
                }
                if(row.getRowAccountId().equals("dart_EquityAtBeginningOfPeriod"))
                    newRow.setRowName(row.getRowName() + " (" + Integer.toString(i) + "기)");
                newRow.setOrder(newRow.getOrder() + (i * sceMaxRowOrder));
                response.get(DocType.SCE).getRows().add(newRow);
                Collections.sort(newRow.getCells());
            }
        }
        Collections.sort(response.get((DocType.SCE)).getRows());
    }


    private void putSCECells() {
        for (Row row : response.get(DocType.SCE).getRows()) {
            Set<Integer> orders = new HashSet<>();
            for (Cell cell : row.getCells()) {
                Integer order = cell.getColumn().getOrder();
                orders.add(order);
            }
            for (int i = 0; i < sceMaxColumnOrder; i++) {
                if(orders.contains(i)) continue;
                row.getCells().add(i, Cell.builder().value("0").build());
            }
        }

    }
}
