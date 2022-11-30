package com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.request;

import com.kideveloper_dart.my_spring_web.dart.application.dto.request.DartDocsRequestDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.request.FinancialStatementsDTO;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.response.APIFinStatsDTO;
import com.kideveloper_dart.my_spring_web.dart_old.dto.DartRequestDTO;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenDartRequestDTO {

    private String corpCode;
    private String bsnsYear;
    private String fsDiv;

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public static OpenDartRequestDTO from(DartDocsRequestDTO dartDocsRequestDTO) {
        return new OpenDartRequestDTO(
                dartDocsRequestDTO.getCorpCode(),
                String.valueOf(dartDocsRequestDTO.getBusinessYear()),
                dartDocsRequestDTO.getFinancialStatDiv()
        );
    }
}
