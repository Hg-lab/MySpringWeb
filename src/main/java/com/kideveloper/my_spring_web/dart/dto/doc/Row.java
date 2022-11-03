package com.kideveloper.my_spring_web.dart.dto.doc;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Row {

    private DocType docType;
    private Integer order = 0;
    private String rowAccountId;
    private String rowName;

    private final List<Cell> cells = new ArrayList<>();

    public Row deepCopy() {
        Row newRow = Row.builder()
                .docType(this.docType)
                .order(this.order)
                .rowAccountId(this.rowAccountId)
                .rowName(this.rowName).build();

        for (Cell cell : this.getCells()) {
            newRow.getCells().add(cell);
        }

        return newRow;
    }

}
