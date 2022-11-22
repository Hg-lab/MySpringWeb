package com.kideveloper_dart.my_spring_web.dart_old.service;

import com.kideveloper_dart.my_spring_web.dart_old.dto.doc.*;

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
    private Map<String, List<Cell>> sceRowNameCellListMap = new HashMap<>();
    private Map<Integer, List<Cell>> sceTermCellListMap = new HashMap<>();

    private Integer sceMaxRowOrder = 0;
    private Integer sceMaxColumnDepth = 0;

    static final Integer SCE_MAX_ROW_ORDER = 100;
    static final Integer SCE_MAX_COLUMN_ORDER = 20;

    private Map<Integer,Column> sceColumnOrderColumnMap = new HashMap<>();

    // write docs
    @Override
    public Response writeDocs(List<LinkedHashMap<String, String>> list) {

        for (LinkedHashMap<String, String> dataMap : list) {
            DocType docType = DocType.valueOf(dataMap.get("sj_div"));

            if(docType == DocType.SCE) {
                writeSCEDoc(dataMap);
                response.get(DocType.SCE).setMaxDepth(sceMaxColumnDepth);
            }

            else {
                writeFinanceStateDoc(dataMap);

            }

        }

        putSCEColumns();
        putSCERows();
        putSCECells();
        return response;
    }

    // TODO: 2022/11/01 refactoring
    private void writeFinanceStateDoc(LinkedHashMap<String, String> dataMap) {
        DocType docType = DocType.valueOf(dataMap.get("sj_div"));

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

        row.getCells().add(thisCell);
        row.getCells().add(fromCell);
        row.getCells().add(beforeFromCell);

    }
    private void writeSCEDoc(LinkedHashMap<String, String> dataMap) {
        System.out.println("dataMap = " + dataMap);

        // TODO: 2022/11/02 정규표현식
        String accountDetail = dataMap.get("account_detail");
        accountDetail = accountDetail.replaceAll("[\\[\\w\\]]", "");
        accountDetail = accountDetail.replaceAll("\\s", "");
        List<String> columnNameList = Arrays.asList(accountDetail.split("\\|"));

        // Build Column
        for (int i = 0; i < columnNameList.size(); i++) {
            boolean isRootColumn = false;
            if(columnNameList.size() == 1) {
                isRootColumn = true;
            }

            int depth = 1;
            if(i >= 1) depth = 2;

            String columnName = columnNameList.get(i).trim();
            Column column = Column.builder()
                    .columnName(columnName)
                    .isRootColumn(isRootColumn)
                    .depth(depth)
                    .build();

            if(sceColumnNameColumnMap.get(columnName) == null) {
                column.setOrder(SCE_MAX_ROW_ORDER-sceColumnNameColumnMap.size()); // 컬럼 들어온 역순으로 정렬
                sceColumnNameColumnMap.put(columnName, column);
            }

            sceMaxColumnDepth = Math.max(sceMaxColumnDepth, depth);
        }
        String parentColumnName = columnNameList.get(0).trim();
        if(sceParentChildColumnNameSetMap.get(parentColumnName) == null) sceParentChildColumnNameSetMap.put(parentColumnName, new HashSet<>());
        sceParentChildColumnNameSetMap.get(parentColumnName).addAll(columnNameList.subList(1, columnNameList.size()));

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
        if(sceRowNameCellListMap.get(row.getRowName()) == null)
            sceRowNameCellListMap.put(row.getRowName(), new ArrayList<>());
        sceRowNameCellListMap.get(row.getRowName()).add(beforeFromTermCell);
        sceRowNameCellListMap.get(row.getRowName()).add(fromTermCell);
        sceRowNameCellListMap.get(row.getRowName()).add(thisTermCell);

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
            if(!column.getIsRootColumn() && column.getDepth() < sceMaxColumnDepth){
                if(column.getOrder() == null)
                    column.setOrder(0);
            }

            if (column.getIsRootColumn()) { // root column 은 합계 항목
                column.setDepth(sceMaxColumnDepth);
                if(column.getOrder() == null)
                    column.setOrder(sceColumnNameColumnMap.size()-2); // child column 의 수
            } else if(column.getDepth() == sceMaxColumnDepth)
                if(column.getOrder() == null)
                    column.setOrder(order++);

            columns.add(column);

            List<Cell> cells = sceColumNameCellListMap.get(column.getColumnName());
            if(cells == null) continue;
            for (Cell cell : cells) cell.setColumn(column);

            sceColumnOrderColumnMap.put(column.getOrder(), column);
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
                List<Cell> cellsInThisRowId = sceRowNameCellListMap.get(newRow.getRowName());
                for (Cell cell : cellsInThisRowId) {
                    if(cell.getTerm() == newRow.getTerm())
                        newRow.getCells().add(cell);
                }
                if(row.getRowAccountId().equals("dart_EquityAtBeginningOfPeriod"))
                    newRow.setRowName(row.getRowName() + " (" + Integer.toString(i) + "기)");
                newRow.setOrder(newRow.getOrder() + (i * SCE_MAX_ROW_ORDER));
                response.get(DocType.SCE).getRows().add(newRow);
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
            Set<Integer> sceColumnOrderSet = new HashSet<>(sceColumnOrderColumnMap.keySet());
            sceColumnOrderSet.removeAll(orders);

            for (Integer columnOrder : sceColumnOrderSet) {
                row.getCells().add(Cell.builder().column(sceColumnOrderColumnMap.get(columnOrder)).value(" ").build());
            }

            Collections.sort(row.getCells());
        }

    }
}
