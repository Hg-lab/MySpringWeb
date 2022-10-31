package com.kideveloper.my_spring_web.dart.dto.doc;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Builder
public class Row {

    private DocType docType;
    private Integer order = 0;
    private String rowName;
}
