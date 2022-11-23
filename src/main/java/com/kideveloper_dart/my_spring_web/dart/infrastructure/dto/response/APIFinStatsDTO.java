package com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class APIFinStatsDTO {

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
}
