package com.kideveloper.my_spring_web.model;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class StatementOfChangesInEquity {

        public StatementOfChangesInEquity(String bsns_year, String ord, String account_id, String account_nm, String account_detail,
                                          String bfefrmtrm_amount, String frmtrm_amount, String thstrm_amount) {
                this.bsns_year = bsns_year;
                this.ord = ord;
                this.account_id = account_id;
                this.account_nm = account_nm;
                this.account_detail = account_detail;
                this.bfefrmtrm_amount = bfefrmtrm_amount;
                this.frmtrm_amount = frmtrm_amount;
                this.thstrm_amount = thstrm_amount;

        }

        private String bsns_year;
        private String ord;
        private String account_id;
        private String account_nm;
        private String account_detail;
        private String bfefrmtrm_amount;
        private String frmtrm_amount;
        private String thstrm_amount;
}
