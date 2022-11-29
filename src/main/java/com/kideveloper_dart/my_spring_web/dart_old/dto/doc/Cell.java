package com.kideveloper_dart.my_spring_web.dart_old.dto.doc;


import lombok.Builder;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@Builder
public class Cell implements Comparable<Cell>{

    private Column column;
    private String value;
    private Integer term;

    @Override
    public int compareTo(Cell cell) {
        return this.column.getOrder() - cell.column.getOrder();
    }

    public static Cell getThisTermCell(LinkedHashMap<String, String> dataMap, Column thisTermColumn) {
        return Cell.builder()
                .value(dataMap.get("thstrm_amount"))
                .column(thisTermColumn)
                .build();
    }

    public static Cell getFromTermCell(LinkedHashMap<String, String> dataMap, Column fromTermColumn) {
        return Cell.builder()
                .value(dataMap.get("frmtrm_amount"))
                .column(fromTermColumn)
                .build();
    }

    public static Cell getBeforeFromTermCell(LinkedHashMap<String, String> dataMap, Column beforeFromTermColumn) {
        return Cell.builder()
                .value(dataMap.get("bfefrmtrm_amount"))
                .column(beforeFromTermColumn)
                .build();
    }

    public Cell deepCopy() {
        Cell cell = Cell.builder()
                .column(this.column)
                .value(this.value)
                .term(this.term)
                .build();
        return cell;
    }
}
