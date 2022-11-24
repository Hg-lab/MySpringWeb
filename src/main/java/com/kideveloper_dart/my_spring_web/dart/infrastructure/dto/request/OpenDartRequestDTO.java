package com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenDartRequestDTO {

    private String corpCode;
    private String bsnsYear;
    private String fsDiv;
}
