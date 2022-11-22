package com.kideveloper_dart.my_spring_web.dart_old.dto.doc;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Row implements Comparable{

    private DocType docType;
    private Integer order;
    private String rowAccountId;
    private String rowName;
    private Integer term;

    private final List<Cell> cells = new ArrayList<>();

    public Row deepCopy() {
        Row newRow = Row.builder()
                .docType(this.docType)
                .order(this.order)
                .rowAccountId(this.rowAccountId)
                .rowName(this.rowName)
                .term(this.term)
                .build();

        for (Cell cell : this.getCells()) {
            newRow.getCells().add(cell);
//            newRow.getCells().add(cell.deepCopy());
        }

        return newRow;
    }

    @Override
    public int compareTo(Object o) {
        return this.order - ((Row) o).order;
    }
}
