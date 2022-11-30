package com.kideveloper_dart.my_spring_web.dart.application.dto.request;

import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.response.APIFinStatsDTO;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinancialStatementsDTO {
    private String rcept_no;
    private String reprt_code;
    private String bsns_year;
    private String corp_code;
    private String sj_div;
    private String sj_nm;
    private String account_id;
    private String account_nm;
    private String account_detail;
    private String thstrm_nm;
    private String thstrm_amount;
    private String thstrm_add_amount;
    private String frmtrm_nm;
    private String frmtrm_amount;
    private String frmtrm_q_nm;
    private String frmtrm_q_amount;
    private String frmtrm_add_amount;
    private String bfefrmtrm_nm;
    private String bfefrmtrm_amount;
    private String ord;
    private String currency;
    private String status;

    public static FinancialStatementsDTO from(APIFinStatsDTO apiFinStatsDTO) {
        return new FinancialStatementsDTO(
                apiFinStatsDTO.getRcept_no(),
                apiFinStatsDTO.getReprt_code(),
                apiFinStatsDTO.getBsns_year(),
                apiFinStatsDTO.getCorp_code(),
                apiFinStatsDTO.getSj_div(),
                apiFinStatsDTO.getSj_nm(),
                apiFinStatsDTO.getAccount_id(),
                apiFinStatsDTO.getAccount_nm(),
                apiFinStatsDTO.getAccount_detail(),
                apiFinStatsDTO.getThstrm_nm(),
                apiFinStatsDTO.getThstrm_amount(),
                apiFinStatsDTO.getThstrm_add_amount(),
                apiFinStatsDTO.getFrmtrm_nm(),
                apiFinStatsDTO.getFrmtrm_amount(),
                apiFinStatsDTO.getFrmtrm_q_nm(),
                apiFinStatsDTO.getFrmtrm_q_amount(),
                apiFinStatsDTO.getFrmtrm_add_amount(),
                apiFinStatsDTO.getBfefrmtrm_nm(),
                apiFinStatsDTO.getBfefrmtrm_amount(),
                apiFinStatsDTO.getOrd(),
                apiFinStatsDTO.getCurrency(),
                apiFinStatsDTO.getStatus()
        );
    }
}
