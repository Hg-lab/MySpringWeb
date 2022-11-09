package com.kideveloper.my_spring_web.dart.dto.doc;


import lombok.Builder;
import lombok.Data;

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

    public Cell deepCopy() {
        Cell cell = Cell.builder()
                .column(this.column)
                .value(this.value)
                .term(this.term)
                .build();
        return cell;
    }
}
