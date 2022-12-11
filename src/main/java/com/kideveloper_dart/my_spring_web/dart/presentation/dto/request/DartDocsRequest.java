package com.kideveloper_dart.my_spring_web.dart.presentation.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DartDocsRequest {

    private String stock_code;
    private String corp_code;
    private Integer bsns_year;
    private String fs_div;
    private String doc_type;

}
