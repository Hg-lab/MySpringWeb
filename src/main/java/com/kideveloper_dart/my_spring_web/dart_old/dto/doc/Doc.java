package com.kideveloper_dart.my_spring_web.dart_old.dto.doc;

import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Data
public class Doc {
    private final DocType docType;
    private List<Column> columns = new ArrayList<>();
    private List<Row> rows = new ArrayList<>();
    private Integer maxDepth;
    public Doc(DocType docType) {
        this.docType = docType;
    }
}
