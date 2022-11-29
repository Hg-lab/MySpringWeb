package com.kideveloper_dart.my_spring_web.dart_old.dto.doc;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Column implements Comparable<Column>{

    private final String id = UUID.randomUUID().toString();
    private String columnName;
    private Integer term;
    private Integer order;
    private Boolean isRootColumn;
    private Column parentColumn;
    private final List<Column> childColumns = new ArrayList<>();
    private Integer depth;


    public static Column getThisTermColumn(LinkedHashMap<String, String> dataMap) {
        return Column.builder()
                .columnName(dataMap.get("thstrm_nm"))
                .order(0)
                .build();
    }

    public static Column getFromTermColumn(LinkedHashMap<String, String> dataMap) {
        return Column.builder()
                .columnName(dataMap.get("frmtrm_nm"))
                .order(1)
                .build();
    }

    public static Column getBeforeFromTermColumn(LinkedHashMap<String, String> dataMap) {
        return Column.builder()
                .columnName(dataMap.get("bfefrmtrm_nm"))
                .order(2)
                .build();
    }

    // 중복검사를 위해 VO로 구현, HashSet 에서는 hashCode 로 중복체크한다
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return this.getColumnName().equals(column.columnName);
    }

    @Override
    public int hashCode() {
        return columnName.hashCode();
    }

    @Override
    public int compareTo(Column column) {
        return this.order - column.order;
    }
}
