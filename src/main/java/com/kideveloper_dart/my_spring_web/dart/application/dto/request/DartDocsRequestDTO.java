package com.kideveloper_dart.my_spring_web.dart.application.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DartDocsRequestDTO {

    private String stockCode;
    private String corpCode;
    private Integer businessYear;
    private String financialStatDiv;
}
