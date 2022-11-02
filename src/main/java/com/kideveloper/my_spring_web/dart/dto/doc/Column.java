package com.kideveloper.my_spring_web.dart.dto.doc;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
public class Column {

    private final String id = UUID.randomUUID().toString();
    private String columnName;
    private Integer order;
    private Column rootColumn;
    private Column parentColumn;
    private List<Column> childColumns = new ArrayList<>();
    private Integer depth;

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
}
